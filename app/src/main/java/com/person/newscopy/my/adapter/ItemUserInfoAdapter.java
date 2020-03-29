package com.person.newscopy.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;
import com.person.newscopy.user.net.bean.ContentBean;

import java.util.List;

public class ItemUserInfoAdapter extends RecyclerView.Adapter {

    List<ContentBean> data;
    Fragment fragment;
    Context context;

    public ItemUserInfoAdapter(List<ContentBean> data, Fragment fragment) {
        this.data = data;
        this.fragment = fragment;
        context = fragment.getContext();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_user_info_article,viewGroup,false);
            return new ArticleViewHolder(view);
    }

    private void show(String data){
        Intent intent = new Intent(context,ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,data);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ContentBean bean = data.get(i);
        if (viewHolder instanceof ArticleViewHolder){
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
            articleViewHolder.title.setText(bean.getTitle());
            articleViewHolder.comment.setText(bean.getCommentCount()+"评论");
            articleViewHolder.source.setText(bean.getUserName());
            articleViewHolder.releaseTime.setText(BaseUtil.createComeTime(bean.getReleaseTime()));
            if (bean.getType() == Config.CONTENT.NEWS_TYPE){
                articleViewHolder.videoFlag.setVisibility(View.GONE);
                articleViewHolder.oneNews.setOnClickListener(v -> show(BaseUtil.getGson().toJson(bean)));
            }
            else {
                articleViewHolder.videoFlag.setVisibility(View.VISIBLE);
                articleViewHolder.oneNews.setOnClickListener(v -> showVideo(BaseUtil.getGson().toJson(bean)));
            }

            Glide.with(fragment)
                    .load(bean.getImage())
                    .into(articleViewHolder.pic);
        }

//        else if (viewHolder instanceof VideoViewHolder){
//            VideoViewHolder videoViewHolder = (VideoViewHolder) viewHolder;
//            videoViewHolder.commentNum.setText(bean.getCommentCount()+"");
//            videoViewHolder.likeNum.setText(bean.getLikeCount()+"");
//            videoViewHolder.sendNum.setText(bean.getSendCount()+"");
//            videoViewHolder.time.setText(bean.getTime());
//            videoViewHolder.videoTime.setText(bean.getTime());
//            videoViewHolder.content.setText(bean.getTitle());
//            videoViewHolder.name.setText(bean.getUserName());
//            //videoViewHolder.playFlag
//            Glide.with(fragment)
//                    .load(bean.getUserIcon())
//                    .asBitmap()
//                    .into(videoViewHolder.icon);
//            Glide.with(fragment)
//                    .load(bean.getImage())
//                    .into(videoViewHolder.contentImage);
//        }
    }

    private void showVideo(String data){
        Intent intent = new Intent(context,ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,data);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView title,source,comment,releaseTime;
        ImageView pic;
        ImageView videoFlag;
        LinearLayout oneNews;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            oneNews=itemView.findViewById(R.id.one_news);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.from);
            comment=itemView.findViewById(R.id.commentCount);
            releaseTime=itemView.findViewById(R.id.releaseTime);
            pic=itemView.findViewById(R.id.pic1);
            videoFlag = itemView.findViewById(R.id.video_flag);
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
            content = itemView.findViewById(R.id.video_title);
            contentImage = itemView.findViewById(R.id.content_image);
            sendNum = itemView.findViewById(R.id.send_num);
            commentNum = itemView.findViewById(R.id.comment_num);
            likeNum = itemView.findViewById(R.id.like_num);
            playFlag = itemView.findViewById(R.id.play_flag);
            videoTime = itemView.findViewById(R.id.video_time);
        }
    }
}
