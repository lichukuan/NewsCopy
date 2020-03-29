package com.person.newscopy.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

public class LinearGradientView extends View {

    Paint paint;
    String startColor;
    String endColor;

    public LinearGradientView(Context context,String startColor,String endColor) {
        super(context);
        this.startColor = startColor;
        this.endColor = endColor;
        init();
    }

    private void init(){
//        paint=new Paint();
//       //两个坐标形成变量，规定了渐变的方向和间距大小，着色器为镜像
//        LinearGradient linearGradient =new LinearGradient(,Shader.TileMode.MIRROR);
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        Shape shape = new
//        paint.setShader(linearGradient);
//        paint.setStrokeWidth(50);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
