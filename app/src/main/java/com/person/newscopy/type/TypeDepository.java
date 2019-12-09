package com.person.newscopy.type;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.person.newscopy.type.bean.ContentType;
import com.person.newscopy.type.bean.MessageType;
import com.person.newscopy.type.bean.NewsType;
import com.person.newscopy.type.bean.ShortVideoType;
import com.person.newscopy.type.bean.VideoType;
import com.person.newscopy.user.net.bean.OtherUserInfo;

public class TypeDepository implements TypeProvide.OnTypeLoadCallback{

    private TypeProvide provide = null;

    private MutableLiveData<OtherUserInfo> userInfo = new MutableLiveData<>();

    private MutableLiveData<NewsType> newsTypeData = new MutableLiveData<>();

    private MutableLiveData<VideoType> videoTypeData = new MutableLiveData<>();

    private MutableLiveData<ShortVideoType> shortVideoTypeData = new MutableLiveData<>();

    private MutableLiveData<MessageType> messageTypeData = new MutableLiveData<>();

    private MutableLiveData<ContentType> contentTypeData = new MutableLiveData<>();

    public MutableLiveData<OtherUserInfo> getUserInfo() {
        return userInfo;
    }

    public MutableLiveData<NewsType> getNewsTypeData() {
        return newsTypeData;
    }

    public MutableLiveData<VideoType> getVideoTypeData() {
        return videoTypeData;
    }

    public MutableLiveData<ShortVideoType> getShortVideoTypeData() {
        return shortVideoTypeData;
    }

    public MutableLiveData<MessageType> getMessageTypeData() {
        return messageTypeData;
    }

    public MutableLiveData<ContentType> getContentTypeData() {
        return contentTypeData;
    }

    public TypeDepository() {
        provide = TypeProvide.getInstance();
        provide.setCallback(this);
    }

    public void feedNewsType(){
        provide.feedNewsType();
    }

    public void feedVideoType(){
        provide.feedVideoType();
    }

    public void feedShortVideoType(){
        provide.feedShortVideoType();
    }

    public void feedMessageType(){
        provide.feedMessageType();
    }

    public void feedContentType(){
        provide.feedContentType();
    }

    @Override
    public void onNewsTypeLoad(NewsType newsType) {
        newsTypeData.setValue(newsType);
    }

    @Override
    public void onVideoTypeLoad(VideoType videoType) {
        videoTypeData.setValue(videoType);
    }

    @Override
    public void onShortVideoTypeLoad(ShortVideoType shortVideoType) {
        shortVideoTypeData.setValue(shortVideoType);
    }

    public void login(String name,String pas,String salt){
        provide.login(name,pas,salt);
    }
    @Override
    public void onMessageTypeLoad(MessageType messageType) {
       messageTypeData.setValue(messageType);
    }

    @Override
    public void onContentTypeLoad(ContentType contentType) {
     contentTypeData.setValue(contentType);
    }

    @Override
    public void onLogin(OtherUserInfo userInfoBean) {
        userInfo.setValue(userInfoBean);
    }

    @Override
    public void error(Throwable e) {
        Log.d("TypeDepository",e.getMessage());
    }
}
