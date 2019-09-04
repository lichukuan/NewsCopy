package com.person.newscopy.news.depository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.ShortInNetProvide;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class ShortDepository implements ShortInNetProvide.OnShortLoadListener {

    private ShortInNetProvide provide;

    private Map<Integer,MutableLiveData<List<ShortInfoBean>>> map = new HashMap<>(4);

    public ShortDepository() {
        provide = ShortInNetProvide.getInstance();
        provide.setLoadListener(this);
    }

    public void pickShortData(int type){
        MutableLiveData<List<ShortInfoBean>> data = new MutableLiveData<>();
        map.put(type,data);
        provide.getShortInfo(type);
    }

    public LiveData<List<ShortInfoBean>> getData(int type) {
        return map.get(type);
    }

    @Override
    public void success(int type,List<ShortInfoBean> beans) {
        map.get(type).setValue(beans);
    }

    @Override
    public void error(Throwable e) {
        Log.d("==ShortDepository",e.getMessage());
        Toast.makeText(MyApplication.getContext(), "短视频出错了", Toast.LENGTH_SHORT).show();
    }
}
