package com.person.newscopy.show;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.person.newscopy.R;

public class ShowActivity extends AppCompatActivity {

    private WebView webView;
    public static final String SHOW_WEB_INFO="show_web_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        String showUrl = intent.getStringExtra(SHOW_WEB_INFO);
        webView = findViewById(R.id.show_news);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(showUrl);
    }
}
