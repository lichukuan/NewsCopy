package com.person.newscopy.edit;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.person.newscopy.R;

public class PopupWindowUtil {
    PopupWindow popupWindow;
    public void create(View view, int width, int height, boolean isOutsideTouchable, Activity activity,float alpha,View other){
        popupWindow = new PopupWindow(view, width,height);
        if (isOutsideTouchable){
            //动画效果
            popupWindow.setAnimationStyle(R.style.AnimationStyle);
            //菜单背景色
            ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
        }
        popupWindow.setOnDismissListener(() ->{
           other.setVisibility(View.GONE);
        });
        other.setVisibility(View.VISIBLE);
    }

    public void showAsDropDown(View view){
        if (popupWindow == null)return;
        popupWindow.showAsDropDown(view);
    }

    public void dismiss(){
        popupWindow.dismiss();
    }

    public void showAsDropDown(View view,int xoff, int yoff){
        if (popupWindow == null)return;
        popupWindow.showAsDropDown(view,xoff,yoff);
    }

    public void showAsDropDown(View view,int xoff, int yoff, int gravity ){
        if (popupWindow == null)return;
        popupWindow.showAsDropDown(view,xoff,yoff,gravity);
    }

    public void showAtLocation(View parent, int gravity, int x, int y ){
        if (popupWindow == null)return;
        popupWindow.showAtLocation(parent,gravity,x,y);
    }

    public void create(View view,int width,int height,Activity activity,View other){
         create(view,width,height,true,activity,0.5f,other);
    }


    private void backgroundAlpha(float bgAlpha,Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);

    }
}
