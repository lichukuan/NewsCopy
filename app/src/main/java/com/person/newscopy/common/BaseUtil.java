package com.person.newscopy.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import java.time.Instant;

public final class BaseUtil {

    public static long getTime(){
        long time = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time =  Instant.now().getEpochSecond();
        }else {
            time = System.currentTimeMillis();
        }
        return time;
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

}
