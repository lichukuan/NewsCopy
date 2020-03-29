package com.person.newscopy.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class DoubleClickListenerLinearLayout extends LinearLayout {

    GestureDetector detector = null;
    DoubleClickListenerImpl impl = null;
    OnDoubleClickListener doubleClickListener = null;

    public DoubleClickListenerLinearLayout(Context context) {
        super(context);
        init();
    }

    public DoubleClickListenerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleClickListenerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDoubleClickListener(OnDoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    private void init(){
        impl = new DoubleClickListenerImpl();
        detector = new GestureDetector(getContext(),new MyGestureListener());
        detector.setOnDoubleTapListener(impl);
    }

    public interface OnDoubleClickListener{
        void onDoubleClick();
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

      class DoubleClickListenerImpl implements GestureDetector.OnDoubleTapListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (doubleClickListener != null)
                doubleClickListener.onDoubleClick();
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(detector.onTouchEvent(event)){//用 GestureDetector 的事件代替
            return true;
        }
        return super.onTouchEvent(event);
    }
}
