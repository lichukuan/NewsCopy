package com.person.newscopy.user.net;

import com.person.newscopy.api.Api;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.BaseInterceptor;
import com.person.newscopy.news.network.ContentProvide;
import com.person.newscopy.user.net.bean.AllCareOrFans;
import com.person.newscopy.user.net.bean.AllPrivateTalkInfoBean;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.MessageBean;
import com.person.newscopy.user.net.bean.OtherUserInfo;
import com.person.newscopy.user.net.bean.ReadBean;
import com.person.newscopy.user.net.bean.SimpleTalkBean;
import com.person.newscopy.user.net.bean.SimpleTalkData;
import com.person.newscopy.user.net.bean.UserBean;
import com.person.newscopy.user.net.bean.VersionBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class UserInfoProvide {

    private static UserInfoProvide provide = new UserInfoProvide();

    private static UserInterface userInterface = null;

    public static final String USER_CACHE_STORAGE = "user_cache";

    private OnUserInfoLoadCallback callback;

    private UserInfoProvide(){}

    public void setCallback(OnUserInfoLoadCallback callback) {
        this.callback = callback;
    }

    public static UserInfoProvide getInstance(){
        if (userInterface == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .client(new OkHttpClient()
                            .newBuilder()
                            .cache(new Cache(new File(MyApplication.getContext().getCacheDir(),USER_CACHE_STORAGE), ContentProvide.MAX_CACHE_SPACE))
                            .addInterceptor(new BaseInterceptor())
                            .addNetworkInterceptor(new BaseInterceptor())
                            .build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            userInterface = retrofit.create(UserInterface.class);
        }
        return provide;
    }

    public void changeEmail(String userId,String email){
        userInterface.changeUserEmail(userId,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onChangeEmail(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void queryIsCare(String userId,String searchId){
        userInterface.queryIsCare(userId,searchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onQueryIsCare(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void getUserPas(String email){
        userInterface.getUserPas(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onGetUserPas(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void queryCare(String userId){
        userInterface.queryCare(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onFeedCares(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });

    }

    public void queryFans(String userId){
        userInterface.queryFans(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onFeedFans(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });

    }

    public void uploadArticleContentImage(List<File> files){
        Map<String, RequestBody> map = new HashMap<>();
        int i = 1;
        for (File file:files){
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            //注意这里
            map.put("image"+i+"\""+"; filename=\"\"",requestBody);
            i++;
        }
        userInterface.uploadArticleImage(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onUploadArticleImage(baseResult);
                    }
                });
    }

    public void register(String name,String email,String pas){
        userInterface.register(name,email,pas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onRegister(baseResult);
                    }
                });
    }


    public void releaseArticle(String userId,  String content,String title,
                               String image,String imageList,String tag, String rec){
        userInterface.releaseArticle(userId,content,title,image,imageList,tag,rec)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onReleaseArticle(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void login(String name,String pas,String salt){
        userInterface.login(name,pas,salt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(UserBean userInfoBean) {
                       if (callback!=null)callback.onLogin(userInfoBean);
                    }
                });
    }

    public void uploadUserIcon(File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("icon", file.getName(), requestBody);
        userInterface.uploadUserIcon(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                       if (callback!=null)callback.onUploadUserIcon(baseResult);
                    }
                });
    }


    public void queryHistory(String userId){
        userInterface.queryReadHistory(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.queryHistory(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });

    }

    public void updateUserRecommend(String userId,String recommend){
        userInterface.updateUserRecommend(userId,recommend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                      if (callback!=null)callback.onUpdateUserRecommend(baseResult);
                    }
                });
    }

    public void changeUserName(String id,String newName){
        userInterface.changeUserName(id,newName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onChangeUserName(baseResult);
                    }
                });
    }

    public void changeUserPas(String id,String oldPas,String newPas,String salt){
        userInterface.changeUserPas(oldPas,newPas,id,salt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                       if (callback!=null)callback.onChangeUserPas(baseResult);
                    }
                });
    }

    public void changeUserIcon(String userId,String url){
          userInterface.changeUserIcon(userId,url)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<BaseResult>() {
                      @Override
                      public void onCompleted() {

                      }

                      @Override
                      public void onError(Throwable e) {
                       if (callback!=null)callback.error(e);
                      }

                      @Override
                      public void onNext(BaseResult baseResult) {
                       if (callback!=null)callback.onChangeUserIcon(baseResult);
                      }
                  });
    }

    public void careOrNot(String userId,String careUserId,boolean isCare){
        userInterface.careOrNot(userId,careUserId,String.valueOf(isCare))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                      if (callback!=null)callback.onCareOrNot(baseResult);
                    }
                });
    }

    public void deleteReadHistory(String contentIds){
        userInterface.deleteReadHistory(contentIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                     if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onDeleteRead(baseResult);
                    }
                });
    }

    public void addPrivateTalk(String userId,String to,String content){
        userInterface.addPrivateTalk(userId,to,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onAddPrivateTalk(baseResult);
                    }
                });
    }

    public void queryAllPrivateTalk(String userId,String toId){
        userInterface.queryAllPrivateTalk(userId,toId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AllPrivateTalkInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(AllPrivateTalkInfoBean allPrivateTalkInfoBean) {
                        if (callback!=null)callback.onQueryPrivateTalk(allPrivateTalkInfoBean);
                    }
                });
    }

    public void queryRequireUserInfo(String userId){
        userInterface.queryRequireUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OtherUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(OtherUserInfo userInfoBean) {
                        if (callback!=null)callback.onQueryRequireUserInfo(userInfoBean);
                    }
                });
    }

    public void queryMessage(String userId){
        userInterface.queryMessages(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(MessageBean messageBeans) {
                        if (callback!=null)callback.onQueryMessage(messageBeans);
                    }
                });
    }

    public void findNewVersion(){
        userInterface.queryNewestVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(versionBean -> {
                    callback.newVersion(versionBean);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void like(String isLike, String userId, String contentId, String releaseUserId){
        userInterface.like(isLike,userId,contentId,releaseUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.like(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void save(String isSave, String userId, String contentId){
        userInterface.save(isSave,userId,contentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.save(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void querySimpleTalk(String userId){
        userInterface.querySimpleTalkBean(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleTalkBean -> {
                    callback.onSimpleTalk(simpleTalkBean);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public interface OnUserInfoLoadCallback{

        void onSimpleTalk(SimpleTalkData simpleTalkBean);

        void onDeleteRead(BaseResult baseResult);

        void onAddPrivateTalk(BaseResult baseResult);

        void onQueryPrivateTalk(AllPrivateTalkInfoBean allPrivateTalkInfoBean);

        void onQueryRequireUserInfo(OtherUserInfo userInfoBean);

        void onQueryMessage(MessageBean messageBeans);

        void onRegister(BaseResult baseResult);

        void onLogin(UserBean userInfoBean);

        void onUploadUserIcon(BaseResult baseResult);

        void like(BaseResult baseResult);

        void save(BaseResult baseResult);

        void onQueryIsCare(BaseResult bean);

        void onReleaseArticle(BaseResult baseResult);

        void onFeedCares(AllCareOrFans cares);

        void onFeedFans(AllCareOrFans fans);

        void onChangeEmail(BaseResult baseResult);

        void error(Throwable e);

        void onGetUserPas(BaseResult baseResult);

        void onUpdateUserRecommend(BaseResult baseResult);

        void onChangeUserName(BaseResult baseResult);

        void onChangeUserPas(BaseResult baseResult);

        void onChangeUserIcon(BaseResult baseResult);

        void onCareOrNot(BaseResult baseResult);

        void newVersion(VersionBean version);

        void queryHistory(ReadBean result);

        void onUploadArticleImage(BaseResult baseResult);
    }

}
