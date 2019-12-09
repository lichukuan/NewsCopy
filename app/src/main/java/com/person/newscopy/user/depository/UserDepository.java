package com.person.newscopy.user.depository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.UserInfoProvide;
import com.person.newscopy.user.net.bean.AllCareOrFans;
import com.person.newscopy.user.net.bean.AllPrivateTalkInfoBean;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.ContentBean;
import com.person.newscopy.user.net.bean.MessageBean;
import com.person.newscopy.user.net.bean.OtherUserBean;
import com.person.newscopy.user.net.bean.OtherUserInfo;
import com.person.newscopy.user.net.bean.ReadBean;
import com.person.newscopy.user.net.bean.SimpleTalkBean;
import com.person.newscopy.user.net.bean.SimpleTalkData;
import com.person.newscopy.user.net.bean.UserBean;
import com.person.newscopy.user.net.bean.VersionBean;

import java.io.File;
import java.util.List;
import java.util.Map;


public class UserDepository implements UserInfoProvide.OnUserInfoLoadCallback {

    public static final int REGISTER_TYPE = 0;

    public static final int UPLOAD_USER_ICON = 1;

    public static final int UPDATE_USER_RECOMMEND = 2;

    public static final int CHANGE_USER_NAME = 3;

    public static final int CHANGE_USER_PAS = 4;

    public static final int CHANGE_USER_ICON = 5;

    public static final int CARE_OR_NOT = 6;

    public static final int DELETE_READ = 7;

    public static final int ADD_PRIVATE_TALK = 8;

    public static final int LIKE = 9;

    public static final int SAVE = 10;

    public static final int UPLOAD_ARTICLE_IMAGES = 11;

    public static final int RELEASE_ARTICLE_RESULT = 12;

    public static final int QUERY_CARE_TYPE = 13;

    public static final int QUERY_FANS_TYPE = 14;

    public static final int CHANGE_USER_EMAIL = 15;

    public static final int GET_USER_PAS = 16;

    public static final int QUERY_IS_CARE = 17;

    private UserInfoProvide userInfoProvide;

    private SparseArray<MutableLiveData<BaseResult>> baseResultArray = new SparseArray<>(17);

    private SparseArray<MutableLiveData<AllCareOrFans>> caresOrfans = new SparseArray<>(2);

    private MutableLiveData<UserBean> userInfo = new MutableLiveData<>();

    private MutableLiveData<ReadBean> history = new MutableLiveData<>();

    private MutableLiveData<AllPrivateTalkInfoBean> privateTalkData = new MutableLiveData<>();

    private MutableLiveData<MessageBean> messageData = new MutableLiveData<>();

    private MutableLiveData<OtherUserInfo> otherUserData = new MutableLiveData<>();

    private MutableLiveData<SimpleTalkData> simpleTalkData = new MutableLiveData<>();

    public void register(String name,String email,String pas){
       userInfoProvide.register(name,email,pas);
    }

    public void login(String name,String pas,String salt){
       userInfoProvide.login(name,pas,salt);
    }

    public void like(String isLike, String userId, String contentId, String releaseUserId){
        userInfoProvide.like(isLike,userId,contentId,releaseUserId);
    }

    public void queryIsCare(String userId,String searchId){
        userInfoProvide.queryIsCare(userId,searchId);
    }

    public void save(String isSave, String userId, String contentId){
        userInfoProvide.save(isSave,userId,contentId);
    }

    public void uploadUserIcon(File file){
       userInfoProvide.uploadUserIcon(file);
    }

    public void updateUserRecommend(String userId,String recommend){
       userInfoProvide.updateUserRecommend(userId,recommend);
    }

    public void changeUserName(String id,String newName){
        userInfoProvide.changeUserName(id,newName);
    }

    public void changeUserPas(String id,String oldPas,String newPas,String salt){
       userInfoProvide.changeUserPas(id,oldPas,newPas,salt);
    }

    public void changeUserIcon(String userId,String url){
        userInfoProvide.changeUserIcon(userId,url);
    }

    public void careOrNot(String userId,String careUserId,boolean isCare){
         userInfoProvide.careOrNot(userId,careUserId,isCare);
    }

    public void deleteReadHistory(String contentIds){
        userInfoProvide.deleteReadHistory(contentIds);
    }

    public void addPrivateTalk(String userId,String to,String content){
        userInfoProvide.addPrivateTalk(userId,to,content);
    }

    public void queryHistory(String userId){
        userInfoProvide.queryHistory(userId);
    }

    public void queryAllPrivateTalk(String userId,String toId){
        userInfoProvide.queryAllPrivateTalk(userId,toId);
    }

    public void queryRequireUserInfo(String userId){
        userInfoProvide.queryRequireUserInfo(userId);
    }

    public MutableLiveData<ReadBean> getHistory() {
        return history;
    }

    public void queryMessage(String userId){
        userInfoProvide.queryMessage(userId);
    }

    public MutableLiveData<AllPrivateTalkInfoBean> getPrivateTalkData() {
        return privateTalkData;
    }

    public MutableLiveData<MessageBean> getMessageData() {
        return messageData;
    }

    public MutableLiveData<OtherUserInfo> getOtherUserData() {
        return otherUserData;
    }

    public UserDepository() {
        userInfoProvide = UserInfoProvide.getInstance();
        userInfoProvide.setCallback(this);
    }

    public LiveData<BaseResult> pickRequireData(int type){
         MutableLiveData<BaseResult> d = baseResultArray.get(type);
         if (d == null){
             d = new MutableLiveData<>();
             baseResultArray.put(type,d);
         }
         return d;
    }

