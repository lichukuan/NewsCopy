package com.person.newscopy.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.person.newscopy.R;
import com.person.newscopy.news.network.bean.DataBeanX;
import com.person.newscopy.news.network.bean.DataBeanXXXXXX;
import com.person.newscopy.news.network.bean.DataBeanXXXXXXX;
import com.person.newscopy.news.network.bean.VideoSearchBean;

import java.util.List;

public class HotNewsSearchAdapter extends RecyclerView.Adapter<HotNewsSearchAdapter.ViewHolder> {

    private Context context;

    private List<DataBeanX> content;

    private List<DataBeanXXXXXX> videoSearchBeanList;

    private boolean isVideoSearch=false;

    public HotNewsSearchAdapter(Context context, List<DataBeanX> content,List<DataBeanXXXXXX> videoSearchBeanList) {
        this.context=context;
        if (content==null)isVideoSearch=true;
        this.content=content;
        this.videoSearchBeanList=videoSearchBeanList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_search_content,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          if(isVideoSearch){
              holder.left.setText(videoSearchBeanList.get(position*2).getWord());
              holder.right.setText(videoSearchBeanList.get(position*2+1).getWord());
          }else {
              holder.left.setText(content.get(position*2).getTitle());
              holder.right.setText(content.get(position*2+1).getTitle());
          }
    }

    @Override
    public int getItemCount() {
        if (!isVideoSearch)
        return content.size()/2;
        else return videoSearchBeanList.size()/2;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView left;
        TextView right;
        ViewHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.content_left);
            right = itemView.findViewById(R.id.content_right);
        }
    }

}
