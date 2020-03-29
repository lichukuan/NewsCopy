package com.person.newscopy.edit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.view.DoubleClickListenerLinearLayout;
import com.person.newscopy.edit.Type;
import com.person.newscopy.edit.bean.EditBean;

import java.util.ArrayList;
import java.util.Objects;

public class DefaultEditAdapter extends RecyclerView.Adapter {

    private Context context;

    private Fragment fragment;

     protected ArrayList<EditBean> data = new ArrayList<>();

     private LoadCallback loadImageCallback;

    public void setLoadImageCallback(LoadCallback loadImageCallback) {
        this.loadImageCallback = loadImageCallback;
    }

    public DefaultEditAdapter(Fragment fragment) {
        this.context = fragment.getContext();
        this.fragment = fragment;
        init();
    }

    protected void init(){
        EditBean add = new EditBean();
        add.setType(Type.ADD_TYPE);

        EditBean title = new EditBean();
        title.setType(Type.TITLE_TYPE);

        EditBean cover = new EditBean();
        cover.setType(Type.COVER_TYPE);

        EditBean text = new EditBean();
        text.setType(Type.TEXT_TYPE);

        data.add(cover);
        data.add(title);
        data.add(text);
        data.add(add);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case Type.ADD_TYPE:
                viewHolder =  loadAdd(viewGroup);
                break;
            case Type.COVER_TYPE:
                viewHolder = loadCover(viewGroup);
                break;
            case Type.TITLE_TYPE:
                viewHolder = loadTitle(viewGroup);
                break;
            case Type.TEXT_TYPE:
                viewHolder = loadText(viewGroup);
                break;
            case Type.IMAGE_TYPE:
                viewHolder = loadImage(viewGroup);
                break;
            case Type.LINK_TYPE:
                viewHolder = loadLink(viewGroup);
                break;
            default:
                viewHolder = loadOther(viewType,viewGroup);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        EditBean bean = data.get(i);
        int type = bean.getType();
        switch (type){
            case Type.ADD_TYPE:
                bindAdd(viewHolder,bean);
                break;
            case Type.COVER_TYPE:
                bindCover(viewHolder,bean);
                break;
            case Type.TITLE_TYPE:
                bindTitle(viewHolder,bean);
                break;
            case Type.TEXT_TYPE:
                bindText(viewHolder,bean);
                break;
            case Type.IMAGE_TYPE:
                bindImage(viewHolder,bean);
                break;
            case Type.LINK_TYPE:
                bindLink(viewHolder,bean);
                break;
            default:
                bindOther(viewHolder,bean);
                break;
        }
    }

    protected void bindCover(RecyclerView.ViewHolder viewHolder, EditBean b){
        CoverViewHolder coverViewHolder = (CoverViewHolder) viewHolder;
        Glide.with(fragment)
                .load(b.getCover())
                .into(coverViewHolder.cover);
        coverViewHolder.tag.setOnClickListener(v -> {
            if (loadImageCallback != null)loadImageCallback.loadCover(0);
        });
    }

