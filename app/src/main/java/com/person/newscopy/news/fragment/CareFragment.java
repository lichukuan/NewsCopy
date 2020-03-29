package com.person.newscopy.news.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.MyTranslucentUtil;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.CareUserDataAdapter;
import com.person.newscopy.user.Users;

/**
 * 关注的人的消息
 */
public class CareFragment extends Fragment {

    NewsActivity activity;
    Button toLogin;
    RecyclerView recycler;
    CareUserDataAdapter adapter;
    private boolean isSlidingUpward = false;
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           activity = (NewsActivity) getActivity();
           MyTranslucentUtil.setTranslucent(activity, Color.parseColor("#fecc11"), (int) (25* ScreenFitUtil.getDensity()));
           View view = inflater.inflate(R.layout.fragment_main_care,container,false);
           toLogin = view.findViewById(R.id.to_login);
           recycler = view.findViewById(R.id.recycler);
           refreshLayout = view.findViewById(R.id.refresh);
           return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toLogin.setOnClickListener(v -> toLogin());
        if (Users.LOGIN_FLAG){
            toLogin.setVisibility(View.GONE);
            if (adapter == null)
                adapter = new CareUserDataAdapter();
            adapter.setFragment(this);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(activity));
            recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    // 当不滑动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的itemPosition
                        int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                        int itemCount = manager.getItemCount();
                        // 判断是否滑动到了最后一个item，并且是向上滑动
                        if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                            //加载更多
                            adapter.refresh();
                            activity.feedUserCareData(Users.userId,adapter.getDownTime(),"down").observe(CareFragment.this,newsResult -> {
                                adapter.addDownDataList(newsResult.getResult());
                            });

                        }
                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                    isSlidingUpward = dy > 0;
                }
            });
            if (!adapter.isInit()) {
                activity.feedUserCareData(Users.userId, 0, "up").observe(this, newsResult -> {
                    adapter.setDataBeanList(newsResult.getResult(), true);
                });
            }
        }
        else
            toLogin.setVisibility(View.VISIBLE);

        refreshLayout.setColorSchemeResources(R.color.main_color);//设置刷新进度条的颜色
        //设置监听
        refreshLayout.setOnRefreshListener(() -> {//当下拉时，会调用这个方法
            if (!Users.LOGIN_FLAG){
                Toast.makeText(activity, "请先登录", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
                return;
            }
            activity.feedUserCareData(Users.userId,adapter.getTopTime(),"up").observe(this, contentResult -> {
                adapter.addTopData(contentResult.getResult());
                refreshLayout.setRefreshing(false);//设置刷新进度条是否隐藏，false表示隐藏
            });
        });
    }

    private void toLogin(){
        Intent intent = new Intent(activity,MyActivity.class);
        intent.putExtra(MyActivity.MY_TYPE,MyActivity.LOGIN_TYPE);
        activity.startActivity(intent);
    }
}
