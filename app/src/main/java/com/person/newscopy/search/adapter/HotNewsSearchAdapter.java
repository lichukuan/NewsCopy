package com.person.newscopy.search.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.search.SearchActivity;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;

import java.util.List;

public class HotNewsSearchAdapter extends RecyclerView.Adapter<HotNewsSearchAdapter.ViewHolder> {

    private SearchActivity context;

    private List<ResultBean> content;

    public HotNewsSearchAdapter(SearchActivity context, List<ResultBean> content) {
        this.context=context;
        this.content=content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_search_content,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          ResultBean resultBean = content.get(position);
          holder.content.setText(resultBean.getTitle());
          holder.content.setOnClickListener(v -> {
              context.addToHistory(resultBean.getTitle());
              if (resultBean.getType() == Config.CONTENT.NEWS_TYPE){
                  goToShowNewActivity(BaseUtil.getGson().toJson(resultBean));
              }else{
                  goToVideoNewActivity(BaseUtil.getGson().toJson(resultBean));
              }
          });
    }

    private void goToShowNewActivity(String url){
        Intent intent = new Intent(context,ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,url);
        context.startActivity(intent);
        context.finish();
    }

    private void goToVideoNewActivity(String url){
        Intent intent = new Intent(context,ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,url);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }

}
