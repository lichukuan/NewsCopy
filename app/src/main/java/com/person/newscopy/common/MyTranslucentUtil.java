package com.person.newscopy.common;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MyTranslucentUtil {
    private MyTranslucentUtil() {
    }

    //利用反射获取状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void fitsSystemWindows(Activity activity) {
        ViewGroup contentFrameLayout = (ViewGroup) activity.findViewById(android.R.id.content);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null ) {
            //布局预留状态栏高度的 padding
            parentView.setFitsSystemWindows(true);
            if (parentView instanceof DrawerLayout) {
                DrawerLayout drawer = (DrawerLayout) parentView;
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                drawer.setClipToPadding(false);
            }
        }
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                //attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    public static void setTranslucent(Activity activity,int color,int statusBarHeight){
        setTranslucent(activity,false,-1,statusBarHeight);
    }

    /**
     *设置沉浸式状态栏
     * @param activity
     * @param isDrawerLayout 是否有侧滑
     * @param drawerContent 当有侧滑时，主内容的id
     */
    public static void setTranslucent(Activity activity,boolean isDrawerLayout,int drawerContent,int statusBarHeight){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            fitsSystemWindows(activity);
        }
        fullScreen(activity);
        addStatus(activity,isDrawerLayout,drawerContent,statusBarHeight);
    }

    private static void addStatus(Activity activity,boolean isDrawerLayout,int drawerContent,int statusBarHeight){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setColors(new int[]{Color.parseColor("#2087ff"),Color.parseColor("#2499ff")});
        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        if (isDrawerLayout) {
            //要在内容布局增加状态栏，否则会盖在侧滑菜单上
            ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
            //DrawerLayout 则需要在第一个子视图即内容试图中添加padding
            View parentView = rootView.getChildAt(0);
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarHeight);

            statusBarView.setBackground(gradientDrawable);
            //添加占位状态栏到线性布局中
            linearLayout.addView(statusBarView, lp);
            //侧滑菜单
            DrawerLayout drawer = (DrawerLayout) parentView;
            //内容视图
            View content = activity.findViewById(drawerContent);
            //将内容视图从 DrawerLayout 中移除
            drawer.removeView(content);
            //添加内容视图
            linearLayout.addView(content, content.getLayoutParams());
            //将带有占位状态栏的新的内容视图设置给 DrawerLayout
            drawer.addView(linearLayout, 0);
        } else {
            //设置 paddingTop
            ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
            rootView.setPadding(0, statusBarHeight, 0, 0);
                //增加占位状态栏
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                View statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        statusBarHeight);
                statusBarView.setBackground(gradientDrawable);
                decorView.addView(statusBarView, lp);
        }
    }
}
