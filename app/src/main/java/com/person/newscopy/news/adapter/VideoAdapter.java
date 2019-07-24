package com.person.newscopy.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.news.depository.VideoDepository;
import com.person.newscopy.news.network.bean.CardsBean;
import com.person.newscopy.news.network.bean.ChannelBaseInfoBean;
import com.person.newscopy.show.ShowActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter {

    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_LIVE = 1;

    private int type;

    private Fragment fragment;

    private Context context;

    private List<ChannelBaseInfoBean> channelBeans;

    private List<CardsBean> liveBeans;

    public VideoAdapter(int type, Fragment fragment) {
        this.type = type;
        this.fragment = fragment;
        context=fragment.getContext();
        switch (type){
            case TYPE_LIVE:
                liveBeans=new ArrayList<>();
                break;
                default:
                    channelBeans=new ArrayList<>();
                    break;
        }
    }

    public void setChannelBeans(List<ChannelBaseInfoBean> channelBeans) {
        this.channelBeans = channelBeans;
        notifyDataSetChanged();
    }

    public void setLiveBeans(List<CardsBean> liveBeans) {
        this.liveBeans = liveBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = 0;
        switch (viewType){
            case TYPE_LIVE:
                layoutId=R.layout.recycler_item_video_live;
                View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
                return new LiveViewHolder(view);
            case TYPE_NORMAL:
                default:
                layoutId= R.layout.recycler_item_video_normal;
                View view1 = LayoutInflater.from(context).inflate(layoutId,parent,false);
                return new NormalViewHolder(view1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         if (type==TYPE_NORMAL){
             ChannelBaseInfoBean bean = channelBeans.get(position);
             NormalViewHolder normalViewHolder= (NormalViewHolder) holder;
             normalViewHolder.videoSource.setText(bean.getAuthorName());
             normalViewHolder.playTime.setText(bean.getBlackText());
             normalViewHolder.videoTitle.setText(bean.getVideoTitle());
             normalViewHolder.commentCount.setText(bean.getCommentNum());
             normalViewHolder.playNum.setText(bean.getPlayNum());
             Glide.with(fragment)
                     .load(bean.getVideoImage())
                     .into(normalViewHolder.image);
             normalViewHolder.normalVideo.setOnClickListener(v -> showWebInfo(subNeedPath(TYPE_NORMAL,bean.getVideoId())));
         }else {
            CardsBean bean=liveBeans.get(position);
            LiveViewHolder liveViewHolder= (LiveViewHolder) holder;
            liveViewHolder.lookNum.setText(bean.getHotNum());
            liveViewHolder.userName.setText(bean.getAuthorName());
            Glide.with(fragment)
                    .load(bean.getAvatar_url())
                    .into(((LiveViewHolder) holder).userIcon);
            Glide.with(fragment)
                    .load(bean.getVideoImage())
                    .into(liveViewHolder.videoImage);
            liveViewHolder.title.setText(bean.getVideoTitle());
            liveViewHolder.liveVideo.setOnClickListener(v -> showWebInfo(subNeedPath(TYPE_LIVE,bean.getShort_id()+"")));
         }
    }

    private void showWebInfo(String url){
        Intent intent = new Intent(context,ShowActivity.class);
        intent.putExtra(ShowActivity.SHOW_WEB_INFO,url);
        context.startActivity(intent);
    }

    private String subNeedPath(int type,String videoId){
        if (type == TYPE_NORMAL)
            return "https://www.ixigua.com/i"+videoId;
        else
            return " https://live.ixigua.com/"+videoId;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        switch (type){
            case TYPE_LIVE:
                return liveBeans.size();
            default:
                return channelBeans.size();
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder{
        ImageView image,videoMore;
        TextView videoTitle,playNum,playTime,videoSource,commentCount;
        CardView normalVideo;
         NormalViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.video_image);
            videoMore=itemView.findViewById(R.id.video_more);
            videoTitle=itemView.findViewById(R.id.video_title);
            playNum=itemView.findViewById(R.id.play_num);
            playTime=itemView.findViewById(R.id.play_time);
            videoSource=itemView.findViewById(R.id.video_source);
            commentCount=itemView.findViewById(R.id.commentCount);
            normalVideo=itemView.findViewById(R.id.normal_video);
        }
    }

    class LiveViewHolder extends RecyclerView.ViewHolder{

        ImageView userIcon;
        TextView userName,lookNum,title;
        ImageView videoImage;
        CardView liveVideo;
         LiveViewHolder(View itemView) {
            super(itemView);
            userIcon=itemView.findViewById(R.id.live_user_icon);
            userName=itemView.findViewById(R.id.live_user_name);
            videoImage=itemView.findViewById(R.id.video_image);
            lookNum=itemView.findViewById(R.id.lookNum);
            title=itemView.findViewById(R.id.live_title);
            liveVideo=itemView.findViewById(R.id.live_video);
        }
    }

}
