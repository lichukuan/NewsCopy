package com.person.newscopy.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.news.network.bean.CardsBean;
import com.person.newscopy.news.network.bean.ChannelBaseInfoBean;
import com.person.newscopy.show.ShowNewsActivity;

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

    public static final int TYPE_REFRESH = -1;

    private boolean isNeedRefresh=false;
    private boolean isRefreshOver = false;
    private float height;
    private float width;


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
        height = ViewUtil.ScreenInfo.getScreenHeight(context);
        width = ViewUtil.ScreenInfo.getScreenWidth(context);
    }

    public void setChannelBeans(List<ChannelBaseInfoBean> channelBeans) {
        this.channelBeans = channelBeans;
        notifyDataSetChanged();
    }

    public void setLiveBeans(List<CardsBean> liveBeans) {
        this.liveBeans = liveBeans;
        notifyDataSetChanged();
    }

    public void refresh(){
        isNeedRefresh=true;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = 0;
        switch (viewType){
            case TYPE_REFRESH:
                View view2=LayoutInflater.from(context).inflate(R.layout.recycler_item_load,parent,false);
                return  new LoadHolder(view2);
            case TYPE_LIVE:
                layoutId=R.layout.recycler_item_video_live;
                View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
                return new LiveViewHolder(view);
                default:
                layoutId= R.layout.recycler_item_video_normal;
                View view1 = LayoutInflater.from(context).inflate(layoutId,parent,false);
                return new NormalViewHolder(view1);
        }
    }

    private void createPop(View location){
        float d = ViewUtil.FitScreen.getDensity();
        int l[] = new int[2];
        location.getLocationOnScreen(l);
        float x = l[0];
        float y = l[1];
        View view = LayoutInflater.from(context).inflate(R.layout.pop_cancel_view,null);
        View top = view.findViewById(R.id.top);
        View bottom = view.findViewById(R.id.bottom);
        top.setVisibility(View.VISIBLE);
        bottom.setVisibility(View.VISIBLE);
        PopupWindow popupWindow = new PopupWindow(view, (int)(width-10*d),(int)(60*d));
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        if(y>height/2){
            top.setVisibility(View.INVISIBLE);
            bottom.setX(x-5*d);
            popupWindow.showAtLocation(location, Gravity.TOP,0,(int)(y-60*d));
        }
        else {
            bottom.setVisibility(View.INVISIBLE);
            top.setX(x-5*d);
            popupWindow.showAtLocation(location, Gravity.BOTTOM,0,(int)(height-y-40*d));
        }
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(() -> backgroundAlpha(1));
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = fragment.getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        fragment.getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount()-1){
            LoadHolder loadHolder = (LoadHolder) holder;
            if (isRefreshOver)
                loadHolder.refreshItem.setVisibility(View.INVISIBLE);
            if (isNeedRefresh)
                loadHolder.refreshItem.setVisibility(View.VISIBLE);
            isNeedRefresh=false;
            isRefreshOver=false;
            return;
        }
        Log.d("===VideoAdapter===",type+"");
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
             Log.d("==VideoAdapter==","直播的布局");
            LiveViewHolder liveViewHolder= (LiveViewHolder) holder;
            liveViewHolder.lookNum.setText(bean.getHotNum());
            liveViewHolder.userName.setText(bean.getAuthorName());
            liveViewHolder.close.setOnClickListener(this::createPop);
            Glide.with(fragment)
                    .load(bean.getAvatar_url())
                    .asBitmap()
                    .into(((LiveViewHolder) holder).userIcon);
            Glide.with(fragment)
                    .load(bean.getVideoImage())
                    .into(liveViewHolder.videoImage);
            liveViewHolder.title.setText(bean.getVideoTitle());
            liveViewHolder.liveVideo.setOnClickListener(v -> showWebInfo(subNeedPath(TYPE_LIVE,bean.getShort_id()+"")));
         }
    }

    private void showWebInfo(String url){
        Intent intent = new Intent(context,ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,url);
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
        if (position==getItemCount()-1)
            return TYPE_REFRESH;
        return type;
    }

    @Override
    public int getItemCount() {
        switch (type){
            case TYPE_LIVE:
                return liveBeans.size()+1;
            default:
                return channelBeans.size()+1;
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

        ShapeImageView userIcon;
        TextView userName,lookNum,title;
        ImageView videoImage;
        CardView liveVideo;
        ImageView close;
         LiveViewHolder(View itemView) {
            super(itemView);
            userIcon=itemView.findViewById(R.id.live_user_icon);
            userName=itemView.findViewById(R.id.live_user_name);
            videoImage=itemView.findViewById(R.id.video_image);
            lookNum=itemView.findViewById(R.id.lookNum);
            title=itemView.findViewById(R.id.live_title);
            liveVideo=itemView.findViewById(R.id.live_video);
            close = itemView.findViewById(R.id.video_close);
        }
    }

    class LoadHolder extends RecyclerView.ViewHolder{
        LinearLayout refreshItem;
        LoadHolder(View itemView) {
            super(itemView);
            refreshItem=itemView.findViewById(R.id.item_refresh);
        }
    }

}
