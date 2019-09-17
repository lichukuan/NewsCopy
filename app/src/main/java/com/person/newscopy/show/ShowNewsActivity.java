package com.person.newscopy.show;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;

public class ShowNewsActivity extends AppCompatActivity {

    private WebView webView;

    public static final String SHOW_WEB_INFO="show_web_info";

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        TranslucentUtil.setTranslucent(this,Color.WHITE, (int) (20*ScreenFitUtil.getDensity()));
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        String showUrl = intent.getStringExtra(SHOW_WEB_INFO);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        webView = findViewById(R.id.show_news);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(showUrl);
    }
}
