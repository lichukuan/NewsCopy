package com.person.newscopy.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.image.fragment.ShowAllImageFragment;

public class ScaleImageView extends android.support.v7.widget.AppCompatImageView {

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1f;

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // 限制缩放的大小
            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 3.0f));
            setScaleX(mScaleFactor);
            setScaleY(mScaleFactor);
            return true;
        }
    }

    private void init(){
        mScaleDetector = new ScaleGestureDetector(getContext(),new ScaleImageView.ScaleListener());
        screenHeight = ViewInfoUtil.ScreenInfo.getScreenHeight(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            mScaleDetector.onTouchEvent(event);
        }
        return true;
    }

    public ScaleImageView(Context context) {
        super(context);
        init();
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    float offsetX = 0;
    float offsetY = 0;
    float x = 0;
    float y = 0;
    float screenHeight = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (event.getPointerCount() == 1){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float offset_x = (event.getX() - x);
                        float offset_y = (event.getY() - y);
                        if (Math.abs(offset_x) >= Math.abs(offset_y))
                            offsetX += offset_x;
                        else offsetY += offset_y;
                        break;
                }
            }
        if (Math.abs(offsetX) > (mScaleFactor-1)*getDrawable().getBounds().width()/2){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else {
            float imageHeight = (mScaleFactor)*getDrawable().getBounds().height();
            if (imageHeight > screenHeight){
                if (offsetY < 0)
                    offsetY =  - Math.min(Math.abs(offsetY),(imageHeight - screenHeight)/2);
                else
                offsetY = Math.min(offsetY,(imageHeight - screenHeight)/2);
                setTranslationY(offsetY);
            }
            setTranslationX(offsetX);
        }
        return super.dispatchTouchEvent(event);
    }

}
