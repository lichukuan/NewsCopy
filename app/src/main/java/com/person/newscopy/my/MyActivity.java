package com.person.newscopy.my;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.my.fragment.ChangePasFragment;
import com.person.newscopy.my.fragment.CommentFragment;
import com.person.newscopy.my.fragment.FansFragment;
import com.person.newscopy.my.fragment.FeedbackFragment;
import com.person.newscopy.my.fragment.GetUserPasFragment;
import com.person.newscopy.my.fragment.HistoryFragment;
import com.person.newscopy.my.fragment.LikeFragment;
import com.person.newscopy.my.fragment.LoginFragment;
import com.person.newscopy.my.fragment.MessageFragment;
import com.person.newscopy.my.fragment.MoreFragment;
import com.person.newscopy.my.fragment.MyCareFragment;
import com.person.newscopy.my.fragment.MyInfoFragment;
import com.person.newscopy.my.fragment.OnePrivateMessageFragment;
import com.person.newscopy.my.fragment.PrivateMessageFragment;
import com.person.newscopy.my.fragment.PushArticleFragment;
import com.person.newscopy.my.fragment.SaveFragment;
import com.person.newscopy.my.fragment.SettingFragment;
import com.person.newscopy.my.fragment.UserInfoFragment;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.UserViewModel;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.AllCareOrFans;
import com.person.newscopy.user.net.bean.AllPrivateTalkInfoBean;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.MessageBean;
import com.person.newscopy.user.net.bean.OneContentResult;
import com.person.newscopy.user.net.bean.OtherUserInfo;
import com.person.newscopy.user.net.bean.ReadBean;
import com.person.newscopy.user.net.bean.SimpleTalkBean;
import com.person.newscopy.user.net.bean.SimpleTalkData;
import com.person.newscopy.user.net.bean.UserBean;
import com.person.newscopy.user.net.bean.VersionBean;

