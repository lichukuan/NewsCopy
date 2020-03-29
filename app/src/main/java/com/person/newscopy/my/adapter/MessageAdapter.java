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
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.show.ShowNewsActivity;
import com.person.newscopy.show.ShowVideoActivity;
import com.person.newscopy.show.net.bean.MessageCommentBean;
import com.person.newscopy.show.net.bean.MessageSaveAndLikeBean;
import com.person.newscopy.user.net.bean.MessageContentBean;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private List<MessageContentBean> messageBeans;
    private Fragment fragment;
    private Context context;

    public MessageAdapter(List<MessageContentBean> messageBeans, Fragment fragment) {
        this.messageBeans = messageBeans;
        this.fragment = fragment;
        context = fragment.getContext();
    }

    public void setMessageBeans(List<MessageContentBean> messageBeans) {
        this.messageBeans = messageBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final int type = messageBeans.get(i).getType();
        if (type == Config.MESSAGE.CARE_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_message_care,viewGroup,false);
            return new CareMessageViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_my_message_comment,viewGroup,false);
            return new CommentMessageViewHolder(view);
        }
}

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MessageContentBean b = messageBeans.get(i);
        if (viewHolder instanceof CareMessageViewHolder){
            CareMessageViewHolder careMessageViewHolder = (CareMessageViewHolder) viewHolder;
            Glide.with(fragment)
                    .asBitmap()
                    .load(b.getIcon())
                    .into(careMessageViewHolder.icon);
            careMessageViewHolder.icon.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyActivity.class);
                intent.putExtra(MyActivity.MY_TYPE,MyActivity.USER_WORK_TYPE);
                intent.putExtra(MyActivity.SEARCH_KEY,b.getFromId());
                context.startActivity(intent);
            });
            careMessageViewHolder.name.setText(b.getName()+"关注了你");
            careMessageViewHolder.time.setText(b.getTime());
        }else {
            CommentMessageViewHolder commentMessageViewHolder = (CommentMessageViewHolder) viewHolder;
            commentMessageViewHolder.time.setText(b.getTime());
            Glide.with(fragment)
                    .asBitmap()
                    .load(b.getIcon())
                    .into(commentMessageViewHolder.icon);
            commentMessageViewHolder.icon.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyActivity.class);
                intent.putExtra(MyActivity.MY_TYPE,MyActivity.USER_WORK_TYPE);
                intent.putExtra(MyActivity.SEARCH_KEY,b.getFromId());
                context.startActivity(intent);
            });
            final int type = b.getType();
            if (type == Config.MESSAGE.COMMENT_TYPE){
                MessageCommentBean messageCommentBean = BaseUtil.getGson().fromJson(b.getContent(),MessageCommentBean.class);
                commentMessageViewHolder.name.setText(b.getName()+"评论了你");
                commentMessageViewHolder.content.setText(messageCommentBean.getCommentContent());
            }else if (type == Config.MESSAGE.SAVE_TYPE){
                MessageSaveAndLikeBean messageSaveAndLikeBean = BaseUtil.getGson().fromJson(b.getContent(),MessageSaveAndLikeBean.class);
                if (messageSaveAndLikeBean.getType() == Config.CONTENT.NEWS_TYPE){
                    commentMessageViewHolder.name.setText(b.getName()+"收藏了您发布的文章《"+messageSaveAndLikeBean.getTitle()+"》");
                    commentMessageViewHolder.name.setOnClickListener(v -> goToArticle(messageSaveAndLikeBean.getData()));
                }else{
                    commentMessageViewHolder.name.setText(b.getName()+"收藏了您发布的视频《"+messageSaveAndLikeBean.getTitle()+"》");
                    commentMessageViewHolder.name.setOnClickListener(v -> goToVideo(messageSaveAndLikeBean.getData()));
                }
                commentMessageViewHolder.content.setVisibility(View.GONE);
            }else if (type == Config.MESSAGE.LIKE_TYPE){
                MessageSaveAndLikeBean messageSaveAndLikeBean = BaseUtil.getGson().fromJson(b.getContent(),MessageSaveAndLikeBean.class);
                if (messageSaveAndLikeBean.getType() == Config.CONTENT.NEWS_TYPE){
                    commentMessageViewHolder.name.setText(b.getName()+"赞了您发布的文章《"+messageSaveAndLikeBean.getTitle()+"》");
                    commentMessageViewHolder.name.setOnClickListener(v -> goToArticle(messageSaveAndLikeBean.getData()));
                }else{
                    commentMessageViewHolder.name.setText(b.getName()+"赞了您发布的视频《"+messageSaveAndLikeBean.getTitle()+"》");
                    commentMessageViewHolder.name.setOnClickListener(v -> goToVideo(messageSaveAndLikeBean.getData()));
                }
                commentMessageViewHolder.content.setVisibility(View.GONE);
            }else if (type == Config.MESSAGE.PRIVATE_TALK_TYPE){
                 commentMessageViewHolder.name.setText(b.getName()+"私信了你");
                 commentMessageViewHolder.content.setText(b.getContent());
                 commentMessageViewHolder.name.setOnClickListener(v -> goToPrivateTalk(b.getFromId()) );
                 commentMessageViewHolder.content.setOnClickListener(v -> goToPrivateTalk(b.getFromId()));
            }
        }
    }

    private void goToPrivateTalk(String id){
        Intent intent = new Intent(context,MyActivity.class);
        intent.putExtra(MyActivity.MY_TYPE,MyActivity.PRIVATE_TALK_INFO_TYPE);
        intent.putExtra(MyActivity.PRIVATE_TALK_REQUIRE_ID,id);
        context.startActivity(intent);
    }

    private void goToArticle(String data){
        Intent intent = new Intent(context, ShowNewsActivity.class);
        intent.putExtra(ShowNewsActivity.SHOW_WEB_INFO,data);
        context.startActivity(intent);
    }

    private void goToVideo(String data){
        Intent intent = new Intent(context, ShowVideoActivity.class);
        intent.putExtra(ShowVideoActivity.SHORT_VIDEO_INFO_KEY,data);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return messageBeans.size();
    }

    class CareMessageViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView name;
        TextView time;
        public CareMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
        }
    }

    class CommentMessageViewHolder extends RecyclerView.ViewHolder{
        ShapeImageView icon;
        TextView name;
        TextView time;
        TextView content;
        public CommentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.comment);
        }
    }

}
