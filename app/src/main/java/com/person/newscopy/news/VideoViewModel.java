package com.person.newscopy.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.person.newscopy.news.depository.VideoDepository;
import com.person.newscopy.news.network.bean.VideoChannelBean;
import com.person.newscopy.news.network.bean.VideoHotBean;
import com.person.newscopy.news.network.bean.VideoLiveBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;

public class VideoViewModel extends AndroidViewModel {

    private VideoDepository depository;

    public VideoViewModel(@NonNull Application application) {
        super(application);
        depository=new VideoDepository();
    }

    public LiveData<VideoHotBean> getHot(String type,String maxTime,int count){
        depository.pickVideoData(type,maxTime,count);
        return depository.getHotData();
    }

    public LiveData<VideoChannelBean> getChannel(String type,String maxTime,int count){
        depository.pickVideoData(type,maxTime,count);
        return depository.getChannelLiveData(type);
    }

    public LiveData<VideoSearchBean> getSearchInfo(String type,String maxTime,int count){
        depository.pickVideoData(type,maxTime,count);
        return depository.getSearchData();
    }

    public LiveData<VideoLiveBean> getLiveInfo(String maxTime,int count){
        depository.pickVideoData(VideoDepository.VIDEO_LIVE,maxTime,count);
        return depository.getLiveData();
    }
}
