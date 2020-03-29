package com.person.newscopy.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowVideoActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VideoAdapter extends RecyclerView.Adapter {

    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_LIVE = 1;

    private Set<String> key = new HashSet<>();

    private int type;

    private Fragment fragment;

    private Context context;

    private List<ResultBean> channelBeans = new ArrayList<>();

    public static final int TYPE_REFRESH = -1;

    private boolean isNeedRefresh=false;
    private boolean isRefreshOver = false;
    private float height;
    private float width;


    public VideoAdapter(int type, Fragment fragment) {
        this.type = type;
        this.fragment = fragment;
        context=fragment.getContext();
        height = ViewInfoUtil.ScreenInfo.getScreenHeight(context);
        width = ViewInfoUtil.ScreenInfo.getScreenWidth(context);
    }

    public void setChannelBeans(List<ResultBean> beans,boolean isInit) {
        int sum = 0;
        for (ResultBean bean : beans) {
            if (!key.contains(bean.getId())){
                key.add(bean.getId());
                channelBeans.add(bean);
                sum++;
            }
        }
        int startSize = channelBeans.size();
        notifyItemRangeInserted(startSize,sum);
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
                default:
                layoutId= R.layout.recycler_item_video_normal;
                View view1 = LayoutInflater.from(context).inflate(layoutId,parent,false);
                return new NormalViewHolder(view1);
        }
    }

    private void createPop(View location){
        float d = ScreenFitUtil.getDensity();
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

    public void addTopData(List<ResultBean> beans){
        int sum = 0;
        for (int i = beans.size() - 1; i >= 0; i--) {
            if(!key.contains(beans.get(i).getId())){
                key.add(beans.get(i).getId());
                channelBeans.add(0,beans.get(i));
                sum++;
            }
        }
        notifyItemRangeInserted(0,sum);
    }

    public void addDownData(List<ResultBean> beans){
        int startSize = channelBeans.size();
        int sum = 0;
        for (ResultBean bean : beans) {
            if (!key.contains(bean.getId())){
                channelBeans.add(bean);
                key.add(bean.getId());
                sum++;
            }
        }
        notifyItemRangeInserted(startSize,sum);
    }

    public int getDownTime(){
        if (channelBeans.size() >=1)
            return channelBeans.get(channelBeans.size()-1).getReleaseTime();
        else return 0;
    }

    public int getTopTime(){
        if (channelBeans.size() >0)
            return channelBeans.get(0).getReleaseTime();
        else return 0;
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
         if (type==TYPE_NORMAL){
             ResultBean bean = channelBeans.get(position);
             NormalViewHolder normalViewHolder= (NormalViewHolder) holder;
             normalViewHolder.videoSource.setText(bean.getUserName());
             normalViewHolder.playTime.setText(bean.getTime());
             normalViewHolder.videoTitle.setText(bean.getTitle());
             normalViewHolder.commentCount.setText(bean.getCommentCount()+"");
             normalViewHolder.playNum.setText(bean.getPlayCount()+"播放");
             Glide.with(fragment)
                     .load(bean.getImage())
                     .into(normalViewHolder.image);
             Glide.with(fragment)
                     .asBitmap()
                     .load(bean.getUserIcon())
                     .into(normalViewHolder.icon);
             normalViewHolder.normalVideo.setOnClickListener(v -> showWebInfo(BaseUtil.getGson().toJson(bean)));
         }
    }

    private void showWebInfo(String data){
        Intent intent = new Intent(context,ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,data);
        context.startActivity(intent);
    }


    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1)
            return TYPE_REFRESH;
        return type;
    }

    @Override
    public int getItemCount() {
        if (channelBeans == null)return 0;
        return channelBeans.size()+1;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder{
        ImageView image,videoMore;
        TextView videoTitle,playNum,playTime,videoSource,commentCount;
        CardView normalVideo;
        ShapeImageView icon;
         NormalViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.video_image);
            //videoMore=itemView.findViewById(R.id.video_more);
            videoTitle=itemView.findViewById(R.id.video_title);
            playNum=itemView.findViewById(R.id.play_num);
            playTime=itemView.findViewById(R.id.play_time);
            videoSource=itemView.findViewById(R.id.video_source);
            commentCount=itemView.findViewById(R.id.commentCount);
            normalVideo=itemView.findViewById(R.id.normal_video);
            icon=itemView.findViewById(R.id.user_icon);
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
