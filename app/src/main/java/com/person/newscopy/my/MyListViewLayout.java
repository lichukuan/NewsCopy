package com.person.newscopy.my;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.edit.EditActivity;
import com.person.newscopy.user.Users;

public class MyListViewLayout extends FrameLayout implements View.OnClickListener{

    public void setContent(String[] content,int[] res){
        final float d = ScreenFitUtil.getDensity();
        for (int i = 0; i <content.length ; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.my_list_item_view,this,false);
            view.setTag(content[i]);
            view.setOnClickListener(this);
            ImageView icon = view.findViewById(R.id.icon);
            TextView text = view.findViewById(R.id.text);
            icon.setImageResource(res[i]);
            text.setText(content[i]);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)(d*40));
            params.topMargin = (int) (40*i*d);
            view.setLayoutParams(params);
            addView(view);
        }
        requestLayout();
    }


    public MyListViewLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MyListViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }

    @Override
    public void onClick(View v) {
        Context c = v.getContext();
        if (!Users.LOGIN_FLAG){
            Toast.makeText(c, "请先登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        String tag = (String) v.getTag();
        switch (tag){
            case "消息通知":
                startRequireFragment(MyActivity.MESSAGE_TYPE,c);
                break;
            case "私信":
                startRequireFragment(MyActivity.PRIVATE_TALK_TYPE,c);
                break;
            case "我的收藏":
                startRequireFragment(MyActivity.SAVE_TYPE,c);
                break;
            case "阅读纪录":
                startRequireFragment(MyActivity.HISTORY_TYPE,c);
                break;
            case "用户反馈":
                startRequireFragment(MyActivity.FEEDBACK_TYPE,c);
                break;
            case "设置":
                startRequireFragment(MyActivity.SETTING_TYPE,c);
                break;
            case "发布内容":
                getContext().startActivity(new Intent(getContext(), EditActivity.class));
                break;
            case "关于":
                launchAppDetail(getContext().getPackageName(),"");
                break;
            case "视频分享":
                startRequireFragment(MyActivity.VIDEO_SHARE,c);
                break;
        }
    }

    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startRequireFragment(int type,Context context){
        Intent i = new Intent(getContext(), MyActivity.class);
        i.putExtra(MyActivity.MY_TYPE,type);
        context.startActivity(i);
    }
}
