package com.person.newscopy.type;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.person.newscopy.type.bean.ContentType;
import com.person.newscopy.type.bean.MessageType;
import com.person.newscopy.type.bean.NewsType;
import com.person.newscopy.type.bean.ShortVideoType;
import com.person.newscopy.type.bean.VideoType;
import com.person.newscopy.user.net.bean.OtherUserInfo;

public class TypeViewModel extends AndroidViewModel {
    
    private TypeDepository typeDepository;
    
    public TypeViewModel(@NonNull Application application) {
        super(application);
        typeDepository = new TypeDepository();
    }

    public LiveData<OtherUserInfo> login(String name, String pas, String salt){
        typeDepository.login(name,pas,salt);
        return typeDepository.getUserInfo();
    }

    public LiveData<NewsType> feedNewsType(){
        typeDepository.feedNewsType();
        return typeDepository.getNewsTypeData();
    }

    public LiveData<VideoType> feedVideoType(){
        typeDepository.feedVideoType();
        return typeDepository.getVideoTypeData();
    }

    public LiveData<ShortVideoType> feedShortVideoType(){
        typeDepository.feedShortVideoType();
        return typeDepository.getShortVideoTypeData();
    }

    public LiveData<MessageType> feedMessageType(){
        typeDepository.feedMessageType();
        return typeDepository.getMessageTypeData();
    }

    public LiveData<ContentType> feedContentType(){
        typeDepository.feedContentType();
        return typeDepository.getContentTypeData();
    }
    
}
