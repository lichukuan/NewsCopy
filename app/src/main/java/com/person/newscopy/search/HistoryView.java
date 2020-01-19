package com.person.newscopy.search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HistoryView extends LinearLayout{

    private float screenHeight = 0;
    private float screenWidth = 0;
    private float density = 0;
    private float margin = 0;

    public HistoryView(Context context) {
        super(context);
        init();
    }

    public HistoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Set<String> data = new HashSet<>();

    private void init(){
        screenWidth = ViewInfoUtil.ScreenInfo.getScreenWidth(this);
        screenHeight = ViewInfoUtil.ScreenInfo.getScreenHeight(this);
        density = ScreenFitUtil.getDensity();
        Log.d("Search===density","density = "+density);
        margin = density * 5;
    }

    public interface OnHistoryItemClickListener{
        void click(String s);
    }

    private OnHistoryItemClickListener clickListener = null;

    public void setClickListener(OnHistoryItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

//    public void setData(Collection<String> s){
//        data.addAll(s);
//        for (String s1 : s) {
//            TextView textView = new TextView(getContext());
//            ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,(int) (30*density));
//            textView.setLayoutParams(layoutParams);
//            textView.setTextSize(17);
//            textView.setTextColor(Color.RED);
//            textView.setText(s1);
//            textView.setGravity(Gravity.CENTER);
//            textView.setOnClickListener(v -> {
//                if (clickListener != null)clickListener.click(s1);
//            });
//            addView(textView);
//        }
//        requestLayout();
//    }
//
//    public void setData(String s){
//        data.add(s);
//        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.history_text,null);
//        textView.setOnClickListener(v -> {
//            if (clickListener != null)clickListener.click(s);
//        });
//        textView.setText(s);
//        addView(textView);
//    }

    public void clearAll(){
        data.clear();
        removeAllViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float x = 0;
        float y = margin;
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //这个很重要，没有就不显示
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.AT_MOST), heightMeasureSpec);
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child =  getChildAt(i);
            if(x + child.getMeasuredWidth() + margin > screenWidth){
                x = margin;
                y += child.getMeasuredHeight() + margin;
            }
            x+=child.getMeasuredWidth() + margin;
        }
        if (getChildCount() > 0)
           y += getChildAt(0).getMeasuredHeight() + margin;
        setMeasuredDimension(widthMeasureSpec,MeasureSpec.makeMeasureSpec((int) y,MeasureSpec.AT_MOST));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("====History","left = "+left+" top = "+top+" right = "
                +right+" bottom = "+bottom+" childCount = "+getChildCount()+"  screenHeight = "+screenHeight);
        float x = margin;
        float y = margin;
        Log.d("===History","top = "+top+" getTop = "+getTop()+" getPivotY = "+getPivotY()+" getY() = "+getY());
        for (int i = 0; i < getChildCount(); i++) {
            View child =  getChildAt(i);
            if(x + child.getMeasuredWidth() + margin > screenWidth){
                x = margin;
                y += child.getMeasuredHeight() + margin;
            }
            child.layout((int)x,(int)y,(int)(x+child.getMeasuredWidth()),(int) (y+child.getMeasuredHeight()));
            Log.d("==History",x+" , "+y+" , "+(x+child.getMeasuredWidth())+" , "+(y+child.getMeasuredHeight()));
            x += child.getMeasuredWidth() + margin;
        }
    }
}
