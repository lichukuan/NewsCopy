package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.MyListViewLayout;
@Deprecated
public class MoreFragment extends Fragment {

    public static final String[] base_content = {"消息通知","私信","我的收藏","阅读纪录","用户反馈","免流量服务","我的评论","系统设置","我的点赞"
            ,"扫一扫","超级会员"};

    public static final int[] base_res = {R.drawable.my_message,R.drawable.my_private_message,R.drawable.my_shoucang,R.drawable.my_history
            ,R.drawable.my_fankui,R.drawable.my_comment,R.drawable.my_set,R.drawable.my_like
            ,R.drawable.my_saoyisao,R.drawable.my_vip};

    MyActivity myActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_more,container,false);
        myActivity = (MyActivity) getActivity();
        view.findViewById(R.id.back).setOnClickListener(v->{myActivity.finish();});
        MyListViewLayout base = view.findViewById(R.id.list_base);
        base.setContent(base_content,base_res);
        for (int i = 0; i < base.getChildCount(); i++) {
            View v = base.getChildAt(i);
            switch (i){
                case 0:
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.MESSAGE_TYPE,true));
                    break;
                case 1:
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.PRIVATE_TALK_TYPE,true));
                    break;
                case 2:
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.SAVE_TYPE,true));
                    break;
                case 3:
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.HISTORY_TYPE,true));
                    break;
                case 4:
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.FEEDBACK_TYPE,true));
                    break;
                case 5://免流量服务
                    //v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.HISTORY_TYPE,true));
                    break;
                case 6://评论
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.COMMENT_TYPE,true));
                    break;
                case 7:
                    v.setOnClickListener(v1->myActivity.changeFragment(MyActivity.SETTING_TYPE,true));
                    break;
                case 8:
                    v.setOnClickListener(v1 -> myActivity.changeFragment(MyActivity.LIKE_TYPE,true));
                    break;
                case 9://扫一扫
                    break;
                case 10://超级会员
                    break;
            }
        }
        return view;
    }

}
