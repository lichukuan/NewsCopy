package com.person.newscopy.common;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.easy.generaltool.common.ScreenFitUtil;


public class RedCircleImageView extends android.support.v7.widget.AppCompatImageView {

    private int unSelectedIcon = -1;

    private int selectedIcon = -1;

    private boolean isShowNumber = false;//是否显示文字

    private int nowIcon = -1;

    private int number = -1;

    private float d = -1f;

    private int width;

    private int height;

    private int radius;

    private OnStateChangeListener onStateChangeListener;

    private Paint textPaint;

    private Path  circlePath;

    private Paint circlePaint;

    private boolean isFixed = false;//是否固定当前的ui

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public RedCircleImageView(Context context) {
        super(context);
        init();
    }

    public RedCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    private void init(){
       d = ScreenFitUtil.getDensity();
       textPaint = new Paint();
       textPaint.setStrokeWidth(4*d);
       textPaint.setTextSize(10*d);
       textPaint.setTextAlign(Paint.Align.CENTER);
       textPaint.setColor(Color.WHITE);
       circlePaint = new Paint();
       circlePaint.setAntiAlias(true);
       circlePath = new Path();
       circlePaint.setColor(Color.RED);
       circlePaint.setStyle(Paint.Style.FILL);
    }

    public void setIcon(int selectedIcon,int unSelectedIcon,int defaultIcon){
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.nowIcon = defaultIcon;
        setImageBitmap(BitmapFactory.decodeResource(getResources(),nowIcon));
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = height / 4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isFixed){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if (nowIcon == selectedIcon){
                        nowIcon = unSelectedIcon;
                        changeNumber(-1);
                    }
                    else {
                        nowIcon = selectedIcon;
                        changeNumber(1);
                    }
                    setImageBitmap(BitmapFactory.decodeResource(getResources(),nowIcon));
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setIcon(int selectedIcon, int unSelectedIcon){
       setIcon(selectedIcon,unSelectedIcon,unSelectedIcon);
    }

    public void setNumber(int number) {
        if (isFixed)return;
        this.number = number;
        invalidate();
    }

    public void setShowNumber(boolean showNumber) {
        isShowNumber = showNumber;
        invalidate();
    }

    private void changeNumber(int plus){
        number+=plus;
        if (onStateChangeListener!=null)onStateChangeListener.change(plus);
    }

    public void changeIcon(){
        if (isFixed)return;
        if (nowIcon == unSelectedIcon)nowIcon = selectedIcon;
        else nowIcon = unSelectedIcon;
        setImageBitmap(BitmapFactory.decodeResource(getResources(),nowIcon));
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        if (isFixed)return;
        if (isShowNumber){
            String text = "";
            if (number >0 && number < 99)
                text = number+"";
            else if (number > 99)
                text = "99+";
            if (!"".equals(text)){
                Rect rect = new Rect();
                textPaint.getTextBounds(text, 0, text.length(), rect);
                final int w = rect.width();
                final int h = rect.height();
                if (w < radius*2){
                    canvas.drawCircle(width-radius,radius,radius,circlePaint);
                    canvas.drawText(text,width-radius,radius+h/2,textPaint);
                }else {
                    canvas.drawRoundRect(new RectF(width - w - radius,0,width,radius*2),radius,radius,circlePaint);
                    canvas.drawText(text,width-(w + radius)/2,radius+h/2,textPaint);
                }
            }
        }
    }

    public interface OnStateChangeListener{
        void change(int plus);
    }
}
