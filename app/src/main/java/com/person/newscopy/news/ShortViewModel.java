package com.person.newscopy.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.person.newscopy.news.depository.ShortDepository;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.util.List;

public class ShortViewModel extends AndroidViewModel {

    public static final int TECH = 0;

    public static final int ENTERTAINMENT = 1;

    public static final int FINANCE = 2;

    public static final int KNOWLEDGE = 3;

    private ShortDepository depository = new ShortDepository();

    public ShortViewModel(@NonNull Application application) {
        super(application);
    }

    public void pickShortData(int type){
       depository.pickShortData(type);
    }

    public LiveData<List<ShortInfoBean>> getShortData(int type){
        depository.pickShortData(type);
        return depository.getData(type);
    }
}
