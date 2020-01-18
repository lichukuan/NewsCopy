package com.person.newscopy.search.net;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.person.newscopy.news.network.bean.ContentResult;

public class SearchViewModel extends AndroidViewModel {

    private SearchDepository depository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        depository = new SearchDepository();
    }

    public LiveData<ContentResult> getSearchContentData(String content){
        depository.querySearchContent(content);
        return depository.getSearchData();
    }

    public LiveData<ContentResult> queryHotData(){
        depository.queryHotData();
        return depository.getHotData();
    }
}
