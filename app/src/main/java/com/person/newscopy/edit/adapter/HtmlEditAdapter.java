package com.person.newscopy.edit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.edit.HtmlType;
import com.person.newscopy.edit.Type;
import com.person.newscopy.edit.bean.EditBean;
import java.util.ArrayList;
import java.util.List;

public class HtmlEditAdapter extends DefaultEditAdapter {

    private Context context;

    public HtmlEditAdapter(Fragment fragment) {
        super(fragment);
        this.context = fragment.getContext();
    }

    @Override
    protected void bindAdd(RecyclerView.ViewHolder viewHolder, EditBean b) {
        AddViewHolder addViewHolder = (AddViewHolder) viewHolder;
        addViewHolder.add.setVisibility(View.GONE);
    }

    @Override
    protected void bindOther(RecyclerView.ViewHolder viewHolder, EditBean b) {
        int type = b.getType();
        switch (type){
            case HtmlType.SECOND_TITLE:
                bindSecondTitle(viewHolder,b);
                return;
            case HtmlType.THREE_TITLE:
                bindThreeTitle(viewHolder,b);
                return;
            case HtmlType.LIST_TITLE:
                bindList(viewHolder, b);
                return;
        }
    }

    public void bindSecondTitle(RecyclerView.ViewHolder viewHolder,EditBean b){
        SecondTitleViewHolder textViewHolder = (SecondTitleViewHolder) viewHolder;
        if(b.getTitle() != null&&textViewHolder.secondTitle.getText().toString().equals(""))
            textViewHolder.secondTitle.setText(b.getTitle());
        textViewHolder.secondTitle.addTextChangedListener(new TextWatcher() {
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
        textViewHolder.secondTitle.setOnLongClickListener(v -> {
            deleteAndAddItem(b);
            return true;
        });
    }

    public void bindThreeTitle(RecyclerView.ViewHolder viewHolder,EditBean b){
        ThreeTitleViewHolder textViewHolder = (ThreeTitleViewHolder) viewHolder;
        if(b.getTitle() != null&&textViewHolder.threeTitle.getText().toString().equals(""))
            textViewHolder.threeTitle.setText(b.getTitle());
        textViewHolder.threeTitle.addTextChangedListener(new TextWatcher() {
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
        textViewHolder.threeTitle.setOnLongClickListener(v -> {
            deleteAndAddItem(b);
            return true;
        });
    }

    public void bindList(RecyclerView.ViewHolder viewHolder,EditBean bean){

    }

    @Override
    public String toFormatContent() {
        return toHtml();
    }

    @Override
    public void createNewItem(int position) {
        AlertDialog.Builder listDialog = new AlertDialog.Builder(context);
        //创建存储数据的数组
        String[] ITEM_KINDS = {"添加图片","添加链接","添加文本","二级标题","三级标题"};
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
                case 3:
                    createSecondTitle(position);
                    break;
                case 4:
                    createThreeTitle(position);
                    break;
                case 5:
                    createList(position);
                    break;
                default:
                    createOther(i,position);
            }
            if(position +1 < getItemCount()&&data.get(position+1).getType() != Type.TEXT_TYPE && position == getItemCount()-2){
                createNewText(getItemCount() - 1);
            }
        });
        listDialog.show();
    }


    public void createSecondTitle(int position){
        EditBean editBean = new EditBean();
        editBean.setType(HtmlType.SECOND_TITLE);
        data.add(position,editBean);
        notifyItemInserted(position);
    }

    public void createThreeTitle(int position){
        EditBean editBean = new EditBean();
        editBean.setType(HtmlType.THREE_TITLE);
        data.add(position,editBean);
        notifyItemInserted(position);
    }

    public void createList(int position){
        EditBean editBean = new EditBean();
        editBean.setType(HtmlType.LIST_TITLE);
        data.add(position,editBean);
        notifyItemInserted(position);
    }

    @Override
    protected RecyclerView.ViewHolder loadOther(int viewType, ViewGroup root) {
        switch (viewType){
            case HtmlType.SECOND_TITLE:
                return new SecondTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_second_title,root,false));
            case HtmlType.THREE_TITLE:
                return new ThreeTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_three_title,root,false));
            case HtmlType.LIST_TITLE:
                return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_edit_list,root,false));
        }
        return super.loadOther(viewType, root);
    }

    public class SecondTitleViewHolder extends RecyclerView.ViewHolder{
        EditText secondTitle;
        public SecondTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            secondTitle = itemView.findViewById(R.id.second_title);
        }
    }

    public class ThreeTitleViewHolder extends RecyclerView.ViewHolder{
        EditText threeTitle;
        public ThreeTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            threeTitle = itemView.findViewById(R.id.three_title);
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        LinearLayout listParent;
        EditText item1;
        EditText item2;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            listParent = itemView.findViewById(R.id.list_parent);
            item1 = itemView.findViewById(R.id.item_1);
            item2 = itemView.findViewById(R.id.item_2);
        }
    }

    public String toHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<article>");
        builder.append("<h1>"+data.get(1).getTitle()+"</h1>");
        //生成封面
        if(data.get(0).getCover() != null)
            builder.append("<img src = \""+data.get(0).getCover()+"\" width = \"100%\" />");
        for (int i = 2; i < data.size(); i++) {
            EditBean editBean = data.get(i);
            switch (editBean.getType()){
                case Type.TEXT_TYPE:
                    if(editBean.getText() != null)
                        //.replaceAll(" ","&nbsp").replaceAll("\n","<br/>")
                    builder.append("<p>"+editBean.getText()+"</p>");
                    break;
                case Type.IMAGE_TYPE:
                    builder.append("<img src = \""+editBean.getImage()+"\" width = \"100%\" />");
                    break;
                case Type.LINK_TYPE:
                    String[] d = editBean.getLink().split("#");
                    builder.append("<a href = \""+d[1]+"\">"+d[0]+"</a>");
                    break;
                case HtmlType.SECOND_TITLE:
                    builder.append("<h2>"+editBean.getTitle()+"</h2>");
                    break;
                case HtmlType.THREE_TITLE:
                    builder.append("<h3>"+editBean.getTitle()+"</h3>");
                    break;
            }
        }
        builder.append("</article></html>");
        builder.append("<json>");
        builder.append(getJsonData());
        builder.append("<json>");
        return builder.toString();
    }

    public String getCover(){
        return data.get(0).getCover();
    }

    public List<String> getImages(){
        List<String> l = new ArrayList<>();
        for (int i = 2; i < data.size(); i++) {
            if(data.get(i).getType() == Type.IMAGE_TYPE)
                l.add(data.get(i).getImage());
        }
        return l;
    }

    public String getJsonData(){
        return BaseUtil.getGson().toJson(data);
    }

    public String getTitle(){
        return data.get(1).getTitle();
    }

}
