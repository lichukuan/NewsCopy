package com.person.newscopy.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.person.newscopy.R;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        findViewById(R.id.back).setOnClickListener(v->finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
