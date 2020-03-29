package com.person.newscopy.my.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.user.net.bean.CareOrFansBean;

import java.util.List;

public class AllFansAdapter extends RecyclerView.Adapter <AllFansAdapter.FansViewHolder>{

    private List<CareOrFansBean> fans;
    private Context context;
    private Fragment fragment;

    public AllFansAdapter(List<CareOrFansBean> fans, Fragment fragment) {
        this.fans = fans;
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    @NonNull
    @Override
    public FansViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_fans,viewGroup,false);
        return new FansViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FansViewHolder fansViewHolder, int i) {
        CareOrFansBean bean = fans.get(i);
        Glide.with(fragment)
                .asBitmap()
                .load(bean.getIcon())
                .into(fansViewHolder.fansIcon);
        fansViewHolder.fansName.setText(bean.getName());
        fansViewHolder.fansRecommend.setText(bean.getRecommend());
        fansViewHolder.all.setOnClickListener(v -> {
            Intent intent = new Intent(context, MyActivity.class);
            intent.putExtra(MyActivity.MY_TYPE,MyActivity.USER_WORK_TYPE)
                    .putExtra(MyActivity.SEARCH_KEY,bean.getId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return fans.size();
    }

    class FansViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView fansIcon;
        TextView fansName;
        TextView fansRecommend;
        View all;
        public FansViewHolder(@NonNull View view) {
            super(view);
            fansIcon = view.findViewById(R.id.fans_icon);
            fansName = view.findViewById(R.id.fans_name);
            fansRecommend = view.findViewById(R.id.fans_recommend);
            all = view.findViewById(R.id.all);
        }
    }
}
