package com.person.newscopy.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.fragment.SaveFragment;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;
import com.person.newscopy.news.network.bean.ResultBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolder> {

    private List<ResultBean> data;
    private Context context;
    private SaveFragment fragment;
    private boolean isShowSwitchButton = false;
    private Set<ResultBean> requireDeleteData = new HashSet<>();

    public SaveAdapter(List<ResultBean> data, SaveFragment fragment) {
        this.data = data;
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_operate_video,viewGroup,false);
        return new ViewHolder(view);
    }

    RequestOptions requestOptions = new RequestOptions()
            .centerCrop();

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultBean readBean = data.get(i);
        if (isShowSwitchButton)viewHolder.switchButton.setVisibility(View.VISIBLE);
        else viewHolder.switchButton.setVisibility(View.GONE);
        viewHolder.content.setText(readBean.getTitle());
        viewHolder.name.setText(readBean.getUserName());
        viewHolder.time.setText(BaseUtil.createComeTime(readBean.getReleaseTime()));
        if (readBean.getImage() == null || readBean.getImage().equals(""))
            viewHolder.contentImage.setVisibility(View.GONE);
        else {
            viewHolder.contentImage.setVisibility(View.VISIBLE);
            Glide.with(fragment)
                    .load(readBean.getImage())
                    .apply(requestOptions)
                    .into(viewHolder.contentImage);
        }
            if (readBean.getType() == Config.CONTENT.NEWS_TYPE){
                viewHolder.flag.setVisibility(View.GONE);
            }else viewHolder.flag.setVisibility(View.VISIBLE);
            viewHolder.parent.setOnClickListener(v -> {
                viewHolder.parent.setClickable(false);
                if (readBean.getType() == Config.CONTENT.NEWS_TYPE)
                    goToArticleActivity(BaseUtil.getGson().toJson(readBean));
                else goToVideoActivity(BaseUtil.getGson().toJson(readBean));
                viewHolder.parent.setClickable(true);
            });
            if(isShowSwitchButton){
                viewHolder.switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked)requireDeleteData.add(readBean);
                    else requireDeleteData.remove(readBean);
                });
                viewHolder.switchButton.setOnClickListener(v -> {
                    if (viewHolder.switchButton.isChecked())viewHolder.switchButton.setChecked(false);
                    else viewHolder.switchButton.setChecked(true);
                });
            }else viewHolder.switchButton.setChecked(false);
    }

    public void goToArticleActivity(String data){
        Intent intent = new Intent(context, ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,data);
        context.startActivity(intent);
    }

    public void goToVideoActivity(String data){
        Intent intent = new Intent(context, ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,data);
        context.startActivity(intent);
    }

    public void changeToEditMode(){
        isShowSwitchButton = true;
        notifyDataSetChanged();
    }

    public void deleteSave(){
        fragment.waitDelete();
        isShowSwitchButton = false;
        for (ResultBean key:requireDeleteData) {
            fragment.deleteSaveData(key.getId());
            data.remove(key);
        }
        requireDeleteData.clear();
        notifyDataSetChanged();
        fragment.deleteCompleted();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;
        View parent;
        ImageView flag;
        RadioButton switchButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.title);
            contentImage = itemView.findViewById(R.id.content_image);
            parent = itemView.findViewById(R.id.parent);
            flag = itemView.findViewById(R.id.flag);
            switchButton = itemView.findViewById(R.id.switch_button);
        }
    }
}
