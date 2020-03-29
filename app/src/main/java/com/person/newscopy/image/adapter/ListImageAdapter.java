package com.person.newscopy.image.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.image.bean.ImageBean;

import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter {

    private Context context;
    private Fragment fragment;
    private List<ImageBean> data;
    private int width;

    public ListImageAdapter(Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getContext();
        width = (int) (ViewInfoUtil.ScreenInfo.getScreenWidth(context)/3);
    }

    public void setData(List<ImageBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pick_image,viewGroup,false);
        view.getLayoutParams().height = width;
        return new ItemImageViewHolder(view);
    }

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.picture)
            .centerCrop();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemImageViewHolder holder = (ItemImageViewHolder) viewHolder;
       ImageBean imageBean = data.get(i);
        Glide.with(fragment)
                .load(imageBean.getPath())
                .apply(requestOptions)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        ItemImageViewHolder itemImageViewHolder = (ItemImageViewHolder) holder;
        Glide.with(fragment).clear(itemImageViewHolder.image);
    }

    class ItemImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView num;
        public ItemImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            num = itemView.findViewById(R.id.num);
        }
    }
}
