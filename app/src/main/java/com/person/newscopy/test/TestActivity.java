package com.person.newscopy.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.person.newscopy.R;
import com.person.newscopy.show.fragment.ShowArticleFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        ShowArticleFragment showArticleFragment = new ShowArticleFragment();
        showArticleFragment.setJson(data);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.parent,showArticleFragment)
                .commit();
    }
}
