package com.person.newscopy.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 要实现的推送功能：关注的人发文章、用户关注、私信
 */
public class NewsMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;
    //onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
//        mMessage = message.getContent();
//        if(!TextUtils.isEmpty(message.getTopic())) {
//            mTopic=message.getTopic();
//        } else if(!TextUtils.isEmpty(message.getAlias())) {
//            mAlias=message.getAlias();
//        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount=message.getUserAccount();
//        }
          startNotice(context,message);
    }

    private PendingIntent createPendingIntent(int type,Context context,String content){
        Intent intent = null;
        switch (type){
            case PushType.LIKE://赞
                intent = new Intent(context, MyActivity.class);
                intent.putExtra(MyActivity.MY_TYPE,MyActivity.MESSAGE_TYPE);
                break;
            case PushType.COMMENT://评论
                ResultBean b = BaseUtil.getGson().fromJson(content,ResultBean.class);
                if (b.getType() == Config.CONTENT.NEWS_TYPE){
                    intent = new Intent(context, ShowNewsActivity.class);
                    intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,content);
                }else{
                    intent = new Intent(context, ShowVideoActivity.class);
                    intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,content);
                }
                break;
            case PushType.SAVE://收藏
                intent = new Intent(context,MyActivity.class);
                intent.putExtra(MyActivity.MY_TYPE,MyActivity.SAVE_TYPE);
                break;
            case PushType.CARE://关注
                intent = new Intent(context,MyActivity.class);
                intent.putExtra(MyActivity.MY_TYPE,MyActivity.CARE_TYPE);
                break;
            case PushType.PRIVATE_TALK://私信
                intent = new Intent(context,MyActivity.class);
                intent.putExtra(MyActivity.MY_TYPE,MyActivity.PRIVATE_TALK_TYPE);
                break;
            case PushType.SYSTEM://系统
                break;
        }
        return PendingIntent.getActivity(context,0,intent,0);
    }

    private void startNotice(Context context,MiPushMessage message){
        PendingIntent intent = createPendingIntent(Integer.valueOf(message.getExtra().get("notice_type")),context,message.getContent());
        NotificationManager manager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (manager == null)return;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            Notification notification=new NotificationCompat.Builder(context)
                    .setContentTitle(message.getTitle())
                    .setContentText(message.getContent())
                    .setSmallIcon(R.mipmap.wei_icon)  //设置通知的小图标
                    .setAutoCancel(true) //设置通知点击后取消
                    .setContentIntent(intent)  //设置PendingIntent
                    .build();
            manager.notify(1,notification); //让通知显示出来
        }else{
            String id="id";
            //android 8 以后要用这个方法创建channel
            NotificationChannel channel=new NotificationChannel("id","name",NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("微进步通知");
            //设置channel
            manager.createNotificationChannel(channel);
            Notification m= new NotificationCompat.Builder(context,id)
                    .setContentTitle(message.getTitle())
                    .setContentText(message.getContent())
                    .setSmallIcon(R.mipmap.wei_icon)
                    .build();
            manager.notify(1,m);
        }
    }


    //onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
    // 这个回调方法会在用户手动点击通知后触发。
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }
    //onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
    // 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic=message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias=message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
    }
    // * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }
    // * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
    // * 8、以上这些方法运行在非 UI 线程中。
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
    }

}
