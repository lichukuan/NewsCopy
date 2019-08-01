package com.person.newscopy.news.depository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.ShortInNetProvide;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.util.List;

public class ShortDepository implements ShortInNetProvide.OnShortLoadListener {

    private MutableLiveData<List<ShortInfoBean>> data = new MutableLiveData<>();

    private ShortInNetProvide provide;

    public ShortDepository() {
        provide = ShortInNetProvide.getInstance();
        provide.setLoadListener(this);
    }

    public void pickShortData(int type){
        provide.getShortInfo(type);
    }

    public LiveData<List<ShortInfoBean>> getData() {
        return data;
    }

    @Override
    public void success(List<ShortInfoBean> beans) {
        data.setValue(beans);
    }

    @Override
    public void error(Throwable e) {
        Log.d("==ShortDepository",e.getMessage());
        Toast.makeText(MyApplication.getContext(), "短视频出错了", Toast.LENGTH_SHORT).show();
    }
}
