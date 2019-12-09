package com.person.newscopy.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.ShapeImageView;
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
                    .load(b.getIcon())
                    .asBitmap()
                    .into(careMessageViewHolder.icon);
            careMessageViewHolder.name.setText(b.getContent());
            careMessageViewHolder.time.setText(b.getTime());
        }else {
            CommentMessageViewHolder commentMessageViewHolder = (CommentMessageViewHolder) viewHolder;
            commentMessageViewHolder.name.setText(b.getContent());
            commentMessageViewHolder.time.setText(b.getTime());
            Glide.with(fragment)
                    .load(b.getIcon())
                    .asBitmap()
                    .into(commentMessageViewHolder.icon);
            final int type = messageBeans.get(i).getType();
            if (type == Config.MESSAGE.COMMENT_TYPE){

            }else if (type == Config.MESSAGE.SAVE_TYPE){

            }else if (type == Config.MESSAGE.SEND_TYPE){

            }else{//type.equals(valueOf(R.string.message_type_system))

            }
        }
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
            name = itemView.findViewById(R.id.content);
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
            name = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content);
        }
    }

}