    public MutableLiveData<UserBean> getUserInfo() {
        return userInfo;
    }

    public void querySimpleTalk(String userId){
        userInfoProvide.querySimpleTalk(userId);
    }

    @Override
    public void onSimpleTalk(SimpleTalkData simpleTalkBean) {
        simpleTalkData.setValue(simpleTalkBean);
    }

    public MutableLiveData<SimpleTalkData> getSimpleTalkData() {
        return simpleTalkData;
    }

    @Override
    public void onDeleteRead(BaseResult baseResult) {
        updateData(DELETE_READ,baseResult);
    }

    @Override
    public void onAddPrivateTalk(BaseResult baseResult) {
       updateData(ADD_PRIVATE_TALK,baseResult);
    }

    @Override
    public void onQueryPrivateTalk(AllPrivateTalkInfoBean allPrivateTalkInfoBean) {
         privateTalkData.setValue(allPrivateTalkInfoBean);
    }

    @Override
    public void onQueryRequireUserInfo(OtherUserInfo userInfoBean) {
       otherUserData.setValue(userInfoBean);
    }

    @Override
    public void onQueryMessage(MessageBean messageBeans) {
        messageData.setValue(messageBeans);
    }

    @Override
    public void onRegister(BaseResult baseResult) {
        updateData(REGISTER_TYPE,baseResult);
    }

    private void updateData(int type,BaseResult baseResult){
        MutableLiveData<BaseResult> r = baseResultArray.get(type);
        if (r == null)r = new MutableLiveData<>();
        r.setValue(baseResult);
        baseResultArray.setValueAt(type,r);
    }

    public void releaseArticle(String userId,  String content,String title,
                               String image,String imageList,String tag, String rec){
        userInfoProvide.releaseArticle(userId,content,title,image,imageList,tag,rec);
    }

    @Override
    public void onLogin(UserBean userInfoBean) {
        userInfo.setValue(userInfoBean);
    }

    @Override
    public void onUploadUserIcon(BaseResult baseResult) {
          updateData(UPLOAD_USER_ICON,baseResult);
    }

    @Override
    public void like(BaseResult baseResult) {
        updateData(LIKE,baseResult);
    }

    @Override
    public void save(BaseResult baseResult) {
         updateData(SAVE,baseResult);
    }

    @Override
    public void onQueryIsCare(BaseResult bean) {
        updateData(QUERY_IS_CARE,bean);
    }

    @Override
    public void onReleaseArticle(BaseResult baseResult) {
        updateData(RELEASE_ARTICLE_RESULT,baseResult);
    }


    public void queryCares(String userId){
        userInfoProvide.queryCare(userId);
    }

    public void queryFans(String userId){
        userInfoProvide.queryFans(userId);
    }

    public LiveData<AllCareOrFans> feedCaresOrFans(int type){
        MutableLiveData<AllCareOrFans> data = caresOrfans.get(type);
        if (data == null){
            data = new MutableLiveData<>();
            caresOrfans.put(type,data);
        }
        return data;
    }

    @Override
    public void onFeedCares(AllCareOrFans cares) {
        putCareOrFansData(QUERY_CARE_TYPE,cares);
    }

    private void putCareOrFansData(int type,AllCareOrFans value){
        MutableLiveData<AllCareOrFans> data = caresOrfans.get(type);
        if (data == null){
            data = new MutableLiveData<>();
            caresOrfans.put(type,data);
        }
        data.setValue(value);
    }

    public void changeUserEmail(String userId,String email){
        userInfoProvide.changeEmail(userId,email);
    }

    public void getUserPas(String email){
        userInfoProvide.getUserPas(email);
    }

    @Override
    public void onFeedFans(AllCareOrFans fans) {
        putCareOrFansData(QUERY_FANS_TYPE,fans);
    }

    @Override
    public void onChangeEmail(BaseResult baseResult) {
        updateData(CHANGE_USER_EMAIL,baseResult);
    }

    @Override
    public void error(Throwable e) {
        Log.d("UserDepository",e.getMessage());
    }

    @Override
    public void onGetUserPas(BaseResult baseResult) {
        updateData(GET_USER_PAS,baseResult);
    }

    @Override
    public void onUpdateUserRecommend(BaseResult baseResult) {
       updateData(UPDATE_USER_RECOMMEND,baseResult);
    }

    @Override
    public void onChangeUserName(BaseResult baseResult) {
      updateData(CHANGE_USER_NAME,baseResult);
    }

    @Override
    public void onChangeUserPas(BaseResult baseResult) {
      updateData(CHANGE_USER_PAS,baseResult);
    }

    @Override
    public void onChangeUserIcon(BaseResult baseResult) {
      updateData(CHANGE_USER_ICON,baseResult);
    }

    @Override
    public void onCareOrNot(BaseResult baseResult) {
       updateData(CARE_OR_NOT,baseResult);
    }

    private MutableLiveData<VersionBean> versionData = new MutableLiveData<>();

    public void queryNewVersion(){
        userInfoProvide.findNewVersion();
    }

    @Override
    public void newVersion(VersionBean version) {
        versionData.setValue(version);
    }

    @Override
    public void queryHistory(ReadBean result) {
        history.setValue(result);
    }

    public void UploadArticleImage(List<File> files){
        userInfoProvide.uploadArticleContentImage(files);
    }

    @Override
    public void onUploadArticleImage(BaseResult baseResult) {
        updateData(UPLOAD_ARTICLE_IMAGES,baseResult);
    }

    public MutableLiveData<VersionBean> getVersionData() {
        return versionData;
    }
}
