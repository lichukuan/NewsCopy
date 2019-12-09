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
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.user.net.bean.PrivateTalkOutBeansBean;
import com.person.newscopy.user.net.bean.SimpleTalkBean;

import java.util.List;

public class PrivateTalkAllAdapter extends RecyclerView.Adapter<PrivateTalkAllAdapter.ViewHolder> {

    List<SimpleTalkBean> l;
    Fragment fragment;
    Context context;

    public PrivateTalkAllAdapter(List<SimpleTalkBean> l, Fragment fragment) {
        this.l = l;
        this.fragment = fragment;
        context = fragment.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_private_talk_simple,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SimpleTalkBean privateTalkOutBeansBean = l.get(i);
        Glide.with(fragment)
                .load(privateTalkOutBeansBean.getIcon())
                .asBitmap()
                .into(viewHolder.icon);
        viewHolder.content.setText(privateTalkOutBeansBean.getLastContent());
        viewHolder.name.setText(privateTalkOutBeansBean.getName());
        viewHolder.view.setOnClickListener(v -> {
            MyActivity activity = (MyActivity) fragment.getActivity();
            activity.setPrivateTalkRequireId(privateTalkOutBeansBean.getUserId());
            activity.setLeftName(privateTalkOutBeansBean.getName());
            activity.setLeftIcon(privateTalkOutBeansBean.getIcon());
            activity.changeFragment(MyActivity.PRIVATE_TALK_INFO_TYPE,true);
        });
    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
       ShapeImageView icon;
       TextView name;
       TextView content;
       View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            view = itemView.findViewById(R.id.item);
        }
    }
}
