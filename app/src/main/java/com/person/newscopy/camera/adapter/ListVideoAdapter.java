package com.person.newscopy.camera.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.camera.bean.VideoBean;
import com.person.newscopy.image.ImageActivity;
import com.person.newscopy.image.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

public class ListVideoAdapter extends RecyclerView.Adapter {

    private Context context;
    private Fragment fragment;
    private List<VideoBean> data;

    public ListVideoAdapter(Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getContext();
    }

    public void setData(List<VideoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_video,viewGroup,false);
        return new ItemVideoViewHolder(view);
    }

    private MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemVideoViewHolder holder = (ItemVideoViewHolder) viewHolder;
        VideoBean videoBean = data.get(i);
        mediaMetadataRetriever.setDataSource(videoBean.getPath());
        holder.image.setImageBitmap(mediaMetadataRetriever.getFrameAtTime());
        holder.videoName.setText(videoBean.getPath());
        holder.videoInfo.setText(videoBean.getDuration()+"|"+videoBean.getSize());
        holder.parent.setOnClickListener(v -> {

        });
    }


    public List<String> getImagePath(List<ImageBean> data){
        List<String> l = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            l.add(data.get(i).getPath());
        }
        return l;
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }


    class ItemVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView videoName,videoInfo;
        LinearLayout parent;
        public ItemVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            videoName = itemView.findViewById(R.id.video_name);
            videoInfo = itemView.findViewById(R.id.video_info);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
