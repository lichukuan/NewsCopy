package com.person.newscopy.show.net;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.show.net.bean.ArticleDetail;
import com.person.newscopy.show.net.bean.AttitudeBean;
import com.person.newscopy.show.net.bean.CommentResult;
import com.person.newscopy.show.net.bean.NewAttitudeBean;
import com.person.newscopy.user.net.bean.BaseResult;

public class ShowDepository implements ShowProvide.OnShowCallback{

    private ShowProvide showProvide;

    public static final int HISTORY_TYPE = 0;

    public static final int MESSAGE_TYPE = 1;

    public static final int CARE_OR_NOT_TYPE = 2;

    public static final int LIKE_TYPE = 3;

    public static final int SAVE_TYPE = 4;

    @Deprecated
    public static final int ARTICLE_DETAIL_TYPE = 5;

    public static final int ADD_COMMENT_TYPE = 6;

    private SparseArray<MutableLiveData<BaseResult>> arrayData = new SparseArray<>(7);

    private MutableLiveData<ContentResult> newsData = new MutableLiveData<>();

    private MutableLiveData<ContentResult> videoData = new MutableLiveData<>();

    private MutableLiveData<CommentResult> commentData = new MutableLiveData<>();

    private MutableLiveData<ArticleDetail> articleDetail = new MutableLiveData<>();

    private MutableLiveData<AttitudeBean> attitude = new MutableLiveData<>();

    private MutableLiveData<NewAttitudeBean> newAttitudeBeanMutableLiveData = new MutableLiveData<>();

    public ShowDepository() {
        showProvide = ShowProvide.getInstance();
        showProvide.setCallback(this);
    }

    public MutableLiveData<BaseResult> pickData(int type) {
        MutableLiveData<BaseResult> liveData = arrayData.get(type);
        if (liveData == null){
            liveData = new MutableLiveData<>();
            arrayData.put(type,liveData);
        }
        return liveData;
    }

    public MutableLiveData<ContentResult> getNewsData() {
        return newsData;
    }

    public MutableLiveData<ContentResult> getVideoData() {
        return videoData;
    }

    public MutableLiveData<CommentResult> getCommentData() {
        return commentData;
    }

    public void addHistory(String userId, String contentId,int contentType ,String lookId){
        showProvide.addHistory(userId,contentId,contentType,lookId);
    }

    public void addMessage(String userId, int messageType, String fromUserId, String content,
                           String image,  String contentId){
       showProvide.addMessage(userId,messageType,fromUserId,content,image,contentId);
    }

    public void feedNewsRecommend( int contentType,String tag){
       showProvide.feedNewsRecommend(contentType,tag);
    }

    public void feedVideoRecommend(int contentType){
       showProvide.feedVideoRecommend(contentType);
    }

    public void feedComment(String contentId){
      showProvide.feedComment(contentId);
    }

    public void careOrNot(String userId,String careUserId,boolean isCare){
       showProvide.careOrNot(userId,careUserId,isCare);
    }

    public void like(String isLike, String userId, String contentId, String releaseUserId){
        showProvide.like(isLike,userId,contentId,releaseUserId);
    }

    public MutableLiveData<ArticleDetail> getArticleDetail() {
        return articleDetail;
    }

    public void save(String isSave, String userId, String contentId){
        showProvide.save(isSave,userId,contentId);
    }

    public void queryArticleDetail(String contentId,String userId,String contentUserId){
        showProvide.queryArticleDetail(contentId,userId,contentUserId);
    }

    public void addComment(String userId,String contentId,
                           String content,int contentType,String toId){
        showProvide.addComment(userId, contentId,content,contentType,toId);
    }

    @Override
    public void onHistoryLoad(BaseResult baseResult) {
         dataChange(baseResult,HISTORY_TYPE);
    }

    @Override
    public void like(BaseResult baseResult) {
        dataChange(baseResult,LIKE_TYPE);
    }


    public void queryContentAttitude(String contentId,String userId,String contentUserId){
        showProvide.queryContentAttitude(contentId,userId,contentUserId);
    }

    @Override
    public void onQueryAttitude(AttitudeBean bean) {
        attitude.setValue(bean);
    }

    public MutableLiveData<AttitudeBean> getAttitude() {
        return attitude;
    }

    @Override
    public void save(BaseResult baseResult) {
        dataChange(baseResult,SAVE_TYPE);
    }

    private void dataChange(BaseResult baseResult,int type){
        MutableLiveData<BaseResult> liveData = arrayData.get(type);
        liveData.setValue(baseResult);
    }

    @Override
    public void onMessageLoad(BaseResult baseResult) {
          dataChange(baseResult,MESSAGE_TYPE);
    }

    @Override
    public void onNewsRecommendLoad(ContentResult newsResult) {
         newsData.setValue(newsResult);
    }

    @Override
    public void onVideoRecommendLoad(ContentResult videoResult) {
       videoData.setValue(videoResult);
    }

    @Override
    public void onCommentLoad(CommentResult commentResultBean) {
      commentData.setValue(commentResultBean);
    }

    @Override
    public void onCareOrNot(BaseResult baseResult) {
       dataChange(baseResult,CARE_OR_NOT_TYPE);
    }

    @Override
    public void error(Throwable e) {
        Log.d("ShowDepository",e.getMessage());
    }

    @Override
    public void queryArticleDetail(ArticleDetail detail) {
       articleDetail.setValue(detail);
    }

    @Override
    public void onAddComment(BaseResult baseResult) {
        dataChange(baseResult,ADD_COMMENT_TYPE);
    }

    public void queryNewAttitude(String userId,String contentId,String contentUserId){
        showProvide.queryNewAttitude(userId,contentId,contentUserId);
    }

    public MutableLiveData<NewAttitudeBean> getNewAttitudeLiveData() {
        return newAttitudeBeanMutableLiveData;
    }

    @Override
    public void onQueryNewAttitude(NewAttitudeBean newAttitudeBean) {
        newAttitudeBeanMutableLiveData.setValue(newAttitudeBean);
    }
}
