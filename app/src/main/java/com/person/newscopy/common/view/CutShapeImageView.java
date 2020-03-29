package com.person.newscopy.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;

public class CutShapeImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint paint;
    private Paint bitmapPaint;
    private Bitmap beforeBitmap;
    private float cutRadius;
    Path path = new Path();
    public static final int CIRCLE_SHAPE = 1;
    public static final int SQUARE_SHAPE = 2;
    private int shape = CIRCLE_SHAPE;

    public CutShapeImageView(Context context) {
        super(context);
        init();
    }

    public CutShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CutShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
       paint = new Paint();
       paint.setStyle(Paint.Style.FILL);
       paint.setColor(Color.BLACK);
       paint.setAlpha(150);
       bitmapPaint = new Paint();
       mScaleDetector = new ScaleGestureDetector(getContext(),new ScaleListener());

    }

    public void setShape(int shape) {
        this.shape = shape;
        invalidate();
    }

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1f;

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // 限制缩放的大小
            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 3.0f));
            //mScaleFactor = 2f;
            invalidate();
            return true;
        }
    }

    float x = 0;
    float y = 0;
    float offsetX = 0;
    float offsetY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1){
            mScaleDetector.onTouchEvent(event);
        }else {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newX = event.getX();
                    float newY = event.getY();
                    if (Math.abs(newX - x) >= Math.abs(newY - y)){
                        offsetX += newX - x;
                    }else {
                        offsetY -= newY - y;
                    }
                    if (offsetX > bitmapWidth/2 - cutRadius/mScaleFactor)offsetX = bitmapWidth/2 - cutRadius/mScaleFactor;
                    if (offsetX < cutRadius/mScaleFactor - bitmapWidth/2)offsetX = cutRadius/mScaleFactor - bitmapWidth/2;
                    if (offsetY > bitmapHeight/2- cutRadius/mScaleFactor)offsetY = bitmapHeight/2 - cutRadius/mScaleFactor;
                    if (offsetY < cutRadius/mScaleFactor - bitmapHeight/2)offsetY = cutRadius/mScaleFactor - bitmapHeight/2;
                    invalidate();
                    break;
            }
        }
        return true;
    }
    float bitmapWidth = -1;
    float bitmapHeight = -1;
    int centerX = -1;
    int centerY = -1;
    @Override
    protected void onDraw(Canvas canvas) {
        if(beforeBitmap != null){
            if (centerX == -1 && centerY == -1){
                centerX = getWidth()/2;
                centerY = getHeight()/2;
                cutRadius = Math.min(Math.min(bitmapHeight,bitmapWidth),Math.min(getWidth(),getHeight()))/2;
            }
            float left = centerX - bitmapWidth/2+offsetX;
            float top = centerY - bitmapHeight/2+offsetY;
            canvas.save();
            canvas.scale(mScaleFactor, mScaleFactor,centerX,centerY);
            canvas.drawBitmap(beforeBitmap,left,top,bitmapPaint);
            canvas.restore();
            canvas.drawRect(0,0,getWidth(),getHeight(),paint);
           if (shape == SQUARE_SHAPE)
                path.addRect(centerX - cutRadius,centerY - cutRadius,centerX+cutRadius,centerY+cutRadius, Path.Direction.CCW);
           else path.addCircle(centerX,centerY,cutRadius, Path.Direction.CCW);
            canvas.save();
            canvas.clipPath(path);
            canvas.scale(mScaleFactor, mScaleFactor,centerX,centerY);
            canvas.drawBitmap(beforeBitmap,left,top,bitmapPaint);
            canvas.restore();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (bm == null)return;
        beforeBitmap = bm;
        bitmapWidth = beforeBitmap.getWidth();
        bitmapHeight = beforeBitmap.getHeight();
    }

    public Bitmap getResult() {
        Bitmap result = Bitmap.createBitmap((int) (cutRadius*2),(int) (cutRadius*2), Bitmap.Config.ARGB_8888);
        float left = cutRadius - bitmapWidth/2+offsetX;
        float top = cutRadius - bitmapHeight/2+offsetY;
        Paint paint = new Paint();
        Path path = new Path();
        Canvas canvas = new Canvas(result);
        //相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        if (shape == SQUARE_SHAPE)
            path.addRect(0,0,2*cutRadius,2*cutRadius, Path.Direction.CCW);
        else path.addCircle(cutRadius,cutRadius,cutRadius, Path.Direction.CCW);
        canvas.save();
        canvas.clipPath(path);
        canvas.scale(mScaleFactor, mScaleFactor,cutRadius,cutRadius);
        canvas.drawBitmap(beforeBitmap,left,top,paint);
        canvas.restore();
        return result;
    }
}
