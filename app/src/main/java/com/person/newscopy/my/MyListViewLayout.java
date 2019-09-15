package com.person.newscopy.my;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;

public class MyListViewLayout extends FrameLayout {

    public void setContent(String[] content,int[] res){
        float d = ScreenFitUtil.getDensity();
        float width = ViewInfoUtil.ScreenInfo.getScreenWidth(this)/4;
        int j = 1;
        for (int i = 0; i <content.length ; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.my_list_item_view,this,false);
            ImageView icon = view.findViewById(R.id.icon);
            TextView text = view.findViewById(R.id.text);
            icon.setImageResource(res[i]);
            text.setText(content[i]);
            LayoutParams params = new LayoutParams((int) width,(int)(d*60));
            params.topMargin = (int) (20*d+100*(i/4)*d);
            if(j==5)j=1;
            params.leftMargin = (int)((j-1)*width);
            j++;
            view.setLayoutParams(params);
            addView(view);
        }
        requestLayout();
    }


    public MyListViewLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MyListViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }
}
