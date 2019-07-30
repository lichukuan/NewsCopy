package com.person.newscopy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.depository.NewsDepository;
import com.person.newscopy.news.depository.VideoDepository;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class WelcomeActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public static final int DELAY_TIME = 1;

    Subscription subscription;

    NewsDepository newsDepository;

    VideoDepository videoDepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        newsDepository = new NewsDepository();
//        videoDepository = new VideoDepository();
//        newsDepository.initLoad();
//        videoDepository.initLoad();
        subscription = Observable.timer(DELAY_TIME, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    startActivity(new Intent(this, NewsActivity.class));
                    subscription.unsubscribe();
                    finish();
                });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
