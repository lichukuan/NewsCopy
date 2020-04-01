package com.person.newscopy.image.adapter;

import android.content.Context;
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
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.person.newscopy.R;
import com.person.newscopy.image.bean.ImageGroupBean;
import com.person.newscopy.image.fragment.ImagesPickFragment;

import java.util.List;

public class ListImageGroupAdapter extends RecyclerView.Adapter {

    private Context context;
    private ImagesPickFragment fragment;
    private List<ImageGroupBean> data;

    public ListImageGroupAdapter(ImagesPickFragment fragment, List<ImageGroupBean> data) {
        this.context = fragment.getContext();
        this.fragment = fragment;
        this.data = data;
    }

    public List<ImageGroupBean> getData() {
        return data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pop_pick_images,viewGroup,false);
        return new ViewHolder(view);
    }

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.picture)
            .centerCrop();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ImageGroupBean bean = data.get(i);
        Glide.with(fragment)
                .load(bean.getPath())
                .apply(requestOptions)
                .into(holder.image);
        holder.name.setText(bean.getGroupName());
        holder.parent.setOnClickListener(v -> {
            fragment.selected(bean.getGroupId());
        });
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(fragment).clear(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView selected;
        TextView name;
        LinearLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.album_name);
            selected = image.findViewById(R.id.selected);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
