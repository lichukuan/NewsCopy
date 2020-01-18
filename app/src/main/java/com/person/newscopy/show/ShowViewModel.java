package com.person.newscopy.show;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.show.net.bean.ArticleDetail;
import com.person.newscopy.show.net.bean.AttitudeBean;
import com.person.newscopy.show.net.bean.CommentResult;
import com.person.newscopy.show.net.ShowDepository;
import com.person.newscopy.show.net.bean.NewAttitudeBean;
import com.person.newscopy.user.net.bean.BaseResult;

public class ShowViewModel extends AndroidViewModel {
    
    private ShowDepository showDepository;
    
    public ShowViewModel(@NonNull Application application) {
        super(application);
        showDepository = new ShowDepository();
    }

    public LiveData<BaseResult> addHistory(String userId, String contentId,int contentType ,String lookId){
        showDepository.addHistory(userId,contentId,contentType,lookId);
        return showDepository.pickData(ShowDepository.HISTORY_TYPE);
    }

    public LiveData<BaseResult> addMessage(String userId, int messageType, String fromUserId, String content,
                                           String image,  String contentId){
        showDepository.addMessage(userId,messageType,fromUserId,content,image,contentId);
        return showDepository.pickData(ShowDepository.MESSAGE_TYPE);
    }

    public LiveData<NewAttitudeBean> queryNewAttitude(String userId,String contentId,String contentUserId){
        showDepository.queryNewAttitude(userId,contentId,contentUserId);
        return showDepository.getNewAttitudeLiveData();
    }


    public LiveData<ContentResult> feedNewsRecommend(int contentType,String tag){
        showDepository.feedNewsRecommend(contentType,tag);
        return showDepository.getNewsData();
    }

    public LiveData<ContentResult> feedVideoRecommend(int contentType){
        showDepository.feedVideoRecommend(contentType);
        return showDepository.getVideoData();
    }

    public LiveData<AttitudeBean> queryContentAttitude(String contentId,String userId,String contentUserId){
        showDepository.queryContentAttitude(contentId,userId,contentUserId);
        return showDepository.getAttitude();
    }


    public LiveData<CommentResult> feedComment(String contentId){
        showDepository.feedComment(contentId);
        return showDepository.getCommentData();
    }

    public LiveData<BaseResult> careOrNot(String userId,String careUserId,boolean isCare){
        showDepository.careOrNot(userId,careUserId,isCare);
        return showDepository.pickData(ShowDepository.CARE_OR_NOT_TYPE);
    }

    public LiveData<BaseResult> like(String isLike, String userId, String contentId, String releaseUserId){
        showDepository.like(isLike,userId,contentId,releaseUserId);
        return showDepository.pickData(ShowDepository.LIKE_TYPE);
    }

    public LiveData<BaseResult> save(String isSave, String userId, String contentId){
        showDepository.save(isSave,userId,contentId);
        return showDepository.pickData(ShowDepository.SAVE_TYPE);
    }

    public LiveData<ArticleDetail> queryArticleDetail(String contentId,String userId,String contentUserId){
        showDepository.queryArticleDetail(contentId,userId,contentUserId);
        return showDepository.getArticleDetail();
    }

    public LiveData<BaseResult> addComment(String userId,String contentId,
                           String content,int contentType,String toId){
        showDepository.addComment(userId, contentId,content,contentType,toId);
        return showDepository.pickData(ShowDepository.ADD_COMMENT_TYPE);
    }
}
