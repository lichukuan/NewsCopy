package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.UserInfoStatePagerAdapter;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.ContentBean;
import com.person.newscopy.user.net.bean.ResultBean;

import java.util.ArrayList;
import java.util.List;
//用户的个人消息
public class UserInfoFragment extends Fragment {

    MyActivity myActivity;
    private List<String> tab = new ArrayList<>();
    ResultBean user;
    List<ContentBean> contents = new ArrayList<>();
    List<ContentBean> article = new ArrayList<>();
    List<ContentBean> video = new ArrayList<>();
    Button talk;
    TextView noFlag;
    TabLayout tabLayout;
    ViewPager viewPager;
    Button careButton;
    boolean isCare = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_all_user_info,container,false);
        myActivity = (MyActivity) getActivity();
        String searchId = myActivity.getSearchId();
        if (searchId == null){
            Toast.makeText(myActivity, "出错了", Toast.LENGTH_SHORT).show();
            myActivity.finish();
        }
        careButton = view.findViewById(R.id.care);
        talk = view.findViewById(R.id.talk);
        noFlag = view.findViewById(R.id.no_flag);
        tabLayout = view.findViewById(R.id.tab);
        viewPager = view.findViewById(R.id.pager);
        if (!Users.userId.equals(searchId))
        myActivity.queryIsCare(Users.userId,searchId).observe(this,baseResult -> {
            if (baseResult.getResult().equals("yes")){
                isCare = true;
                careButton.setText("已关注");
            }else{
                isCare = false;
                careButton.setText("关注");
            }
        });
        myActivity.queryRequireUserInfo(searchId).observe(this, otherUserInfo -> {
            List<ContentBean> b = otherUserInfo.getResult().getContent();
            user = otherUserInfo.getResult().getUser();
            contents.clear();
            contents.addAll(b);
            TextView releaseNum = view.findViewById(R.id.release_count);
            releaseNum.setText(user.getReleaseCount()+"投稿");
            TextView careNum = view.findViewById(R.id.care_count);
            careNum.setText(user.getCareCount()+"关注");
            TextView fansNum = view.findViewById(R.id.fans_count);
            fansNum.setText(user.getFansCount()+"粉丝");
            TextView recommend = view.findViewById(R.id.rec);
            recommend.setText(user.getRecommend());
            TextView name = view.findViewById(R.id.user_name);
            name.setText(user.getName());
            ShapeImageView icon = view.findViewById(R.id.icon);
            Glide.with(this)
                    .load(user.getIcon())
                    .asBitmap()
                    .into(icon);
            if (searchId.equals(Users.userId)){//查询自己的信息
                 careButton.setVisibility(View.GONE);
                 talk.setVisibility(View.GONE);
            }else {
                careButton.setVisibility(View.VISIBLE);
                talk.setVisibility(View.VISIBLE);
            }
            tab.clear();
            article.clear();
            video.clear();
            if (contents.size() > 0){
                noFlag.setVisibility(View.GONE);
                Log.d("========","加载全部");
                tab.add("全部");
                for (int i = 0; i < contents.size(); i++) {
                    final ContentBean contentBean = contents.get(i);
                    if (contentBean.getType() == Config.CONTENT.NEWS_TYPE)
                        article.add(contentBean);
                    else
                        video.add(contentBean);
                }
                if (article.size() > 0)tab.add("文章");
                if (video.size() > 0)tab.add("视频");

                for (String s : tab) {
                    tabLayout.addTab(tabLayout.newTab().setText(s));
                }
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setAdapter(new UserInfoStatePagerAdapter(getChildFragmentManager(),tab,contents,article,video));
            }else
                noFlag.setVisibility(View.VISIBLE);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        talk.setOnClickListener(v -> {
            myActivity.setPrivateTalkRequireId(user.getId());
            myActivity.changeFragment(MyActivity.PRIVATE_TALK_INFO_TYPE,true);
        });
        careButton.setOnClickListener( v ->{
            careButton.setClickable(false);
            isCare = !isCare;
            myActivity.careOrNot(Users.userId,myActivity.getSearchId(),isCare).observe(this,baseResult -> {
                if (baseResult.getCode() == Config.FAIL){
                    Toast.makeText(myActivity, baseResult.getResult(), Toast.LENGTH_SHORT).show();
                    isCare = !isCare;
                }else{
                    if (isCare)careButton.setText("已关注");
                    else careButton.setText("关注");
                }
                careButton.setClickable(true);
            });
        });
    }
}
