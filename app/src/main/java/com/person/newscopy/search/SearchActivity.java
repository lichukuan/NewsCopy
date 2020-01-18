package com.person.newscopy.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.common.MyTranslucentUtil;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.search.adapter.HotNewsSearchAdapter;
import com.person.newscopy.search.net.SearchViewModel;
import com.person.newscopy.show.ShowNewsActivity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.person.newscopy.news.fragment.VideoFragment.HOT_VIDEO_KEY;


public class SearchActivity extends AppCompatActivity {

    EditText content;
    TextView search;
    RecyclerView hotNews;
    HistoryView history;
    LinearLayout layout;
    ImageView clearHistory;
    public static final String STORE_NAME = "search_history";
    public static final String KEY = "history_value";
    private SearchViewModel searchViewModel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(), this, ScreenFitUtil.FIT_WIDTH);
        MyTranslucentUtil.setTranslucent(this, Color.parseColor("#ffff4444"), (int) (30 * ScreenFitUtil.getDensity()));
        setContentView(R.layout.activity_search);
        findViewById(R.id.back).setOnClickListener(v -> finish());
        content = findViewById(R.id.search_content);
        search = findViewById(R.id.search);
        hotNews = findViewById(R.id.hot_new_list);
        history = findViewById(R.id.history);
        layout  =findViewById(R.id.history_layout);
        clearHistory = findViewById(R.id.delete_history);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(STORE_NAME, 0);
        Set<String> l = preferences.getStringSet(KEY,null);
        l = new HashSet<>();
        l.add("游戏王");
        l.add("请问今天要来点兔子吗");
        l.add("异度入侵");
        l.add("火影忍者");
        l.add("海贼王");
        history.setData(l);
        searchViewModel.queryHotData().observe(this, contentResult -> {
            HotNewsSearchAdapter hotNewsSearchAdapter = new HotNewsSearchAdapter(SearchActivity.this,contentResult.getResult());
            hotNews.setAdapter(hotNewsSearchAdapter);
        });
        hotNews.setLayoutManager(new GridLayoutManager(this,2));
        clearHistory.setOnClickListener(v -> {
            history.clearAll();
            layout.setVisibility(View.GONE);
        });
    }

    private void createPop(){

    }
}
