package com.person.newscopy.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.news.network.bean.DataBeanX;
import com.person.newscopy.news.network.bean.DataBeanXXXXXX;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.search.adapter.HotNewsSearchAdapter;
import com.person.newscopy.show.ShowNewsActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.person.newscopy.news.fragment.VideoFragment.HOT_VIDEO_KEY;


public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_KEY = "key";

    public static final String SEARCH_ID = "value";

    EditText content;

    TextView search;

    RecyclerView hotNews;

    RecyclerView history;

    boolean isContinue = true;

    Subscription subscription;

    List<DataBeanXXXXXX> videoData;

    List<DataBeanX> newsData;

    int index = 0;

    HotNewsSearchAdapter newsAdapter;
    boolean isNews = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        TranslucentUtil.setTranslucent(this, Color.parseColor("#ffff4444"), (int) (20*ScreenFitUtil.getDensity()));
        setContentView(R.layout.activity_search);
        findViewById(R.id.back).setOnClickListener(v->finish());
        content=findViewById(R.id.search_content);
        search=findViewById(R.id.search);
        hotNews=findViewById(R.id.hot_new_list);
        history=findViewById(R.id.search_history);

        Intent intent = getIntent();
        String key = intent.getStringExtra(SEARCH_KEY);
        SharedPreferences preferences=getSharedPreferences(key,0);
        Gson gson = new Gson();
        if(key.equals(HOT_VIDEO_KEY)){
            isNews = false;
            VideoSearchBean value = gson.fromJson(preferences.getString(SEARCH_ID,""), VideoSearchBean.class);
            videoData = value.getData().getData();
            HotNewsSearchAdapter adapter = new HotNewsSearchAdapter(this,null,videoData);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            hotNews.setAdapter(adapter);
            hotNews.setLayoutManager(manager);
        }else {
            HotNewsBean value = gson.fromJson(preferences.getString(SEARCH_ID,""), HotNewsBean.class);
            newsData = value.getData();
            newsAdapter = new HotNewsSearchAdapter(this,newsData.subList(index,index+4),null);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            hotNews.setAdapter(newsAdapter);
            hotNews.setLayoutManager(manager);
        }
        search.setOnClickListener(v -> {
            String value = content.getText().toString();
            Intent intent1 = new Intent(this,ShowNewsActivity.class);
            if (isNews)
            intent1.putExtra(ShowNewsActivity.SHOW_WEB_INFO,"https://www.toutiao.com/search/?keyword="+value);
            else intent1.putExtra(ShowNewsActivity.SHOW_WEB_INFO,"https://www.ixigua.com/search/"+value);
            startActivity(intent1);
        });
        delayCircle(10);
    }

    private void delayCircle(int time){
        subscription = Observable.timer(time, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    index+=4;
                 if(index+4>newsData.size())index=0;
                 newsAdapter.setContent(newsData.subList(index,index+4));
                    if (isContinue)
                    delayCircle(time);
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        isContinue=false;
        subscription.unsubscribe();
    }
}
