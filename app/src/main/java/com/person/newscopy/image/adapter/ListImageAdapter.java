package com.person.newscopy.image.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.image.ImageActivity;
import com.person.newscopy.image.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter {

    private Context context;
    private Fragment fragment;
    private List<ImageBean> data;
    private int width;
    private List<ImageBean> selectedImage = new ArrayList<>();
    private int num = 0;
    private int maxCount;

    public List<ImageBean> getSelectedImage() {
        return selectedImage;
    }

    public ListImageAdapter(Fragment fragment,int maxCount) {
        this.fragment = fragment;
        context = fragment.getContext();
        this.maxCount = maxCount;
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
        if (imageBean.getFlag() != 0){
            holder.num.setText(imageBean.getFlag()+"");
            holder.num.setChecked(true);
        }else{
            holder.num.setChecked(false);
            holder.num.setText("");
        }
        Glide.with(fragment)
                .load(imageBean.getPath())
                .apply(requestOptions)
                .into(holder.image);
        holder.image.setOnClickListener(v -> {
            ((ImageActivity)fragment.getActivity()).showImages(getImagePath(data),i);
        });
        holder.num.setOnClickListener(v -> {
            if (holder.num.isChecked()){
                holder.num.setChecked(false);
                holder.num.setText("");
                selectedImage.remove(imageBean.getFlag() - 1);
                imageBean.setFlag(0);
                for (int j = 0; j < selectedImage.size(); j++) {
                    selectedImage.get(j).setFlag(j+1);
                }
                num--;
                notifyDataSetChanged();
            }else{
                if (selectedImage.size() >= maxCount){
                    Toast.makeText(context, "最多选择"+maxCount+"张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                num++;
                imageBean.setFlag(num);
                selectedImage.add(imageBean);
                holder.num.setChecked(true);
                holder.num.setText(num+"");
            }
        });
    }

    public void preview(){
        if (selectedImage.size() > 0)
        ((ImageActivity)fragment.getActivity()).showImages(getImagePath(selectedImage),0);
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

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        ItemImageViewHolder itemImageViewHolder = (ItemImageViewHolder) holder;
        Glide.with(fragment).clear(itemImageViewHolder.image);
    }

    class ItemImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CheckedTextView num;
        public ItemImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            num = itemView.findViewById(R.id.num);
        }
    }
}
