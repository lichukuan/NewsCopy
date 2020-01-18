package com.person.newscopy.search.net;

import android.arch.lifecycle.MutableLiveData;

import com.person.newscopy.news.network.bean.ContentResult;

public class SearchDepository implements SearchProvide.CallBackListener {

    MutableLiveData<ContentResult> searchData = new MutableLiveData<>();

    MutableLiveData<ContentResult> hotData = new MutableLiveData<>();

    private SearchProvide provide;

    public SearchDepository(){
        provide = SearchProvide.getInstance();
        provide.setCallBackListener(this);
    }

    public MutableLiveData<ContentResult> getSearchData() {
        return searchData;
    }

    public MutableLiveData<ContentResult> getHotData() {
        return hotData;
    }

    public void querySearchContent(String content){
        provide.querySearchContent(content);
    }

    public void queryHotData(){
        provide.queryHot();
    }

    @Override
    public void onQuerySearchContent(ContentResult contentResult) {
        searchData.setValue(contentResult);
    }

    @Override
    public void onQueryHotData(ContentResult contentResult) {
        hotData.setValue(contentResult);
    }

    @Override
    public void error(Throwable e) {

    }
}
