package com.person.newscopy.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
        if (contentType == Config.CONTENT.NEWS_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_operate_article,viewGroup,false);
            return new ArticleViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_operate_video,viewGroup,false);
            return new VideoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ReadContent readBean = readBeans.get(i);
        if (viewHolder instanceof ArticleViewHolder){
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
                articleViewHolder.likeNum.setText(readBean.getLikeCount() + "");
                articleViewHolder.commentNum.setText(readBean.getCommentCount() + "");
                articleViewHolder.sendNum.setText(readBean.getSendCount() + "");
                articleViewHolder.content.setText(readBean.getTitle());
                articleViewHolder.name.setText(readBean.getName());
                //articleViewHolder.time.setText(readBean.getTime());
                Glide.with(fragment)
                        .load(readBean.getIcon())
                        .asBitmap()
                        .into(((ArticleViewHolder) viewHolder).icon);
                Glide.with(fragment)
                        .load(readBean.getImage())
                        .into(((ArticleViewHolder) viewHolder).contentImage);
        }else if (viewHolder instanceof VideoViewHolder){
                VideoViewHolder videoViewHolder = (VideoViewHolder) viewHolder;
                videoViewHolder.commentNum.setText(readBean.getCommentCount()+"");
                videoViewHolder.likeNum.setText(readBean.getLikeCount()+"");
                videoViewHolder.sendNum.setText(readBean.getSendCount()+"");
                videoViewHolder.content.setText(readBean.getTitle());
                videoViewHolder.name.setText(readBean.getName());
                //videoViewHolder.playFlag
                Glide.with(fragment)
                        .load(readBean.getIcon())
                        .asBitmap()
                        .into(videoViewHolder.icon);
                Glide.with(fragment)
                        .load(readBean.getImage())
                        .into(videoViewHolder.contentImage);
        }
    }

    @Override
    public int getItemCount() {
        return readBeans.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;
        TextView sendNum;
        TextView commentNum;
        TextView likeNum;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.release_user_icon);
            name = itemView.findViewById(R.id.release_user_name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content_title);
            contentImage = itemView.findViewById(R.id.content_image);
            sendNum = itemView.findViewById(R.id.send_sum);
            commentNum = itemView.findViewById(R.id.comment_num);
            likeNum = itemView.findViewById(R.id.like_num);
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;
        TextView sendNum;
        TextView commentNum;
        TextView likeNum;
        ImageView playFlag;
        TextView videoTime;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.release_user_icon);
            name = itemView.findViewById(R.id.release_user_name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content_title);
            contentImage = itemView.findViewById(R.id.content_image);
            sendNum = itemView.findViewById(R.id.send_sum);
            commentNum = itemView.findViewById(R.id.comment_num);
            likeNum = itemView.findViewById(R.id.like_num);
            playFlag = itemView.findViewById(R.id.play_flag);
            videoTime = itemView.findViewById(R.id.video_time);
        }
    }
}
