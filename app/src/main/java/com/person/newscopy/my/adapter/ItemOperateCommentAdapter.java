package com.person.newscopy.my.adapter;

import android.content.Context;
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
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.user.net.bean.CommentsBean;

import java.util.List;

public class ItemOperateCommentAdapter extends RecyclerView.Adapter<ItemOperateCommentAdapter.CommentViewHolder>{

    private List<CommentsBean> comments;
    private Fragment fragment;
    private Context context;

    public ItemOperateCommentAdapter(List<CommentsBean> comments, Fragment fragment) {
        this.comments = comments;
        this.fragment = fragment;
        context = fragment.getContext();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_operate_article_comment,viewGroup,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        CommentsBean commentsBean = comments.get(i);
        Glide.with(fragment)
                .load(commentsBean.getIcon())
                .asBitmap()
                .into(commentViewHolder.icon);
        Glide.with(fragment)
                .load(commentsBean.getImage())
                .into(commentViewHolder.contentImage);
        commentViewHolder.likeNum.setText(commentsBean.getLikeNum()+"");
        //commentViewHolder.sendNum.setText(commentsBean.g);
        //commentViewHolder.commentNum.setText();
        commentViewHolder.commentContent.setText(commentsBean.getContent());
        commentViewHolder.content.setText(commentsBean.getTitle());
        commentViewHolder.time.setText(commentsBean.getTime());
        commentViewHolder.name.setText(commentsBean.getName());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;
        TextView sendNum;
        TextView commentNum;
        TextView likeNum;
        TextView commentContent;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.release_user_icon);
            name = itemView.findViewById(R.id.release_user_name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content_title);
            contentImage = itemView.findViewById(R.id.content_image);
            commentNum = itemView.findViewById(R.id.comment_num);
            likeNum = itemView.findViewById(R.id.like_num);
            commentContent = itemView.findViewById(R.id.comment_content);
        }
    }
}
