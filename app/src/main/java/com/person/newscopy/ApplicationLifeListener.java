package com.person.newscopy;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Bitmap;
import android.util.Log;

public class ApplicationLifeListener implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(){
        Log.d("=====","进入前台");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(LifecycleOwner owner){
        Log.d("======","进入后台");
    }

}
