package com.person.newscopy.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.AllPrivateTalkInfoBean;
import com.person.newscopy.user.net.bean.ContentsBean;

import java.util.List;

public class PrivateTalkInfoAdapter extends RecyclerView.Adapter {

    private List<ContentsBean> data;
    private Fragment fragment;
    private Context context;
    private String leftName;
    private String leftIcon;
    private String leftId;

    public PrivateTalkInfoAdapter(AllPrivateTalkInfoBean bean, Fragment fragment) {
        this.fragment = fragment;
        data = bean.getResult().getContents();
        context = fragment.getContext();
        this.leftName = bean.getResult().getLeftName();
        this.leftIcon = bean.getResult().getLeftIcon();
        this.leftId = bean.getResult().getLeftId();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ContentsBean r = data.get(i);
        if (r.getType().equals("left")){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_private_talk_left,viewGroup,false);
            return new LeftViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_private_talk_right,viewGroup,false);
            return  new RightViewHolder(view);
        }
    }


    public void addTalk(ContentsBean b){
        data.add(b);
        notifyItemInserted(data.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ContentsBean d = data.get(i);
        if (viewHolder instanceof LeftViewHolder){
            LeftViewHolder leftViewHolder = (LeftViewHolder) viewHolder;
            leftViewHolder.content.setText(d.getContent());
            leftViewHolder.name.setText(leftName);
            Glide.with(fragment)
                    .asBitmap()
                    .load(leftIcon)
                    .into(leftViewHolder.icon);
        }else if (viewHolder instanceof RightViewHolder){
            RightViewHolder rightViewHolder = (RightViewHolder) viewHolder;
            rightViewHolder.content.setText(d.getContent());
            rightViewHolder.name.setText(Users.userName);
            Glide.with(fragment)
                    .asBitmap()
                    .load(Users.userIcon)
                    .into(rightViewHolder.icon);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LeftViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView content;
        TextView name;
        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.other_icon);
            content = itemView.findViewById(R.id.other_content);
            name = itemView.findViewById(R.id.other_name);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView content;
        TextView name;
        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.my_icon);
            content = itemView.findViewById(R.id.my_content);
            name = itemView.findViewById(R.id.my_name);
        }
    }
}
