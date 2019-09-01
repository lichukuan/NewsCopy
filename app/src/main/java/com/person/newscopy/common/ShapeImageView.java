package com.person.newscopy.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;

public class ShapeImageView extends android.support.v7.widget.AppCompatImageView {

    public static final int SHAPE_CIRCLE = 1;

    public static final int SHAPE_RECTANGLE = 0;

    private int shape;

    private int radius;

    private int topRightRadius;

    private int topLeftRadius;

    private int bottomLeftRadius;

    private int bottomRightRadius;

    private Path path;

    private Paint paint;

    private float density;

    private int width;

    private int height;

    private Matrix matrix;

    public ShapeImageView(Context context) {
        super(context);
        init();
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.easy);//ImageSelectView是自定义属性中 name 设置的值
        //通过name_<属性名>来获取对应的属性值，第二个参数是默认值
        radius= (int) typedArray.getDimension(R.styleable.easy_radius,0);
        topLeftRadius= (int) typedArray.getDimension(R.styleable.easy_topLeftRadius,0);
        topRightRadius= (int) typedArray.getDimension(R.styleable.easy_topRightRadius,0);
        bottomLeftRadius= (int) typedArray.getDimension(R.styleable.easy_bottomLeftRadius,0);
        bottomRightRadius= (int) typedArray.getDimension(R.styleable.easy_bottomRightRadius,0);
        shape=typedArray.getInt(R.styleable.easy_shape,SHAPE_CIRCLE);
        typedArray.recycle();//使用完之后一定要记得回收
        init();
    }

    private void init(){
        paint = new Paint();
        path = new Path();
        matrix = new Matrix();
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        radius = Math.min(width, height) / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }
        if(topLeftRadius==0&&topRightRadius==0&&bottomLeftRadius==0&&bottomRightRadius==0||shape==1){//绘制圆形
            if (drawable instanceof BitmapDrawable) {
                paint.setShader(initBitmapShader((BitmapDrawable) drawable));//将着色器设置给画笔
                canvas.drawCircle(width / 2, height / 2, radius, paint);//使用画笔在画布上画圆
                return;
            }
        }else if(shape==0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                path.moveTo(0,0);
                path.arcTo(0,0,topLeftRadius *2,topLeftRadius *2,180,90,true);
                //path.moveTo(topLeftRadius ,0);
                path.lineTo(width-topRightRadius ,0);
                path.arcTo(width-topRightRadius *2,0,width,topRightRadius *2, 0,-90,true);
                path.lineTo(0,topLeftRadius );
                //canvas.drawPath(path,paint);
                //path.reset();
                path.moveTo(width,height-bottomRightRadius );
                //path.lineTo(width,height-bottomRightRadius );
                path.arcTo(width-bottomRightRadius *2,height-bottomRightRadius *2,width,height,0,90,true);
//            path.moveTo(width-bottomRightRadius ,height);
                path.lineTo(bottomLeftRadius ,height);
                path.arcTo(0,height-bottomLeftRadius *2,bottomLeftRadius *2,height,90,90,true);
//            path.moveTo(0,height-bottomLeftRadius );
                path.lineTo(width,height-bottomRightRadius );
                path.addRect(0,topLeftRadius ,width,height-bottomRightRadius , Path.Direction.CCW);
                if (drawable instanceof BitmapDrawable) {
                    paint.setShader(initBitmapShader((BitmapDrawable) drawable));//将着色器设置给画笔
                    canvas.drawPath(path,paint);//使用画笔在画布上画圆
                    return;
                }
            }
        }
        super.onDraw(canvas);

    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        matrix.setScale(scale, scale);//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }

}
