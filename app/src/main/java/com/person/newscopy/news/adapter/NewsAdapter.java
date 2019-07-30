package com.person.newscopy.news.adapter;

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
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.news.network.bean.DataBean;
import com.person.newscopy.news.network.bean.NewsBean;
import com.person.newscopy.show.ShowActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataBean> dataBeanList=new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private boolean isNeedRefresh=false;
    private boolean isRefreshOver = false;
    public static final int TYPE_TOP=-1;
    public static final int TYPE_ONE=0;
    public static final int TYPE_BIG=1;
    public static final int TYPE_THREE=2;
    public static final int TYPE_REFRESH=3;

    public NewsAdapter() {
    }

    public NewsAdapter(NewsBean newsBean, Fragment fragment) {
        dataBeanList = newsBean.getData();
        this.context = fragment.getContext();
        this.fragment=fragment;
    }

    public void refresh(){
        isNeedRefresh=true;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    public void setDataBeanList(List<DataBean> beans) {
        int startSize = dataBeanList.size();
        int count = beans.size();
        //这里还要判断是否有重复项
        dataBeanList.addAll(beans);
        isRefreshOver = true;
        if (startSize!=0)
            notifyItemRangeInserted(startSize,count);
        else
            notifyDataSetChanged();
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
                View view4=LayoutInflater.from(context).inflate(R.layout.recycler_item_load,parent,false);
                return  new LoadHolder(view4);
            case TYPE_ONE:
            default:
                View view2 = LayoutInflater.from(context).inflate(R.layout.recycler_item_news_one,parent,false);
                return new OneViewHolder(view2);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1)
            return TYPE_REFRESH;
        final List list = dataBeanList.get(position).getImage_list();
        if (list==null){
            return TYPE_TOP;
        }else if (checkIsBig(dataBeanList.get(position).getImage_url())){
            return TYPE_BIG;
        }else if (list.size()==3)
            return TYPE_THREE;
        else
            return TYPE_ONE;

    }

    //通过 image_url 是否含有p9来判断
    private boolean checkIsBig(String url){
        return url.contains("p9");
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
        DataBean bean=dataBeanList.get(position);
        if (holder instanceof BigViewHolder){
            BigViewHolder bigViewHolder = (BigViewHolder) holder;
            bigViewHolder.source.setText(bean.getSource());
            bigViewHolder.releaseTime.setText(createComeTime(bean.getBehot_time()));
            bigViewHolder.title.setText(bean.getTitle());
            bigViewHolder.comment.setText(bean.getComments_count()+"评论");
            bigViewHolder.bigNews.setOnClickListener(v -> showWebInfo(subNeedPath(bean.getGroup_id())));
            Glide.with(fragment)
                    .load(bean.getImage_url())
                    .into(bigViewHolder.bigPic);
        }else if (holder instanceof OneViewHolder){
               OneViewHolder oneViewHolder = (OneViewHolder) holder;
               oneViewHolder.title.setText(bean.getTitle());
               oneViewHolder.comment.setText(bean.getComments_count()+"评论");
               oneViewHolder.source.setText(bean.getSource());
               oneViewHolder.releaseTime.setText(createComeTime(bean.getBehot_time()));
               oneViewHolder.oneNews.setOnClickListener(v -> showWebInfo(subNeedPath(bean.getGroup_id())));
               Glide.with(fragment)
                       .load(bean.getImage_url())
                       .into(oneViewHolder.pic);
        }else if (holder instanceof ThreeViewHolder){
             ThreeViewHolder threeViewHolder= (ThreeViewHolder) holder;
             threeViewHolder.title.setText(bean.getTitle());
             threeViewHolder.comment.setText(bean.getComments_count()+"评论");
             threeViewHolder.source.setText(bean.getSource());
             threeViewHolder.releaseTime.setText(createComeTime(bean.getBehot_time()));
             threeViewHolder.threeNews.setOnClickListener(v -> showWebInfo(subNeedPath(bean.getGroup_id())));
             Glide.with(fragment)
                     .load(bean.getImage_list().get(0).getUrl())
                     .into(threeViewHolder.pic1);
            Glide.with(fragment)
                    .load(bean.getImage_list().get(1).getUrl())
                    .into(threeViewHolder.pic2);
            Glide.with(fragment)
                    .load(bean.getImage_list().get(2).getUrl())
                    .into(threeViewHolder.pic3);
        }else if (holder instanceof TopViewHolder){
            TopViewHolder topViewHolder= (TopViewHolder) holder;
            topViewHolder.comment.setText(bean.getComments_count()+"评论");
            topViewHolder.source.setText(bean.getSource());
            topViewHolder.title.setText(bean.getTitle());
            topViewHolder.topNews.setOnClickListener(v -> showWebInfo(subNeedPath(bean.getGroup_id())));
            if (position>2)
                topViewHolder.top.setVisibility(View.GONE);
        }
    }

    private void showWebInfo(String url){
        Intent intent = new Intent(context,ShowActivity.class);
        intent.putExtra(ShowActivity.SHOW_WEB_INFO,url);
        context.startActivity(intent);
    }

    private String subNeedPath(String groupId){
        return "https://www.toutiao.com/a"+groupId;
    }

    private static final int minute = 60;

    private static final int hour = 60*60;

    private String createComeTime(int time){
        long l = BaseUtil.getTime() - time;
        if (l/hour >0)
            return l/hour+"小时前";
        else
            return l/minute+"分钟前";
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
        TextView title,source,comment,top;
        LinearLayout topNews;
        public TopViewHolder(View itemView) {
            super(itemView);
            topNews=itemView.findViewById(R.id.top_news);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.from);
            comment=itemView.findViewById(R.id.commentCount);
            top=itemView.findViewById(R.id.top);
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
