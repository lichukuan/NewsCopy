package com.person.newscopy.search;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.util.MyTranslucentUtil;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.search.adapter.HistorySearchAdapter;
import com.person.newscopy.search.adapter.HotNewsSearchAdapter;
import com.person.newscopy.search.net.SearchViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SearchActivity extends AppCompatActivity {

    EditText content;
    TextView cancel;
    RecyclerView hotNews;
    HistoryView history;
    LinearLayout layout;
    ImageView clearHistory;
    LinearLayout parent;
    public static final String STORE_NAME = "search_history";
    public static final String KEY = "history_value";
    private SearchViewModel searchViewModel = null;
    Set<String> historySet = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(), this, ScreenFitUtil.FIT_WIDTH);
        MyTranslucentUtil.setTranslucent(this, Color.parseColor("#ffff4444"), (int) (30 * ScreenFitUtil.getDensity()));
        setContentView(R.layout.activity_search);
        content = findViewById(R.id.search_content);
        cancel = findViewById(R.id.cancel);
        hotNews = findViewById(R.id.hot_new_list);
        history = findViewById(R.id.history);
        layout  =findViewById(R.id.history_layout);
        clearHistory = findViewById(R.id.delete_history);
        parent = findViewById(R.id.parent);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        SharedPreferences preferences = getSharedPreferences(STORE_NAME, 0);
        cancel.setOnClickListener(v -> finish());
        historySet = new HashSet<>(preferences.getStringSet(KEY,new HashSet<>()));
        if (historySet.size() != 0){
            for (String s1 : historySet) {
                TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.history_text,null);
                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT,90);
                textView.setLayoutParams(layoutParams);
                textView.setOnClickListener(v -> {
                    content.setText(s1);
                });
                textView.setText(s1);
                history.addView(textView);
            }
        }else layout.setVisibility(View.GONE);
        //Log.d("==History","history top = "+history.getTop()+" left = "+history.getLeft()+" right = "+history.getRight()+" bottom = "+history.getBottom());
        searchViewModel.queryHotData().observe(this, contentResult -> {
            List<ResultBean> data =  contentResult.getResult();
            Map<String,ResultBean> map = new HashMap<>(data.size());
            for (ResultBean datum : data) {
                map.put(datum.getTitle(),datum);
            }
            data.clear();
            data.addAll(map.values());
            while (data.size() > 8)data.remove(data.size() - 1);
            HotNewsSearchAdapter hotNewsSearchAdapter = new HotNewsSearchAdapter(SearchActivity.this,data);
            hotNews.setAdapter(hotNewsSearchAdapter);
        });
        hotNews.setLayoutManager(new LinearLayoutManager(this));
        clearHistory.setOnClickListener(v -> {
            SharedPreferences.Editor pre = getSharedPreferences(STORE_NAME, 0).edit();
            pre.remove(KEY);
            pre.apply();
            history.clearAll();
            layout.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        content.setOnEditorActionListener((v, actionId, event) -> {
            if (!content.getText().toString().isEmpty())
            createPop(content.getText().toString());
            return true;
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void addToHistory(String content){
        SharedPreferences.Editor preferences = getSharedPreferences(STORE_NAME, 0).edit();
        if (historySet == null)
        historySet = new HashSet<>();
        historySet.add(content);
        preferences.putStringSet(KEY,historySet);
        preferences.apply();
    }

    private void createPop(String data){
        View view = LayoutInflater.from(this).inflate(R.layout.search_pop_view,null);
        ImageView refresh = view.findViewById(R.id.refresh);
        RecyclerView recyclerView = view.findViewById(R.id.search_content);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.8f);
        refresh.setVisibility(View.VISIBLE);
        searchViewModel.getSearchContentData(data).observe(this, contentResult -> {
            refresh.setVisibility(View.GONE);
            if(contentResult!=null){
                if(contentResult.getCode() == Config.FAIL)
                    Toast.makeText(this, "网络出错了", Toast.LENGTH_SHORT).show();
                else{
                    HistorySearchAdapter historySearchAdapter = new HistorySearchAdapter(this,contentResult.getResult());
                    recyclerView.setAdapter(historySearchAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
            }else   Toast.makeText(this, "网络出错了", Toast.LENGTH_SHORT).show();
        });
        popupWindow.setOnDismissListener(() -> {
            content.setFocusable(false);
            content.setFocusableInTouchMode(true);
            backgroundAlpha(1);
        });
        popupWindow.showAsDropDown(parent,0,0);
    }


}
