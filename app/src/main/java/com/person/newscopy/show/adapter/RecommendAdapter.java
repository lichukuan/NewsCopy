package com.person.newscopy.show.adapter;

import android.app.Activity;
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
import com.person.newscopy.common.Config;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter {

    private List<ResultBean> dataBeanList;
    private Context context;
    private Fragment fragment;
    private Activity activity;

    public RecommendAdapter(List<ResultBean> dataBeanList, Fragment fragment) {
        this.dataBeanList = dataBeanList;
        this.context = fragment.getContext();
        this.fragment = fragment;
    }

    public RecommendAdapter(List<ResultBean> dataBeanList, Activity activity) {
        this.dataBeanList = dataBeanList;
        this.context = activity;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_recommend,viewGroup,false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ResultBean bean = dataBeanList.get(i);
        RecommendViewHolder holder = (RecommendViewHolder) viewHolder;
        holder.title.setText(bean.getTitle());
        holder.comment.setText(bean.getCommentCount()+"评论");
        holder.source.setText(bean.getUserName());
        holder.releaseTime.setText(BaseUtil.createComeTime(bean.getReleaseTime()));
        holder.recommend.setOnClickListener(v -> showContent(bean.getType(),BaseUtil.getGson().toJson(bean)));
        if (bean.getImage()!=null&&(!bean.getImage().equals(""))){
            if (fragment!=null)
                Glide.with(fragment)
                        .load(bean.getImage())
                        .into(holder.pic);
            else
                Glide.with(context)
                        .load(bean.getImage())
                        .into(holder.pic);
        }else
            holder.pic.setVisibility(View.GONE);
    }

    private void showContent(int type,String data){
        switch (type){
            case Config.CONTENT.NEWS_TYPE:
                Intent intent = new Intent(context, ShowNewsActivity.class);
                intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,data);
                context.startActivity(intent);
                break;
            case Config.CONTENT.VIDEO_TYPE:
                Intent intent1 = new Intent(context, ShowVideoActivity.class);
                intent1.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,data);
                context.startActivity(intent1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }
    
    class RecommendViewHolder extends RecyclerView.ViewHolder{
        TextView title,source,comment,releaseTime;
        ImageView pic;
        LinearLayout recommend;
        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend=itemView.findViewById(R.id.recommend);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.from);
            comment=itemView.findViewById(R.id.commentCount);
            releaseTime=itemView.findViewById(R.id.releaseTime);
            pic=itemView.findViewById(R.id.pic1);
        }
    }
}
