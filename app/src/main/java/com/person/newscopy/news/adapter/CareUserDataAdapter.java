package com.person.newscopy.news.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CareUserDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ResultBean> dataBeanList=new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private boolean isNeedRefresh=false;
    private boolean isRefreshOver = false;
    public static final int TYPE_TOP = -1;
    public static final int TYPE_ONE = 0;
    public static final int TYPE_BIG = 1;
    public static final int TYPE_THREE = 2;
    public static final int TYPE_REFRESH = 3;
    public static final int TYPE_VIDEO =  4;
    public static final int TYPE_DEFAUCT = -2;
    boolean isStartTop = false;
    private float height;
    private float width;
    private Activity activity;
    private Set<String> key = new HashSet<>();

    public CareUserDataAdapter() {
    }

    public void refresh(){
        isNeedRefresh = true;
        isRefreshOver = false;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        height = ViewInfoUtil.ScreenInfo.getScreenHeight(context);
        width = ViewInfoUtil.ScreenInfo.getScreenWidth(context);
    }

    public void setActivity(Activity activity){
        this.activity = activity;
        this.context = activity;
        height = ViewInfoUtil.ScreenInfo.getScreenHeight(context);
        width = ViewInfoUtil.ScreenInfo.getScreenWidth(context);
    }

    public boolean isInit(){
        Log.d("===========","getItemCount = "+(getItemCount() - 1));
        return getItemCount() - 1 > 0;
    }


    public int getDownTime(){
        if (dataBeanList.size() >=1)
            return dataBeanList.get(dataBeanList.size()-1).getReleaseTime();
        else return 0;
    }

    public int getTopTime(){
        if (dataBeanList.size() >0)
            return dataBeanList.get(0).getReleaseTime();
        else return 0;
    }

    public void setDataBeanList(List<ResultBean> beans,boolean isInit) {
        int sum = 0;
        for (ResultBean bean : beans) {
            if (!key.contains(bean.getId())){
                key.add(bean.getId());
                dataBeanList.add(bean);
                sum++;
            }
        }
        int startSize = dataBeanList.size();
        notifyItemRangeInserted(startSize,sum);
    }
    public void addDownDataList(List<ResultBean> beans){
        int startSize = dataBeanList.size();
        int sum = 0;
        for (ResultBean bean : beans) {
            if (!key.contains(bean.getId())){
                dataBeanList.add(bean);
                key.add(bean.getId());
                sum++;
            }
        }
        notifyItemRangeInserted(startSize,sum);
    }

    public void addTopData(List<ResultBean> beans){
        int sum = 0;
        for (int i = beans.size() - 1; i >= 0; i--) {
            if(!key.contains(beans.get(i).getId())){
                key.add(beans.get(i).getId());
                dataBeanList.add(0,beans.get(i));
                sum++;
            }
        }
        notifyItemRangeInserted(0,sum);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == TYPE_VIDEO) {
                View view0 = LayoutInflater.from(context).inflate(R.layout.recycler_item_care_video,parent,false);
                return new NormalVideoHolder(view0);
            }else if (viewType == TYPE_BIG) {
                View view1 = LayoutInflater.from(context).inflate(R.layout.recycler_item_care_big,parent,false);
                return new BigViewHolder(view1);
            }else {
                View view4=LayoutInflater.from(context).inflate(R.layout.recycler_item_load,parent,false);
                return  new LoadHolder(view4);
            }

    }


    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1)
            return TYPE_REFRESH;
        final ResultBean bean = dataBeanList.get(position);
        if (bean.getType() == Config.CONTENT.NEWS_TYPE){
                return TYPE_BIG;
        }else {
            return TYPE_VIDEO;
        }
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
        ResultBean bean=dataBeanList.get(position);
        if (holder instanceof NormalVideoHolder){
            NormalVideoHolder normalViewHolder= (NormalVideoHolder) holder;
            normalViewHolder.videoSource.setText(bean.getUserName());
            normalViewHolder.videoTitle.setText(bean.getTitle());
            Glide.with(fragment)
                    .load(bean.getImage())
                    .into(normalViewHolder.image);
            Glide.with(fragment)
                    .asBitmap()
                    .load(bean.getUserIcon())
                    .into(normalViewHolder.icon);
            normalViewHolder.normalVideo.setOnClickListener(v -> showVideo(BaseUtil.getGson().toJson(bean)));
        }else if (holder instanceof BigViewHolder){
            BigViewHolder bigViewHolder = (BigViewHolder) holder;
            bigViewHolder.name.setText(bean.getUserName());
            bigViewHolder.title.setText(bean.getTitle());
            bigViewHolder.bigNews.setOnClickListener(v -> showWebInfo(BaseUtil.getGson().toJson(bean)));
            if (fragment!=null)
            Glide.with(fragment)
                    .load(bean.getImage())
                    .into(bigViewHolder.bigPic);
            else Glide.with(context)
                    .load(bean.getImage())
                    .into(bigViewHolder.bigPic);
            if (fragment!=null)
                Glide.with(fragment)
                        .asBitmap()
                        .load(bean.getUserIcon())
                        .into(bigViewHolder.icon);
            else Glide.with(context)
                    .asBitmap()
                    .load(bean.getUserIcon())
                    .into(bigViewHolder.icon);
        }
    }

    private void showWebInfo(String data){
        Intent intent = new Intent(context,ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,data);
        context.startActivity(intent);
    }

    private void showVideo(String data){
        Intent intent = new Intent(context, ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,data);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size()+1;
    }

    class BigViewHolder extends RecyclerView.ViewHolder{
        TextView title,name;
        ImageView bigPic;
        ShapeImageView icon;
        LinearLayout bigNews;
         BigViewHolder(View itemView) {
            super(itemView);
            bigNews=itemView.findViewById(R.id.big_news);
            title=itemView.findViewById(R.id.title);
            name=itemView.findViewById(R.id.name);
            bigPic=itemView.findViewById(R.id.bigPic);
            icon=itemView.findViewById(R.id.user_icon);
        }
    }

    class NormalVideoHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView videoTitle,videoSource;
        CardView normalVideo;
        ShapeImageView icon;
        NormalVideoHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.video_image);
            videoTitle=itemView.findViewById(R.id.video_title);
            videoSource=itemView.findViewById(R.id.video_source);
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
