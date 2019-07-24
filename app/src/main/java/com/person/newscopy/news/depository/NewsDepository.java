package com.person.newscopy.news.depository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.NewsInNetProvide;
import com.person.newscopy.news.network.NewsType;
import com.person.newscopy.news.network.bean.CommentBean;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.NewsBean;
import com.person.newscopy.news.network.bean.ReplyBean;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NewsDepository implements NewsInNetProvide.onNewsLoadCallback{

    public static final String[] values={"推荐","热点","科技","国际","时政","彩票","运动","社会","家居","互联网","软件","娱乐"};

    public static boolean isloadCache = false;

    public static final int INIT_LOAD_COUNT = 5;

    private NewsInNetProvide newsInNetProvide=null;

    private Deque<String> pickNewsDeque = new LinkedList<>();

    private Map<String,MutableLiveData<NewsBean>> newsMap=new HashMap<>();

    private Map<String,MutableLiveData<CommentBean>> commentMap=new HashMap<>();

    private Map<String,MutableLiveData<ReplyBean>> replyMap=new HashMap<>();

    private MutableLiveData<HotNewsBean> hotNews=new MutableLiveData<>();

    public NewsDepository() {
        newsInNetProvide = NewsInNetProvide.getInstance();
        newsInNetProvide.setCallback(this);
    }

    public void initLoad(){
        for (int i = 0; i < INIT_LOAD_COUNT ; i++) {
            String name = values[i];
            NewsRequirement requirement = new NewsRequirement();
            requirement.setNews(true);
            requirement.setTime(0);
            requirement.setWiden(1);
            requirement.setType(getType(i));
            requirePickData(name,requirement);
        }
        newsInNetProvide.feedHotNews();
        isloadCache = true;
    }


    private NewsType getType(int i){
        switch (i){
            case 0:
                return NewsType.All;
            case 1:
                return NewsType.HOT;
            case 2:
                return NewsType.TECH;
            case 3:
                return NewsType.WORLD;
            case 4:
                return NewsType.POLITICS;
        }
        return NewsType.All;
    }

    public LiveData<NewsBean> getNewsBean(String name) {
        return newsMap.get(name);
    }

    public LiveData<HotNewsBean> getHotNewsBean() {
       return hotNews;
    }

    public LiveData<ReplyBean> getReplyBean(String name) {
       return replyMap.get(name);
    }

    public LiveData<CommentBean> getCommentBean(String name) {
       return commentMap.get(name);
    }

    public void requirePickData(String name, NewsRequirement require){
        pickNewsDeque.add(name);
        if (!newsMap.containsKey(name))
            newsMap.put(name,new MutableLiveData<NewsBean>());
        if (!commentMap.containsKey(name)){
            commentMap.clear();
            commentMap.put(name,new MutableLiveData<CommentBean>());
        }
        if (!replyMap.containsKey(name)){
            replyMap.clear();
            replyMap.put(name,new MutableLiveData<ReplyBean>());
        }
        if (require.isNews()){
            newsInNetProvide.feedNews(require.getType(),require.getTime(),require.getWiden());
        }else if (require.isComment()){
            newsInNetProvide.feedComments(require.getGroupId(),require.getItemId(),require.getOffset(),require.getCount());
        }else if (require.isReply()){
            newsInNetProvide.feedReply(require.getCommentId(),require.getDongtaiId(),require.getOffset(),require.getCount());
        }else {
            newsInNetProvide.feedHotNews();
        }
    }


    @Override
    public void error(Throwable e) {
        Log.d("=====NewsDepository===",e.getMessage());
    }

    @Override
    public void success(HotNewsBean bean) {
       hotNews.setValue(bean);
    }

    @Override
    public void success(ReplyBean bean) {
        String name=pickNewsDeque.removeFirst();
        ReplyBean replyBean=replyMap.get(name).getValue();
        if (replyBean!=null)
            bean.getData().getData().addAll(0,replyBean.getData().getData());
        replyMap.get(name).setValue(bean);
    }

    @Override
    public void success(CommentBean bean) {
        String name=pickNewsDeque.removeFirst();
        CommentBean commentBean = commentMap.get(name).getValue();
      if (commentBean!=null)
      bean.getData().getComments().addAll(0,commentBean.getData().getComments());
      commentMap.get(name).setValue(bean);
    }

    @Override
    public void error(NewsType code, Throwable e) {
        Toast.makeText(MyApplication.getContext(), "网络出错了", Toast.LENGTH_SHORT).show();
        Log.d("=====NewsDepository===","code : "+code+"  "+e.getMessage());
    }

    @Override
    public void success(NewsType code, NewsBean bean) {
        String name = pickNewsDeque.removeFirst();
        NewsBean newsBean=newsMap.get(name).getValue();
        if (newsBean!=null)
            bean.getData().addAll(newsBean.getData());
        newsMap.get(name).setValue(bean);
    }

}