    protected void bindTitle(RecyclerView.ViewHolder viewHolder, EditBean b){
        TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
        if(b.getTitle() != null&&titleViewHolder.title.getText().toString().equals(""))
            titleViewHolder.title.setText(b.getTitle());
        titleViewHolder.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 b.setTitle(s.toString());
            }
        });
    }

    protected void bindLink(RecyclerView.ViewHolder viewHolder,EditBean b){
        LinkViewHolder linkViewHolder = (LinkViewHolder) viewHolder;
        linkViewHolder.link.setText(b.getLink());
        linkViewHolder.link.setOnLongClickListener(v -> {
            deleteAndAddItem(b);
            return true;
        });
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ImageViewHolder)
        {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Glide.with(context).clear(imageViewHolder.image);
        }
        super.onViewRecycled(holder);
    }

    protected void bindImage(RecyclerView.ViewHolder viewHolder, EditBean b){
        ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
        Glide.with(fragment)
                .load(b.getImage())
                .into(imageViewHolder.image);
        imageViewHolder.image.setOnClickListener(v -> {
            deleteAndAddItem(b);
        });
    }

    protected void bindText(RecyclerView.ViewHolder viewHolder,EditBean b){
        TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
        if(b.getText() != null&&textViewHolder.text.getText().toString().equals(""))
            textViewHolder.text.setText(b.getText());
        textViewHolder.text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                b.setText(s.toString());
            }
        });
        textViewHolder.parent.setDoubleClickListener(() -> {
            deleteAndAddItem(b);
        });
    }

    public void deleteAndAddItem(EditBean b){
        int position = data.indexOf(b);
        AlertDialog.Builder listDialog = new AlertDialog.Builder(Objects.requireNonNull(fragment.getContext()));
        //创建存储数据的数组
        String[] d = {"删除当前项","添加新的项"};
        //显示列表，并为列表增加点击事件
        listDialog.setItems(d, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    data.remove(position);
                    notifyDataSetChanged();
                    break;
                case 1:
                    createNewItem(position+1);
                    break;
            }
        });
        listDialog.show();
    }

    public void createNewImage(int position){
        EditBean textBean = new EditBean();
        textBean.setType(Type.IMAGE_TYPE);
        data.add(position,textBean);
        if(loadImageCallback != null)loadImageCallback.loadImage(position);
    }

    public void createNewLink(int position){
        if (loadImageCallback == null)return;
        EditBean textBean = new EditBean();
        textBean.setType(Type.LINK_TYPE);
        data.add(position,textBean);
        loadImageCallback.loadLink(position);
    }

    public void createNewText(int position){
        if(data.get(position - 1).getType() == Type.TEXT_TYPE)return;
        EditBean textBean = new EditBean();
        textBean.setType(Type.TEXT_TYPE);
        data.add(position,textBean);
        notifyItemInserted(position);
    }

    public void insertImage(String url,int position){
        if (url == null || "".equals(url)){
          data.remove(position);
          return;
        }
        EditBean textBean = data.get(position);
        textBean.setType(Type.IMAGE_TYPE);
        textBean.setImage(url);
        notifyItemChanged(position);
    }

    public void insertCover(String url,int position){
        if (url == null || "".equals(url))return;
        EditBean textBean = data.get(position);
        textBean.setType(Type.COVER_TYPE);
        textBean.setCover(url);
        notifyItemChanged(position);
    }

    public void insertLink(String content,String link,int position){
        EditBean textBean = data.get(position);
        textBean.setType(Type.LINK_TYPE);
        textBean.setLink(content+"#"+link);
        data.add(position ,textBean);
        notifyItemInserted(position);
    }

    public void createOther(int location,int position){

    }

    public void createNewItem(int position){
        AlertDialog.Builder listDialog = new AlertDialog.Builder(fragment.getContext());
        //创建存储数据的数组
        String[] ITEM_KINDS = {"添加图片","添加链接","添加文本"};
        //显示列表，并为列表增加点击事件
        listDialog.setItems(ITEM_KINDS, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    createNewImage(position);
                    break;
                case 1:
                    createNewLink(position);
                    break;
                case 2:
                    createNewText(position);
                    break;
                default:
                    createOther(i,position);
            }
        });
        if(data.get(position).getType() != Type.TEXT_TYPE && position == getItemCount()){
            createNewText(getItemCount() - 1);
        }
        listDialog.show();
    }

    public interface LoadCallback{

        void loadImage(int position);

        void loadCover(int position);

        void loadLink(int position);
    }


    protected void bindAdd(RecyclerView.ViewHolder viewHolder,EditBean b){
       AddViewHolder addViewHolder = (AddViewHolder) viewHolder;
       addViewHolder.add.setOnClickListener(v -> {
           createNewItem(data.size() - 1);
       });
    }

    public void clear(){
        for (int i = 2; i <data.size() ; i++) {
            data.remove(i);
        }
        notifyDataSetChanged();
    }

    public String toFormatContent(){
        return "";
    }

    public float getDensity(){
        return ScreenFitUtil.getDensity();
    }

    protected void bindOther(RecyclerView.ViewHolder viewHolder,EditBean b){

    }

    protected RecyclerView.ViewHolder loadCover(ViewGroup viewGroup){
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_cover,viewGroup,false);
        return new CoverViewHolder(view);
    }


    protected RecyclerView.ViewHolder loadTitle(ViewGroup viewGroup){
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_title,viewGroup,false);
        return new TitleViewHolder(view);
    }

    protected RecyclerView.ViewHolder loadText(ViewGroup viewGroup){
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_text,viewGroup,false);
        return new TextViewHolder(view);
    }

    protected RecyclerView.ViewHolder loadImage(ViewGroup viewGroup){
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_image,viewGroup,false);
        return new ImageViewHolder(view);
    }

    protected RecyclerView.ViewHolder loadLink(ViewGroup viewGroup){
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_link,viewGroup,false);
        return new LinkViewHolder(view);
    }

    protected RecyclerView.ViewHolder loadAdd(ViewGroup viewGroup){
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_add,viewGroup,false);
        return new AddViewHolder(view);
    }

    protected RecyclerView.ViewHolder loadOther(int viewType,ViewGroup root){
        return null;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder{
        EditText text;
        DoubleClickListenerLinearLayout parent;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder{
        TextView link;
        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.link);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder{
        EditText title;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public static class CoverViewHolder extends RecyclerView.ViewHolder{
        TextView tag;
        ImageView cover;
        public CoverViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag);
            cover = itemView.findViewById(R.id.cover);
        }
    }

    public static class AddViewHolder extends RecyclerView.ViewHolder{
        ImageView add;
        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
        }
    }

}
