package com.person.newscopy.common.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.VideoView;

public class ShortVideoView extends VideoView {
    public ShortVideoView(Context context) {
        super(context);
    }

    public ShortVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShortVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShortVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    public void setVideoSize(int videoWidth, int videoHeight) {
        ViewGroup.LayoutParams layoutParams  =getLayoutParams();
        layoutParams.width = videoWidth;
        layoutParams.height = videoHeight;
        setLayoutParams(layoutParams);
    }

}
