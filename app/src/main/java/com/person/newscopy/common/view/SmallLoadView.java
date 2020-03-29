package com.person.newscopy.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;

public class SmallLoadView extends android.support.v7.widget.AppCompatImageView {

    private Paint paint;
    private String text = "正在加载";
    private float percent;
    private Handler handler = new Handler();
    private boolean isStop = false;
    LinearGradient gradient;
    Matrix matrix = new Matrix();
    Rect bounds;
    public SmallLoadView(Context context) {
        this(context,null);
    }
    public SmallLoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SmallLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private void initPaint() {
        paint = new Paint();
        paint.setTextSize(40);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        gradient.setLocalMatrix(matrix);
        paint.setShader(gradient);
        canvas.drawText(text, getWidth()/2 - bounds.width()/2,getHeight()/2+bounds.height()/2, paint);
        if (!isStop)
            handler.postDelayed(runnable,100);
    }

    Runnable runnable = () -> {
        if(percent>=1.0){
            percent = 0;
        }else{
            percent+=0.1f;
        }
        matrix.setTranslate(getWidth()*percent - 60,0);
        postInvalidate();
    };

    public void cancel(){
        isStop = true;
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        gradient = new LinearGradient(0,0,bounds.width(),0,new int[]{Color.GRAY,Color.WHITE,Color.GRAY},null, Shader.TileMode.CLAMP);
    }


}
