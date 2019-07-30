package com.person.newscopy.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.person.newscopy.news.depository.NewsDepository;
import com.person.newscopy.news.depository.NewsRequirement;
import com.person.newscopy.news.network.bean.CommentBean;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.NewsBean;
import com.person.newscopy.news.network.bean.ReplyBean;

public class NewsViewModel extends AndroidViewModel {

    private NewsDepository newsDepository=null;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsDepository=new NewsDepository();
    }

    public LiveData<NewsBean> getNews(String name, NewsRequirement require){
        newsDepository.requirePickData(name,require);
        return newsDepository.getNewsBean(require.getType());
    }

    public void pullData(String name, NewsRequirement require){
        newsDepository.requirePickData(name,require);
    }


    public LiveData<CommentBean> getComment(String name, NewsRequirement require){
        newsDepository.requirePickData(name,require);
        return newsDepository.getCommentBean(name);
    }

    public LiveData<ReplyBean> getReply(String name, NewsRequirement require){
        newsDepository.requirePickData(name,require);
        return newsDepository.getReplyBean(name);
    }

    public LiveData<HotNewsBean> getHotNews(String name, NewsRequirement require){
        newsDepository.requirePickData(name,require);
        return newsDepository.getHotNewsBean();
    }

}