import java.io.File;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    public static final String MY_TYPE = "show_type";

    public static final int LOGIN_TYPE = 0;

    public static final int REGISTER_TYPE = 1;

    public static final int MESSAGE_TYPE = 2;

    public static final int PRIVATE_TALK_TYPE = 3;

    public static final int SAVE_TYPE = 4;

    public static final int HISTORY_TYPE = 5;

    public static final int FEEDBACK_TYPE = 6;

    public static final int USER_WORK_TYPE = 7;

    @Deprecated
    public static final int MORE_TYPE = 8;

    public static final int SETTING_TYPE = 9;

    public static final int COMMENT_TYPE = 10;

    public static final int LIKE_TYPE = 11;

    public static final int MY_INFO_TYPE = 12;

    public static final int PRIVATE_TALK_INFO_TYPE = 13;

    public static final int CHANGE_PASSWORD_TYPE = 14;

    public static final int CARE_TYPE = 15;

    public static final int FANS_TYPE = 16;

    public static final int RELEASE_ARTICLE = 17;

    public static final int GET_USER_PAS = 18;

    private Fragment fragment;

    //用来传递私聊所需要的数据
    private String privateTalkRequireId = null;
    private String leftName;
    private String leftIcon;
    //用来查询的id
    public static final String SEARCH_KEY = "search_key";
    private String searchId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //android.R.color.dimen.notification_large_icon_width
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        TranslucentUtil.setTranslucent(this, Color.WHITE, (int) (25*ScreenFitUtil.getDensity()));
        setContentView(R.layout.activity_my);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Intent intent = getIntent();
        int type = intent.getIntExtra(MY_TYPE,-1);
        searchId = intent.getStringExtra(SEARCH_KEY);
        updateFragment(type);
        addFragment();
    }

    public String getSearchId() {
        return searchId;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(String leftIcon) {
        this.leftIcon = leftIcon;
    }

    public String getPrivateTalkRequireId() {
        return privateTalkRequireId;
    }

    public void setPrivateTalkRequireId(String privateTalkRequireId) {
        this.privateTalkRequireId = privateTalkRequireId;
    }

    private void updateFragment(int type){
        switch (type){
            case LOGIN_TYPE:
                fragment = new LoginFragment();
                break;
            case MESSAGE_TYPE:
                fragment = new MessageFragment();
                break;
            case MORE_TYPE:
                fragment = new MoreFragment();
                break;
            case PRIVATE_TALK_TYPE:
                fragment = new PrivateMessageFragment();
                break;
            case SAVE_TYPE:
                fragment = new SaveFragment();
                break;
            case HISTORY_TYPE:
                fragment = new HistoryFragment();
                break;
            case LIKE_TYPE:
                fragment = new LikeFragment();
                break;
            case COMMENT_TYPE:
                 fragment = new CommentFragment();
                 break;
            case FEEDBACK_TYPE:
                fragment = new FeedbackFragment();
                break;
            case USER_WORK_TYPE:
                fragment = new UserInfoFragment();
                break;
            case SETTING_TYPE:
                fragment = new SettingFragment();
                break;
            case MY_INFO_TYPE:
                fragment = new MyInfoFragment();
                break;
            case PRIVATE_TALK_INFO_TYPE:
                fragment = new OnePrivateMessageFragment();
                break;
            case CHANGE_PASSWORD_TYPE:
                fragment = new ChangePasFragment();
                break;
            case CARE_TYPE:
                fragment = new MyCareFragment();
                break;
            case FANS_TYPE:
                fragment = new FansFragment();
                break;
            case RELEASE_ARTICLE:
                fragment = new PushArticleFragment();
                break;
            case GET_USER_PAS:
                fragment = new GetUserPasFragment();
                break;
        }
    }


    public void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.my_fragment,fragment);
        transaction.commit();
    }

    public void addFragmentToBackStack(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.my_fragment,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void back(){
        getSupportFragmentManager().popBackStack();
    }

    public void changeFragment(int type,boolean isToStack){
        fragment = null;
        updateFragment(type);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.my_fragment,fragment);
        if (isToStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment = null;
    }

    public LiveData<BaseResult> register(String name,String email ,String pas){
        return userViewModel.register(name,email,pas);
    }

    public LiveData<UserBean> login(String name, String pas, String salt){
        return userViewModel.login(name,pas,salt);
    }

    public LiveData<BaseResult> uploadUserIcon(File file){
       return userViewModel.uploadUserIcon(file);
    }

    public LiveData<BaseResult> changeUserIcon(String url){
        return userViewModel.changeUserIcon(Users.userId,url);
    }

    public LiveData<BaseResult> changeUserEmail(String userId,String email){
        return userViewModel.changeUserEmail(userId,email);
    }

    public LiveData<BaseResult> getUserPas(String email){
        return userViewModel.getUserPas(email);
    }

    public LiveData<BaseResult> updateUserRecommend(String userId,String recommend){
       return userViewModel.updateUserRecommend(userId,recommend);
    }

    public LiveData<OneContentResult> getContent(int contentType, String contentId){
        return userViewModel.getContent(contentType,contentId);
    }

    public LiveData<BaseResult> changeUserName(String id,String newName){
       return userViewModel.changeUserName(id,newName);
    }

    public LiveData<BaseResult> changeUserPas(String id,String oldPas,String newPas,String salt){
       return userViewModel.changeUserPas(id,oldPas,newPas,salt);
    }

    public LiveData<ReadBean> queryHistory(String userId){
        return userViewModel.queryHistory(userId);
    }


    public LiveData<BaseResult> careOrNot(String userId,String careUserId,boolean isCare){
       return userViewModel.careOrNot(userId,careUserId,isCare);
    }


    public LiveData<BaseResult> deleteReadHistory(String contentIds){
        return userViewModel.deleteReadHistory(contentIds);
    }

    public LiveData<AllCareOrFans> queryAllCares(String userId){
        return userViewModel.feedCares(userId);
    }

    public LiveData<SimpleTalkData> querySimpleTalk(String userId){
        return userViewModel.querySimpleTalk(userId);
    }

    public LiveData<AllCareOrFans> queryAllFans(String userId){
        return userViewModel.feedFans(userId);
    }

    public LiveData<BaseResult> addPrivateTalk(String userId,String to,String content){
        return userViewModel.addPrivateTalk(userId,to,content);
    }

    public LiveData<AllPrivateTalkInfoBean> queryAllPrivateTalk(String userId, String toId){
        return userViewModel.queryAllPrivateTalk(userId,toId);
    }

    public LiveData<BaseResult> queryIsCare(String userId,String searchId){
        return userViewModel.queryIsCare(userId,searchId);
    }

    public LiveData<OtherUserInfo> queryRequireUserInfo(String userId){
        return userViewModel.queryRequireUserInfo(userId);
    }

    public LiveData<VersionBean> findNewVersion(){
        return userViewModel.newVersion();
    }

    public LiveData<MessageBean> queryMessage(String userId){
        return userViewModel.queryMessage(userId);
    }

    public LiveData<BaseResult> like(String isLike, String userId, String contentId, String releaseUserId){
       return userViewModel.like(isLike,userId,contentId,releaseUserId);
    }

    public LiveData<BaseResult> save(String isSave, String userId, String contentId){
        return userViewModel.save(isSave,userId,contentId);
    }

    public LiveData<BaseResult> uploadArticleImages(List<File> files){
        return userViewModel.uploadArticleImages(files);
    }

    public LiveData<BaseResult> deleteSave(String userId,String contentId){
        return userViewModel.deleteSave(userId,contentId);
    }

    public LiveData<ContentResult> querySave(String userId){
        return userViewModel.querySave(userId);
    }

    public LiveData<BaseResult> releaseArticle(String userId,  String content,String title,
                                               String image,String imageList,String tag, String rec){
        return userViewModel.releaseArticle(userId,content,title,image,imageList,tag,rec);
    }

}
