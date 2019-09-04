package com.person.newscopy.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;

public class LoadView extends android.support.v7.widget.AppCompatImageView {

    private Paint colorPaint;

    private Path path;

    float d;

    RectF rectF;

    float height;

    public LoadView(Context context) {
        super(context);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public LoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setP(int p){
        path.reset();
        path.moveTo(d*p,0);
        path.lineTo(d*(p+30),0);
        path.lineTo(d*(p+50),height);
        path.lineTo(d*(p+20),height);
        path.lineTo(d*p,0);
        invalidate();
    }

    private void init(){
        d = ViewUtil.FitScreen.getDensity();
        height = ViewUtil.ScreenInfo.getScreenHeight(this);
       colorPaint = new Paint();
       path = new Path();
       colorPaint.setColor(Color.WHITE);
       colorPaint.setAlpha(150);
       colorPaint.setStyle(Paint.Style.FILL);
       path.moveTo(d*20,0);
       path.lineTo(d*30,0);
       path.lineTo(d*40,100*d);
       path.lineTo(d*30,100*d);
       path.lineTo(d*20,0);
       //LinearGradient linearGradient = new LinearGradient()
       rectF = new RectF(0,0,100*d,100*d);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,colorPaint);
    }
}
