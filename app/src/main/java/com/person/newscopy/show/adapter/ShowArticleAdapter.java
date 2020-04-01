package com.person.newscopy.show.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.view.DoubleClickListenerLinearLayout;
import com.person.newscopy.edit.HtmlType;
import com.person.newscopy.edit.adapter.DefaultEditAdapter;
import com.person.newscopy.edit.bean.EditBean;
import com.person.newscopy.image.ImageActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowArticleAdapter extends RecyclerView.Adapter {

    private List<EditBean> data = null;
    private Context context;
    private ArrayList<EditBean> images = new ArrayList<>();
    private ArrayList<String> imagesUrl = new ArrayList<>();
    private Activity fragment;

    public ShowArticleAdapter(List<EditBean> data,Activity fragment) {
        this.data = data;
        this.context = fragment;
        this.fragment = fragment;
        for (int i = 0; i <data.size() ; i++) {
            EditBean editBean = data.get(i);
            if (editBean.getType() == HtmlType.COVER_TYPE || editBean.getType() == HtmlType.IMAGE_TYPE){
                if (editBean.getCover() != null || editBean.getImage() != null){
                    images.add(editBean);
                    imagesUrl.add(editBean.getImage() != null?editBean.getImage():editBean.getCover());
                }
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.preview_image_item,viewGroup,false);
            return new ImageViewHolder(view);
        }else if (i == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.preview_title_item,viewGroup,false);
            return new TitleViewHolder(view);
        }else if (i == 2){
            View view = LayoutInflater.from(context).inflate(R.layout.preview_link_item,viewGroup,false);
            return new LinkViewHolder(view);
        }else if (i == 3){
            View view = LayoutInflater.from(context).inflate(R.layout.preview_content_item,viewGroup,false);
            return new TextViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder == null)return;
         EditBean editBean = data.get(i);
         int type = editBean.getType();
         if (viewHolder instanceof ImageViewHolder){
             ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
             String url = editBean.getCover() == null ? editBean.getImage():editBean.getCover();
             Glide.with(fragment)
                     .asBitmap()
                     .load(url)
                     .into(imageViewHolder.image);
             imageViewHolder.image.setOnClickListener(v -> {
                 Intent intent = new Intent(context,ImageActivity.class);
                 intent.putExtra(ImageActivity.REQUIRE_TYPE,ImageActivity.TYPE_SHOW);
                 intent.putExtra(ImageActivity.SHOW_IMAGE_INDEX,images.indexOf(editBean));
                 intent.putExtra(ImageActivity.SHOW_ARRAY_DATA,imagesUrl);
                 context.startActivity(intent);
             });
         }else if (viewHolder instanceof TitleViewHolder){
             TitleViewHolder textViewHolder = (TitleViewHolder) viewHolder;
             textViewHolder.text.setText(editBean.getTitle());
             float density = ScreenFitUtil.getDensity();
             switch (type){
                 case HtmlType.SECOND_TITLE:
                     textViewHolder.text.setTextSize(18);
                     break;
                 case HtmlType.THREE_TITLE:
                     textViewHolder.text.setTextSize(16);
                     break;
                 case HtmlType.TITLE_TYPE:
                     textViewHolder.text.setTextSize(20);
                     break;
             }
         }else if (viewHolder instanceof LinkViewHolder){
             LinkViewHolder linkViewHolder = (LinkViewHolder) viewHolder;
             String[] d = editBean.getLink().split("#");
             linkViewHolder.text.setText(d[0]);
             linkViewHolder.text.setOnClickListener(v -> {
                 Intent intent=new Intent(Intent.ACTION_VIEW);
                 intent.setData(Uri.parse(d[1]));
                 context.startActivity(intent);
             });
         }else if (viewHolder instanceof TextViewHolder){
             TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
             textViewHolder.text.setText(editBean.getText());
         }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ImageViewHolder)
        {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Glide.with(context).clear(imageViewHolder.image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = data.get(position).getType();
        switch (type){
            case HtmlType.COVER_TYPE:
            case HtmlType.IMAGE_TYPE:
            return 0;
            case HtmlType.SECOND_TITLE:
            case HtmlType.THREE_TITLE:
            case HtmlType.TITLE_TYPE:
                return 1;
            case HtmlType.LINK_TYPE:
                return 2;
            case HtmlType.TEXT_TYPE:
                return 3;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class TextViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.content);
        }
    }

    public  class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.title);
        }
    }

    public  class LinkViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.link);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }

}
