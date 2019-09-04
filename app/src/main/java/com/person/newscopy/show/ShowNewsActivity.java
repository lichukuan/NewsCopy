package com.person.newscopy.show;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;

public class ShowNewsActivity extends AppCompatActivity {

    private WebView webView;

    public static final String SHOW_WEB_INFO="show_web_info";

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)//如果为Android 5之后的版本
            ViewUtil.Translucent.applyGradualTranslucent(this,R.color.tool_bar_red);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        String showUrl = intent.getStringExtra(SHOW_WEB_INFO);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        webView = findViewById(R.id.show_news);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(showUrl);
    }
}
