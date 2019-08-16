package com.person.newscopy.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;
import com.person.newscopy.show.ShowShortVideoActivity;

import java.util.ArrayList;
import java.util.List;

public class ShortAdapter extends RecyclerView.Adapter {

    private List<ShortInfoBean> data = new ArrayList<>();

    private Context context;

    public ShortAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShortInfoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_short,parent,false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          ShortInfoBean b1 = data.get(position*2);
          ShortInfoBean b2 = data.get(position*2+1);
          NormalViewHolder h = (NormalViewHolder) holder;
          Glide.with(context)
                .load(b1.getImage())
                .asBitmap()
                .into(h.image_1);
          Glide.with(context)
                  .load(b2.getImage())
                  .asBitmap()
                  .into(h.image_2);
          h.author_1.setText(b1.getAuthor());
          h.title_1.setText(b1.getTitle());
          h.time_1.setText(b1.getDuration());
          h.like_1.setText(b1.getLike());
          h.author_2.setText(b2.getAuthor());
          h.title_2.setText(b2.getTitle());
          h.time_2.setText(b2.getDuration());
          h.like_2.setText(b2.getLike());
          h.card_1.setOnClickListener(v->show(b1));
          h.card_2.setOnClickListener(v->show(b2));
    }

    private void show(ShortInfoBean bean){
        Intent intent = new Intent(context,ShowShortVideoActivity.class);
        Gson gson = new Gson();
        intent.putExtra(ShowShortVideoActivity.SHORT_VIDEO_INFO_KEY,gson.toJson(bean));
        context.startActivity(intent);
    }


    class NormalViewHolder extends RecyclerView.ViewHolder{
        CardView card_1,card_2;
        ImageView image_1,image_2;
        TextView like_1,like_2;
        TextView time_1,time_2;
        TextView title_1,title_2;
        TextView author_1,author_2;
        ImageView close_1,close_2;
        public NormalViewHolder(View itemView) {
            super(itemView);
            card_1 = itemView.findViewById(R.id.card_1);
            image_1=itemView.findViewById(R.id.image_1);
            like_1=itemView.findViewById(R.id.like_1);
            time_1=itemView.findViewById(R.id.time_1);
            title_1=itemView.findViewById(R.id.title_1);
            author_1=itemView.findViewById(R.id.author_1);
            close_1=itemView.findViewById(R.id.close_1);
            card_2 = itemView.findViewById(R.id.card_2);
            image_2=itemView.findViewById(R.id.image_2);
            like_2=itemView.findViewById(R.id.like_2);
            time_2=itemView.findViewById(R.id.time_2);
            title_2=itemView.findViewById(R.id.title_2);
            author_2=itemView.findViewById(R.id.author_2);
            close_2=itemView.findViewById(R.id.close_2);
        }
    }

    @Override
    public int getItemCount() {
        return data.size()/2;
    }
}
