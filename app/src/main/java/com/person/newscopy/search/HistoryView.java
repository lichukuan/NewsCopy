package com.person.newscopy.search;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HistoryView extends FrameLayout{

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
        margin = density * 10;
    }

    public interface OnHistoryItemClickListener{
        void click(String s);
    }

    private OnHistoryItemClickListener clickListener = null;

    public void setClickListener(OnHistoryItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(Collection<String> s){
        data.addAll(s);
        for (String s1 : s) {
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.history_text,null);
            textView.setText(s1);
            Log.d("==History","textView width = "+textView.getMeasuredHeight()+" , "+textView.getWidth());
            textView.setOnClickListener(v -> {
                if (clickListener != null)clickListener.click(s1);
            });
            addView(textView);
        }
        requestLayout();
    }

    public void setData(String s){
        data.add(s);
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.history_text,null);
        textView.setOnClickListener(v -> {
            if (clickListener != null)clickListener.click(s);
        });
        textView.setText(s);
        addView(textView);
    }

    public void clearAll(){
        data.clear();
        removeAllViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float x = 0;
        float y = margin;
        measureChildren(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.AT_MOST),heightMeasureSpec);
        Log.d("====History","screenWidth = "+screenWidth);
        for (int i = 0; i < getChildCount(); i++) {
            TextView child = (TextView) getChildAt(i);
            if(x + child.getMeasuredWidth() + margin > screenWidth){
                x = margin;
                y += child.getMeasuredHeight() + margin;
            }
            Log.d("===History=measure","x = "+x+" y = "+y);
            x+=child.getMeasuredWidth() + margin;
        }
        if (getChildCount() > 0)
           y += getChildAt(0).getMeasuredHeight() + margin;
        Log.d("===HistoryView","y = "+y);
        setMeasuredDimension(widthMeasureSpec,MeasureSpec.makeMeasureSpec((int)y,MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("====History","left = "+left+" top = "+top+" right = "
                +right+" bottom = "+bottom+" childCount = "+getChildCount()+"  screenHeight = "+screenHeight);
        float x = left;
        float y = top + margin;
        for (int i = 0; i < getChildCount(); i++) {
            TextView child = (TextView) getChildAt(i);
            if(x + child.getMeasuredWidth() + margin > screenWidth){
                x = margin;
                y += child.getMeasuredHeight() + margin;
            }
            child.layout((int)x,(int)y,(int)(x+child.getMeasuredWidth()),(int) (y+child.getMeasuredHeight()));
            x += child.getMeasuredWidth() + margin;
        }
    }
}
