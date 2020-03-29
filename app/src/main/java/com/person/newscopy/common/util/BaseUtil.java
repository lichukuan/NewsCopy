package com.person.newscopy.common.util;

import android.app.Application;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.person.newscopy.api.Api;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.common.NetState;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.ResultBean;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class BaseUtil {

    public static Gson gson = new Gson();

    private static Date dNow = new Date( );

    public static Gson getGson() {
        return gson;
    }

    public static long getTime(){
        return System.currentTimeMillis();
    }

    public static String getFormatTime(){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(dNow);
    }

    public static NetState getNetType(){
        //步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP){
            //步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
            //NetworkInfo对象包含网络连接的所有信息
            //步骤3：根据需要取出网络连接信息
            //获取WIFI连接的信息
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            Boolean isWifiConn = networkInfo.isConnected();
            if (isWifiConn)
                return NetState.WIFI;
            //获取移动数据连接的信息
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            Boolean isMobileConn = networkInfo.isConnected();
            if (isMobileConn)
                return NetState.MOBILE;
            return NetState.NOT_NET;
        }else {
            //获取所有网络连接的信息
            Network[] networks;
            networks = connMgr.getAllNetworks();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (networkInfo.isConnected()){
                    switch (networkInfo.getType()){
                        case ConnectivityManager.TYPE_MOBILE:
                            return NetState.MOBILE;
                        case ConnectivityManager.TYPE_WIFI:
                            return NetState.WIFI;
                            default:
                                return NetState.OTHER;
                    }
                }else
                    return NetState.NOT_NET;
            }
        }
             return NetState.NOT_NET;
    }

    public static String createSalt(){
        return "";
    }

    public static String createToken(){
        return "";
    }


    public static String createFormatTime(int time){
        int second = time % 60;
        int minute = time /60;
        StringBuilder builder = new StringBuilder();
        if (minute < 10){
            builder.append("0"+minute);
        }else builder.append(minute);
        builder.append(":");
        if (second < 10){
            builder.append("0"+second);
        }else builder.append(second);
        return builder.toString();
    }


    public static String createComeTime(int time){
        long l =  getTime()/1000 - time;
        long allMinute = l/60;
        if (allMinute == 0)
            return "刚刚";
        else if (allMinute < 60)
            return allMinute+"分钟前";
        long allHour = allMinute/60;
        if (allHour < 24)
            return allHour+"小时前";
        int allDay = (int)allHour/24;
        if (allDay < 30)return allDay+"天前";
        int allMouth = allDay/30;
        if (allMouth < 12)return allMouth+"月前";
        return allMouth/12+"年前";
    }

    public static void writeToFile(String name,String content,int mode){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try {
            out = MyApplication.getContext().openFileOutput(name,mode);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null){
                    writer.close();
                }
                if (out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFromFile(String name){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try {
            in=MyApplication.getContext().openFileInput(name);//获取文件输入流
            reader=new BufferedReader(new InputStreamReader(in));
            String data="";
            if ((data=reader.readLine())!=null){
                content.append(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static void updateUserInfo(ResultBean resultBean, Application application){
        Users.userCare = resultBean.getCareCount();
        Users.userFans = resultBean.getFansCount();
        Users.userIcon = resultBean.getIcon();
        Users.userId = resultBean.getId();
        Users.userName = resultBean.getName();
        Users.userWork = resultBean.getReleaseCount();
        Users.userRecommend = resultBean.getRecommend();
        Users.email = resultBean.getEmail();
        SharedPreferences sharedPreferences = application.getSharedPreferences(Config.USER_INFO_STORE_KEY,0);
        sharedPreferences.edit().putBoolean("isLogin",true)
                .putString("userId",Users.userId)
                .putString("userIcon",Users.userIcon)
                .putString("userName",Users.userName)
                .putString("userRecommend",Users.userRecommend)
                .putString("email",Users.email)
                .putInt("care",Users.userCare)
                .putInt("fans",Users.userFans)
                .putInt("work",Users.userWork)
                .apply();
    }

    public static final String DOWNLOAD_APK_ID_PREFS = "app_id";

    public static void downloadApk(String infoName, String storeApk) {
        DownloadManager.Request request;
        try {
            request = new DownloadManager.Request(Uri.parse(Api.DOWNLOAD_NEWEST_APP));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setTitle(infoName);
        request.setDescription("现在下载请稍后....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, storeApk);
        Context appContext = MyApplication.getContext();
        DownloadManager manager = (DownloadManager)
                appContext.getSystemService(Context.DOWNLOAD_SERVICE);

        // 存储下载Key
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(appContext);
        sp.edit().putLong(DOWNLOAD_APK_ID_PREFS, manager.enqueue(request)).apply();
    }

    public static List<String> jsonToStringList(String json){
       return gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
    }

   static Configuration config = new Configuration.Builder()
            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
            .connectTimeout(10)           // 链接超时。默认10秒
            .responseTimeout(60)          // 服务器响应超时。默认60秒
            .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
            .build();
    // 重用uploadManager。一般地，只需要创建一个uploadManager对象
    static UploadManager uploadManager = new UploadManager(config, 3);
    public static void pushImageToQiNiu(String path,String name,UpCompletionHandler completionHandler){
            uploadManager.put(path, name, Users.key,completionHandler, null);
        //配置3个线程数并发上传；不配置默认为3，
        // 只针对file.size>4M生效。线程数建议不超过5，上传速度主要取决于上行带宽，带宽很小的情况单线程和多线程没有区别
    }

    public static String getImagePath(Uri uri){
        String path = null;
        if (DocumentsContract.isDocumentUri(MyApplication.getContext(),uri)){
            //如果是document类型的Uri,通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析出数字格式的id
                String selection= MediaStore.Images.Media._ID+"="+id;
                path=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                path=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则用普通方式来处理
            path=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri,直接获取图片路径就可
            path=uri.getPath();
        }
       return path;
    }

    /**
     * 获得图片的路径
     * @param uri
     * @param selection
     * @return
     */
    private static String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=MyApplication.getContext().getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static String buildImageName(){
        return UUID.randomUUID().toString();
    }
}
