package com.person.newscopy.news.depository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.common.MyException;
import com.person.newscopy.news.network.VideoInNetProvide;
import com.person.newscopy.news.network.bean.VideoChannelBean;
import com.person.newscopy.news.network.bean.VideoHotBean;
import com.person.newscopy.news.network.bean.VideoLiveBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.news.network.bean.VideoType;
import com.person.newscopy.news.network.bean.VideoTypeBean;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VideoDepository implements VideoInNetProvide.VideoInfoLoadCallback {

    public static final String VIDEO_TYPE="video_type_depository";

    public static final String VIDEO_ALL_TYPE="all_video_type";

    public static final String VIDEO_SEARCH="type_search";

    public static final String VIDEO_HOT="type_hot";

    public static final String VIDEO_LIVE="type_live";

    public static boolean isInit = false;

    private VideoInNetProvide videoProvide=null;

    private Deque<String> videoTypeDeque=new LinkedList<>();

    private Map<String,MutableLiveData<VideoChannelBean>> channelMap=new HashMap<>();

    private MutableLiveData<VideoLiveBean> liveData=new MutableLiveData<>();

    private MutableLiveData<VideoHotBean> hotData=new MutableLiveData<>();

    private MutableLiveData<VideoSearchBean> searchData=new MutableLiveData<>();

    private MutableLiveData<VideoType> typeData=new MutableLiveData<>();

    public VideoDepository() {
        videoProvide = VideoInNetProvide.getInstance();
        videoProvide.setCallback(this);
    }

    public void initLoad(){
        videoProvide.feedVideoType();
    }

    public void pickVideoData(@NonNull String type){
        pickVideoData(type,null,0);
    }

    public void pickVideoData(@NonNull String type,String maxTime,int count){
       switch (type){
           case VIDEO_HOT:
               videoProvide.feedVideoHot();
               break;
           case VIDEO_LIVE:
               videoProvide.feedLiveVideoInfo();
               break;
           case VIDEO_SEARCH:
               videoProvide.feedVideoSearch();
               break;
               default:
                   videoTypeDeque.add(type);
                   if (!channelMap.containsKey(type)){
                       channelMap.put(type,new MutableLiveData<VideoChannelBean>());
                   }
                   String channelId=MyApplication.getContext()
                           .getSharedPreferences(VIDEO_TYPE,0)
                           .getString(type,"6797027941");
                   videoProvide.feedVideoChannel(channelId,maxTime,count);
                   break;
       }
    }

    public void updateVideoType(List<VideoTypeBean> beans){
        Set<String> types = new HashSet<>(21);
        for (int i = 0; i <beans.size() ; i++) {
            types.add(beans.get(i).getName());
        }
       SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(VIDEO_TYPE,0);
       SharedPreferences.Editor editor = preferences.edit();
       editor.putStringSet(VIDEO_ALL_TYPE,types);
        for (int i = 0; i < beans.size(); i++) {
            VideoTypeBean bean=beans.get(i);
            editor.putString(bean.getName(),bean.getChannel_id());
        }
        editor.apply();
        editor.commit();
    }

    public LiveData<VideoChannelBean> getChannelLiveData(String name) {
        return channelMap.get(name);
    }

    public LiveData<VideoLiveBean> getLiveData() {
        return liveData;
    }

    public LiveData<VideoHotBean> getHotData() {
        return hotData;
    }

    public LiveData<VideoSearchBean> getSearchData() {
        return searchData;
    }

    public LiveData<VideoType> getTypeData() {
        return typeData;
    }

    @Override
    public void success(VideoLiveBean videoLiveBean) {
         liveData.setValue(videoLiveBean);
    }

    @Override
    public void error(Throwable e) {
        Log.d("==VideoDepository==",e.getMessage());
    }

    @Override
    public void success(VideoType videoType) {
        updateVideoType( videoType.getData());
    }

    @Override
    public void success(VideoSearchBean videoSearchBean) {
        searchData.setValue(videoSearchBean);
    }

    @Override
    public void success(VideoHotBean videoHotBean) {
        hotData.setValue(videoHotBean);
    }

    @Override
    public void success(VideoChannelBean videoChannelBean) {
        String name = videoChannelBean.getData().getChannelInfo().getName();
        channelMap.get(name).setValue(videoChannelBean);
    }
}
