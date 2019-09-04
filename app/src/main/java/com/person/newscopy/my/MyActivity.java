package com.person.newscopy.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.person.newscopy.R;

public class MyActivity extends AppCompatActivity {

    public static final String[] base_content = {"消息通知","私信","我的收藏","阅读纪录","用户反馈","免流量服务","我的评论","系统设置","我的点赞"
    ,"扫一扫","超级会员"};

    public static final int[] base_res = {R.drawable.my_message,R.drawable.my_private_message,R.drawable.my_shoucang,R.drawable.my_history
            ,R.drawable.my_fankui,R.drawable.my_liuliang,R.drawable.my_comment,R.drawable.my_set,R.drawable.my_like
    ,R.drawable.my_saoyisao,R.drawable.my_vip};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        findViewById(R.id.back).setOnClickListener(v->finish());
        MyListViewLayout base = findViewById(R.id.list_base);
        base.setContent(base_content,base_res);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
