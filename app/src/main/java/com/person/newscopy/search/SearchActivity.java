package com.person.newscopy.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.person.newscopy.R;

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_KEY = "key";

    public static final String SEARCH_ID = "value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
