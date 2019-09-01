package com.person.newscopy.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.search.adapter.HotNewsSearchAdapter;

import static com.person.newscopy.news.fragment.VideoFragment.HOT_VIDEO_KEY;


public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_KEY = "key";

    public static final String SEARCH_ID = "value";

    EditText content;

    TextView search;

    RecyclerView hotNews;

    RecyclerView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            VideoSearchBean value = gson.fromJson(preferences.getString(SEARCH_ID,""), VideoSearchBean.class);
            HotNewsSearchAdapter adapter = new HotNewsSearchAdapter(this,null,value.getData().getData());
            LinearLayoutManager manager = new LinearLayoutManager(this);
            hotNews.setAdapter(adapter);
            hotNews.setLayoutManager(manager);
        }else {
            HotNewsBean value = gson.fromJson(preferences.getString(SEARCH_ID,""), HotNewsBean.class);
            HotNewsSearchAdapter adapter = new HotNewsSearchAdapter(this,value.getData(),null);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            hotNews.setAdapter(adapter);
            hotNews.setLayoutManager(manager);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
