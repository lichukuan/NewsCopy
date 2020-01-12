package com.person.newscopy.news.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.google.gson.reflect.TypeToken;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowNewsActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ResultBean> dataBeanList=new ArrayList<>();
    private Set<String> key = new HashSet<>();
    private Context context;
    private Fragment fragment;
    private boolean isNeedRefresh=false;
    private boolean isRefreshOver = false;
    public static final int TYPE_TOP=-1;
    public static final int TYPE_ONE=0;
    public static final int TYPE_BIG=1;
    public static final int TYPE_THREE=2;
    public static final int TYPE_REFRESH=3;
    boolean isStartTop = false;
    private float height;
    private float width;
    private Activity activity;
    private LoadHolder loadHolder;

    public NewsAdapter() {
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

    public void addDownDataBeanList(List<ResultBean> beans) {
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

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = fragment.getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        fragment.getActivity().getWindow().setAttributes(lp);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TOP:
                View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_news_top,parent,false);
                return new TopViewHolder(view);
            case TYPE_BIG:
                View view1 = LayoutInflater.from(context).inflate(R.layout.recycler_item_news_big,parent,false);
                return new BigViewHolder(view1);
            case TYPE_THREE:
                View view3 = LayoutInflater.from(context).inflate(R.layout.recycler_item_news_three,parent,false);
                return new ThreeViewHolder(view3);
            case TYPE_REFRESH:
                if (loadHolder != null)return loadHolder;
                View view4=LayoutInflater.from(context).inflate(R.layout.recycler_item_load,parent,false);
                loadHolder =  new LoadHolder(view4);
                return  loadHolder;
            case TYPE_ONE:
            default:
                View view2 = LayoutInflater.from(context).inflate(R.layout.recycler_item_news_one,parent,false);
                return new OneViewHolder(view2);
        }

    }

    public void hideLoad(){
        loadHolder.refreshItem.setVisibility(View.GONE);
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
        PopupWindow popupWindow = new PopupWindow(view, (int)(width-10*d),(int)(260*d));
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        if(y>height/2){
           top.setVisibility(View.INVISIBLE);
           bottom.setX(x-5*d);
           popupWindow.showAtLocation(location, Gravity.TOP,0,(int)(y-260*d));
        }
        else {
            bottom.setVisibility(View.INVISIBLE);
            top.setX(x-5*d);
            popupWindow.showAtLocation(location, Gravity.BOTTOM,0,(int)(height-y-220*d));
        }
       backgroundAlpha(0.5f);
       popupWindow.setOnDismissListener(() -> backgroundAlpha(1));
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1)
            return TYPE_REFRESH;
        final ResultBean bean = dataBeanList.get(position);
        if (bean.getImageList()==null&&bean.getImage()==null){
            return TYPE_TOP;
        }
        final List<String> list = BaseUtil.jsonToStringList(dataBeanList.get(position).getImageList());
        bean.setImages(list);
        if (bean.getImage() != null)
            return TYPE_ONE;
        else if (bean.getImages().size() >= 3){
            return TYPE_THREE;
        }else
            return TYPE_BIG;
    }

    private static final String TAG = "NewsAdapter";
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadHolder){
            LoadHolder loadHolder = (LoadHolder) holder;
            loadHolder.refreshItem.setVisibility(View.VISIBLE);
            return;
        }
        ResultBean bean=dataBeanList.get(position);
        if (holder instanceof BigViewHolder){
            BigViewHolder bigViewHolder = (BigViewHolder) holder;
            bigViewHolder.source.setText(bean.getUserName());
            bigViewHolder.releaseTime.setText(createComeTime(bean.getReleaseTime()));
            bigViewHolder.title.setText(bean.getTitle());
            bigViewHolder.comment.setText(bean.getCommentCount()+"评论");
            bigViewHolder.bigNews.setOnClickListener(v -> showWebInfo(BaseUtil.getGson().toJson(bean)));
            if (fragment!=null)
            Glide.with(fragment)
                    .load(bean.getImages().get(0))
                    .into(bigViewHolder.bigPic);
            else Glide.with(context)
                    .load(bean.getImages().get(0))
                    .into(bigViewHolder.bigPic);
            bigViewHolder.close.setOnClickListener(this::createPop);
        }else if (holder instanceof OneViewHolder){
               OneViewHolder oneViewHolder = (OneViewHolder) holder;
               oneViewHolder.title.setText(bean.getTitle());
               oneViewHolder.comment.setText(bean.getCommentCount()+"评论");
               oneViewHolder.source.setText(bean.getUserName());
               oneViewHolder.releaseTime.setText(createComeTime(bean.getReleaseTime()));
               oneViewHolder.oneNews.setOnClickListener(v -> showWebInfo(BaseUtil.getGson().toJson(bean)));
               if (fragment!=null)
               Glide.with(fragment)
                       .load(bean.getImage())
                       .into(oneViewHolder.pic);
               else  Glide.with(context)
                       .load(bean.getImage())
                       .into(oneViewHolder.pic);
               oneViewHolder.close.setOnClickListener(this::createPop);
        }else if (holder instanceof ThreeViewHolder){
             ThreeViewHolder threeViewHolder= (ThreeViewHolder) holder;
             threeViewHolder.title.setText(bean.getTitle());
             threeViewHolder.comment.setText(bean.getCommentCount()+"评论");
             threeViewHolder.source.setText(bean.getUserName());
             threeViewHolder.releaseTime.setText(createComeTime(bean.getReleaseTime()));
             threeViewHolder.threeNews.setOnClickListener(v -> showWebInfo(BaseUtil.getGson().toJson(bean)));
            final List<String> list = bean.getImages();
            if (fragment!=null){
                Glide.with(fragment)
                        .load(list.get(0))
                        .into(threeViewHolder.pic1);
                Glide.with(fragment)
                        .load(list.get(1))
                        .into(threeViewHolder.pic2);
                Glide.with(fragment)
                        .load(list.get(2))
                        .into(threeViewHolder.pic3);
            }else {
                Glide.with(context)
                        .load(list.get(0))
                        .into(threeViewHolder.pic1);
                Glide.with(context)
                        .load(list.get(1))
                        .into(threeViewHolder.pic2);
                Glide.with(context)
                        .load(list.get(2))
                        .into(threeViewHolder.pic3);
            }
            threeViewHolder.close.setOnClickListener(this::createPop);
        }else if (holder instanceof TopViewHolder){
            TopViewHolder topViewHolder= (TopViewHolder) holder;
            topViewHolder.comment.setText(bean.getCommentCount()+"评论");
            topViewHolder.source.setText(bean.getUserName());
            topViewHolder.title.setText(bean.getTitle());
            topViewHolder.topNews.setOnClickListener(v -> showWebInfo(BaseUtil.getGson().toJson(bean)));
            topViewHolder.close.setOnClickListener(this::createPop);
        }
    }

    private void showWebInfo(String data){
        Intent intent = new Intent(context,ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,data);
        context.startActivity(intent);
    }

    private String createComeTime(int time){
        long l = BaseUtil.getTime() - time;
        long allMinute = l/60;
        if (allMinute == 0)
            return "刚刚";
        else if (allMinute < 60)
            return allMinute+"分钟前";
        long allHour = allMinute/60;
        if (allHour < 24)
            return allHour+"小时前";
        int allDay = (int)allHour/24;
        if (allDay < 30)return allDay+"天前";
        int allMouth = allDay/30;
        if (allMouth < 12)return allMouth+"月前";
        return allMouth/12+"年前";
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size()+1;
    }

    class BigViewHolder extends RecyclerView.ViewHolder{
        TextView title,source,comment,releaseTime;
        ImageView bigPic;
        ImageView close;
        LinearLayout bigNews;
         BigViewHolder(View itemView) {
            super(itemView);
            bigNews=itemView.findViewById(R.id.big_news);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.from);
            comment=itemView.findViewById(R.id.commentCount);
            releaseTime=itemView.findViewById(R.id.releaseTime);
            bigPic=itemView.findViewById(R.id.bigPic);
            close=itemView.findViewById(R.id.close);
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder{
        TextView title,source,comment,releaseTime;
        ImageView pic;
        ImageView close;
        LinearLayout oneNews;
         OneViewHolder(View itemView) {
            super(itemView);
            oneNews=itemView.findViewById(R.id.one_news);
             title=itemView.findViewById(R.id.title);
             source=itemView.findViewById(R.id.from);
             comment=itemView.findViewById(R.id.commentCount);
             releaseTime=itemView.findViewById(R.id.releaseTime);
             pic=itemView.findViewById(R.id.pic1);
             close=itemView.findViewById(R.id.close);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder{
        TextView title,source,comment,releaseTime;
        ImageView pic3,pic1,pic2;
        ImageView close;
        LinearLayout threeNews;
        ThreeViewHolder(View itemView) {
            super(itemView);
            threeNews=itemView.findViewById(R.id.three_news);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.from);
            comment=itemView.findViewById(R.id.commentCount);
            releaseTime=itemView.findViewById(R.id.releaseTime);
            pic1=itemView.findViewById(R.id.pic1);
            pic2=itemView.findViewById(R.id.pic2);
            pic3=itemView.findViewById(R.id.pic3);
            close=itemView.findViewById(R.id.close);
        }
    }

    class TopViewHolder extends RecyclerView.ViewHolder{
        TextView title,source,comment;
        LinearLayout topNews;
        ImageView close;
        public TopViewHolder(View itemView) {
            super(itemView);
            topNews=itemView.findViewById(R.id.top_news);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.from);
            comment=itemView.findViewById(R.id.commentCount);
            close=itemView.findViewById(R.id.close);
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
