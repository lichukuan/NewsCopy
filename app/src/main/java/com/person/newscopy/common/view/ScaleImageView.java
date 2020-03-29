package com.person.newscopy.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

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
            invalidate();
            return false;
        }
    }

    private void init(){
        mScaleDetector = new ScaleGestureDetector(getContext(),new ScaleImageView.ScaleListener());
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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(mScaleFactor,mScaleFactor,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
        canvas.restore();
    }
}
