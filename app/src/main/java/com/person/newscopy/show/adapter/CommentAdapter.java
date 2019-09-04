package com.person.newscopy.show.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.news.network.shortBean.CommentsBean;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter {

   private List<CommentsBean> comments;

   private Context context;

   private Fragment fragment;

   public static final int TYPE_DOWNLOAD = -1;

    public CommentAdapter(List<CommentsBean> comments, Fragment fragment) {
        this.comments = comments;
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_DOWNLOAD){
            view = LayoutInflater.from(context).inflate(R.layout.download_li_item,parent,false);
            return new DownloadViewHolder(view);
        }
        view = LayoutInflater.from(context).inflate(R.layout.show_comment_item,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1)return TYPE_DOWNLOAD;
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount()-1){
            DownloadViewHolder h = (DownloadViewHolder) holder;
            h.download.setOnClickListener(v -> openApplicationMarket("com.mobile.videonews.li.video"));
            return;
        }
        CommentViewHolder holder1 = (CommentViewHolder)(holder);
         CommentsBean bean = comments.get(position);
         holder1.reply.setText(bean.getReply());
         holder1.like.setText(bean.getLike());
         holder1.content.setText(bean.getContent());
         holder1.time.setText(bean.getTime());
         holder1.name.setText(bean.getName());
         Glide.with(fragment)
                .load(bean.getIcon())
                .asBitmap()
                .into(holder1.icon);
    }

    private void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            context.startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "打开应用商店失败", Toast.LENGTH_SHORT).show();
            // 调用系统浏览器进入商城
            String url = "https://www.pearvideo.com/";
            openLinkBySystem(url);
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView name,content,time,like,reply;
        public CommentViewHolder(View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            name=itemView.findViewById(R.id.name);
            content=itemView.findViewById(R.id.content);
            time=itemView.findViewById(R.id.time);
            like=itemView.findViewById(R.id.like);
            reply=itemView.findViewById(R.id.reply);
        }
    }

    class DownloadViewHolder extends RecyclerView.ViewHolder{
        TextView download;
        public DownloadViewHolder(View itemView) {
            super(itemView);
            download=itemView.findViewById(R.id.download);
        }
    }
}
