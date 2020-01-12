package com.person.newscopy.user;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.depository.UserDepository;
import com.person.newscopy.user.net.bean.AllCareOrFans;
import com.person.newscopy.user.net.bean.AllPrivateTalkInfoBean;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.MessageBean;
import com.person.newscopy.user.net.bean.OneContentResult;
import com.person.newscopy.user.net.bean.OtherUserBean;
import com.person.newscopy.user.net.bean.OtherUserInfo;
import com.person.newscopy.user.net.bean.ReadBean;
import com.person.newscopy.user.net.bean.SimpleTalkBean;
import com.person.newscopy.user.net.bean.SimpleTalkData;
import com.person.newscopy.user.net.bean.UserBean;
import com.person.newscopy.user.net.bean.VersionBean;

import java.io.File;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    
    private UserDepository depository = null;
    
    public UserViewModel(@NonNull Application application) {
        super(application);
        depository = new UserDepository();
    }

    public LiveData<OneContentResult> getContent(int contentType, String contentId){
        depository.getContent(contentType,contentId);
        return depository.getContentResultMutableLiveData();
    }

    public LiveData<BaseResult> uploadArticleImages(List<File> files){
        depository.UploadArticleImage(files);
        return depository.pickRequireData(UserDepository.UPLOAD_ARTICLE_IMAGES);
    }

    public LiveData<BaseResult> changeUserEmail(String userId,String email){
        depository.changeUserEmail(userId,email);
        return depository.pickRequireData(UserDepository.CHANGE_USER_EMAIL);
    }

    public LiveData<BaseResult> queryIsCare(String userId,String searchId){
        depository.queryIsCare(userId,searchId);
        return depository.pickRequireData(UserDepository.QUERY_IS_CARE);
    }

    public LiveData<BaseResult> getUserPas(String email){
        depository.getUserPas(email);
        return depository.pickRequireData(UserDepository.GET_USER_PAS);
    }


    public LiveData<AllCareOrFans> feedCares(String userId){
        depository.queryCares(userId);
        return depository.feedCaresOrFans(UserDepository.QUERY_CARE_TYPE);
    }

    public LiveData<AllCareOrFans> feedFans(String userId){
        depository.queryFans(userId);
        return depository.feedCaresOrFans(UserDepository.QUERY_FANS_TYPE);
    }


    public LiveData<VersionBean> newVersion(){
      depository.queryNewVersion();
      return depository.getVersionData();
    }

    public LiveData<BaseResult> releaseArticle(String userId,  String content,String title,
                               String image,String imageList,String tag, String rec){
        depository.releaseArticle(userId,content,title,image,imageList,tag,rec);
        return depository.pickRequireData(UserDepository.RELEASE_ARTICLE_RESULT);
    }

    public LiveData<BaseResult> register(String name, String email,String pas){
        depository.register(name,email,pas);
        return depository.pickRequireData(UserDepository.REGISTER_TYPE);
    }

    public LiveData<UserBean> login(String name, String pas, String salt){
        depository.login(name,pas,salt);
        return depository.getUserInfo();
    }

    public LiveData<BaseResult> uploadUserIcon(File file){
        depository.uploadUserIcon(file);
        return depository.pickRequireData(UserDepository.UPLOAD_USER_ICON);
    }

    public LiveData<BaseResult> updateUserRecommend(String userId,String recommend){
        depository.updateUserRecommend(userId,recommend);
        return depository.pickRequireData(UserDepository.UPDATE_USER_RECOMMEND);
    }

    public LiveData<BaseResult> changeUserName(String id,String newName){
        depository.changeUserName(id,newName);
        return depository.pickRequireData(UserDepository.CHANGE_USER_NAME);
    }

    public LiveData<BaseResult> changeUserPas(String id,String oldPas,String newPas,String salt){
        depository.changeUserPas(id,oldPas,newPas,salt);
        return depository.pickRequireData(UserDepository.CHANGE_USER_PAS);
    }

    public LiveData<BaseResult> changeUserIcon(String userId,String url){
        depository.changeUserIcon(userId,url);
        return depository.pickRequireData(UserDepository.CHANGE_USER_ICON);
    }

    public LiveData<BaseResult> careOrNot(String userId,String careUserId,boolean isCare){
        depository.careOrNot(userId,careUserId,isCare);
        return depository.pickRequireData(UserDepository.CARE_OR_NOT);
    }

    public LiveData<ReadBean> queryHistory(String userId){
        depository.queryHistory(userId);
        return depository.getHistory();
    }

    public LiveData<BaseResult> deleteReadHistory(String contentIds){
        depository.deleteReadHistory(contentIds);
        return depository.pickRequireData(UserDepository.DELETE_READ);
    }

    public LiveData<BaseResult> addPrivateTalk(String userId,String to,String content){
        depository.addPrivateTalk(userId,to,content);
        return depository.pickRequireData(UserDepository.ADD_PRIVATE_TALK);
    }

    public LiveData<SimpleTalkData> querySimpleTalk(String userId){
        depository.querySimpleTalk(userId);
        return depository.getSimpleTalkData();
    }

    public LiveData<AllPrivateTalkInfoBean> queryAllPrivateTalk(String userId, String toId){
        depository.queryAllPrivateTalk(userId,toId);
        return depository.getPrivateTalkData();
    }

    public LiveData<OtherUserInfo> queryRequireUserInfo(String userId){
        depository.queryRequireUserInfo(userId);
        return depository.getOtherUserData();
    }

    public LiveData<MessageBean> queryMessage(String userId){
        depository.queryMessage(userId);
        return depository.getMessageData();
    }

    public LiveData<BaseResult> like(String isLike, String userId, String contentId, String releaseUserId){
        depository.like(isLike,userId,contentId,releaseUserId);
        return depository.pickRequireData(UserDepository.LIKE);
    }

    public LiveData<BaseResult> save(String isSave, String userId, String contentId){
        depository.save(isSave,userId,contentId);
        return depository.pickRequireData(UserDepository.SAVE);
    }

}
