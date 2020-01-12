package com.person.newscopy.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.fragment.HistoryFragment;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.type.Types;
import com.person.newscopy.user.net.bean.ReadContent;


import java.util.List;

public class ItemOperateReadAdapter extends RecyclerView.Adapter {

    List<ReadContent> readBeans;
    Fragment fragment;
    Context context;

    public ItemOperateReadAdapter(List<ReadContent> readBeans, Fragment fragment) {
        this.readBeans = readBeans;
        this.fragment = fragment;
        context = fragment.getContext();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            int contentType = readBeans.get(i).getContentType();
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_operate_video,viewGroup,false);
            return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ReadContent readBean = readBeans.get(i);
        Log.d("=ItemOperateReadAdapter",readBeans.get(i).getTitle()+" : "+readBeans.get(i).getContentType());
        if (viewHolder instanceof ArticleViewHolder){
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
                articleViewHolder.content.setText(readBean.getTitle());
                articleViewHolder.name.setText(readBean.getName());
                articleViewHolder.time.setText(BaseUtil.createComeTime(readBean.getTime()));
                Glide.with(fragment)
                        .load(readBean.getImage())
                        .into(((ArticleViewHolder) viewHolder).contentImage);
                if (readBean.getContentType() == Config.CONTENT.NEWS_TYPE){
                    articleViewHolder.flag.setVisibility(View.GONE);
                }else articleViewHolder.flag.setVisibility(View.VISIBLE);
                articleViewHolder.parent.setOnClickListener(v -> {
                    articleViewHolder.parent.setClickable(false);
                    if (fragment instanceof HistoryFragment){
                        HistoryFragment historyFragment = (HistoryFragment) fragment;
                        if (readBean.getContentType() == Config.CONTENT.NEWS_TYPE)
                        historyFragment.fromHistoryToShow(Config.CONTENT.NEWS_TYPE,readBean.getContentId());
                        else
                            historyFragment.fromHistoryToShow(Config.CONTENT.VIDEO_TYPE,readBean.getContentId());

                    }
                    articleViewHolder.parent.setClickable(true);
                });
      }
      //else if (viewHolder instanceof VideoViewHolder){
//                VideoViewHolder videoViewHolder = (VideoViewHolder) viewHolder;
//                videoViewHolder.content.setText(readBean.getTitle());
//                videoViewHolder.name.setText(readBean.getName());
//                videoViewHolder.time.setText(BaseUtil.createComeTime(readBean.getTime()));
//                videoViewHolder.parent.setOnClickListener(v -> {
//                    videoViewHolder.parent.setClickable(false);
//                    if (fragment instanceof HistoryFragment){
//                        HistoryFragment historyFragment = (HistoryFragment) fragment;
//                        historyFragment.fromHistoryToShow(Config.CONTENT.VIDEO_TYPE,readBean.getContentId());
//                    }
//                    videoViewHolder.parent.setClickable(true);
//                });
//                Glide.with(fragment)
//                        .load(readBean.getImage())
//                        .into(videoViewHolder.contentImage);
//        }
    }

    @Override
    public int getItemCount() {
        return readBeans.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;
        View parent;
        ImageView flag;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.title);
            contentImage = itemView.findViewById(R.id.content_image);
            parent = itemView.findViewById(R.id.parent);
            flag = itemView.findViewById(R.id.flag);
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;
        View parent;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            parent = itemView.findViewById(R.id.parent);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.title);
            contentImage = itemView.findViewById(R.id.content_image);
        }
    }
}
