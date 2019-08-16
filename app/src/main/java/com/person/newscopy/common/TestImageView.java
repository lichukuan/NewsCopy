package com.person.newscopy.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;

public class TestImageView extends android.support.v7.widget.AppCompatImageView {

    private int topRightRadius;

    private int topLeftRadius;

    private int bottomLeftRadius;

    private int bottomRightRadius;

    private Path path;

    private Paint paint;

    private float density;

    private int width;

    private int height;

    private Paint linePaint;

    public TestImageView(Context context) {
        super(context);
    }

    public TestImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }


    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        //paint.setStrokeWidth(10);
        //paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        topLeftRadius = 40;
        topRightRadius = 40;
        bottomLeftRadius = 40;
        bottomRightRadius = 40;
        density = ViewUtil.FitScreen.getDensity();
        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0,0,width,0,linePaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.moveTo(0,0);
            path.arcTo(0,0,topLeftRadius*density*2,topLeftRadius*density*2,180,90,true);
            //path.moveTo(topLeftRadius*density,0);
            path.lineTo(width-topRightRadius*density,0);
            path.arcTo(width-topRightRadius*density*2,0,width,topRightRadius*density*2, 0,-90,true);
            path.lineTo(0,topLeftRadius*density);
            //canvas.drawPath(path,paint);
            //path.reset();
            path.moveTo(width,height-bottomRightRadius*density);
            //path.lineTo(width,height-bottomRightRadius*density);
            path.arcTo(width-bottomRightRadius*density*2,height-bottomRightRadius*density*2,width,height,0,90,true);
//            path.moveTo(width-bottomRightRadius*density,height);
            path.lineTo(bottomLeftRadius*density,height);
            path.arcTo(0,height-bottomLeftRadius*density*2,bottomLeftRadius*density*2,height,90,90,true);
//            path.moveTo(0,height-bottomLeftRadius*density);
           path.lineTo(width,height-bottomRightRadius*density);
           path.addRect(0,topLeftRadius*density,width,height-bottomRightRadius*density, Path.Direction.CCW);
           canvas.drawPath(path,paint);
           //canvas.drawRect(0,topLeftRadius*density,width,height-bottomRightRadius*density,paint);
        }

    }
}
