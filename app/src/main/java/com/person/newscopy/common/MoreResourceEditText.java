package com.person.newscopy.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;

import java.util.ArrayList;
import java.util.List;

public class MoreResourceEditText extends android.support.v7.widget.AppCompatEditText {

    private final String TAG = "PATEditorView";

    private Context context;

    private List<String> mContentList;

    private float density = ScreenFitUtil.getDensity();

    public int paddingTop;

    public int paddingBottom;

    public int mHeight;

    public int mLayoutHeight;

    private final int MOVE_SLOP = 20; //移动距离临界

    //滑动距离的最大边界
    private int mOffsetHeight;

    //是否到顶或者到底的标志
    private boolean mBottomFlag = false;

    private boolean isCanScroll = false;//标记内容是否触发了滚动

    private float lastY = 0;

    public static final String mBitmapTag = "☆";

    private String mNewLineTag = "\n";

    public MoreResourceEditText(Context context) {
        this(context,null);
    }

    public MoreResourceEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoreResourceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        mContentList = getContentList();
        insertData();
    }

    /**
     * 用集合的形式获取控件里的内容
     *
     * @return
     */
    public List<String> getContentList() {
        if (mContentList == null) {
            mContentList = new ArrayList<>();
        }
        String content = getText().toString().replaceAll(mNewLineTag, "");
        if (content.length() > 0 && content.contains(mBitmapTag)) {
            String[] split = content.split("☆");
            mContentList.clear();
            for (String str : split) {
                mContentList.add(str);
            }
        } else {
            mContentList.add(content);
        }
        return mContentList;
    }

    /**
     * 设置数据
     */
    private void insertData() {
        Log.d("=========","d = "+density);
        if (mContentList.size() > 0) {
            for (String str : mContentList) {
                if (str.indexOf(mBitmapTag) != -1) {//判断是否是图片地址
                    String path = str.replace(mBitmapTag, "");//还原地址字符串
                    Bitmap bitmap = getSmallBitmap(path);
                    //插入图片
                    insertBitmap(path, bitmap);
                } else {
                    //插入文字
                    SpannableString ss = new SpannableString(str);
                    append(ss);
                }
            }
        }
    }

//    // 根据路径获得图片并压缩，返回bitmap用于显示
//    public Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        int w_screen = dm.widthPixels;
//        int w_width = w_screen;
//        int b_width = bitmap.getWidth();
//        int b_height = bitmap.getHeight();
//        int w_height = w_width * b_height / b_width;
//        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (300*density), (int) (100*density), false);
//        return bitmap;
//    }
//
//
//    //计算图片的缩放值
//    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//        if (height > reqHeight || width > reqWidth) {
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//        return inSampleSize;
//    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        final int height = bitmap.getHeight();
        final int width = bitmap.getWidth();
        float reqWidth = ViewInfoUtil.ScreenInfo.getScreenWidth(getContext()) - 10*density;
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) reqWidth, (int) (reqWidth/width*height), false);
        return bitmap;
    }


    //计算图片的缩放值
    public int calculateInSampleSize(BitmapFactory.Options options) {
        final int width = options.outWidth;
        int inSampleSize = 1;
        float reqWidth = ViewInfoUtil.ScreenInfo.getScreenWidth(getContext()) - 10*density;
        if (width > reqWidth) inSampleSize = Math.round((float) width / reqWidth);
        return inSampleSize;
    }

    public void insertImage(String path,String url){
        insertBitmap(url,getSmallBitmap(path));
    }

    public void insertUrl(String content,String url){
        Editable edit_text = getEditableText();
        int index = getSelectionStart(); // 获取光标所在位置
        SpannableString newLine = new SpannableString("\n");
        //edit_text.insert(index, newLine);//插入图片前换行
        SpannableString spannableString = new SpannableString(content);
        URLSpan urlSpan = new URLSpan(url);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(urlSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.insert(index, newLine);
    }

    /**
     * 插入图片
     * @param bitmap
     * @param path
     * @return
     */
    private void insertBitmap(String path, Bitmap bitmap) {
        Editable edit_text = getEditableText();
        int index = getSelectionStart(); // 获取光标所在位置
        //插入换行符，使图片单独占一行
        SpannableString newLine = new SpannableString("\n");
        edit_text.insert(index, newLine);//插入图片前换行
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        path = "<img src = \""+path+"\" />";
        SpannableString spannableString = new SpannableString(path);
        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        edit_text.insert(getSelectionStart(), spannableString);
        edit_text.insert(getSelectionStart(), newLine);//插入图片后换行

    }

//    /**
//     * 插入图片
//     *
//     * @param path
//     */
//    public void insertBitmap(String path) {
//        Bitmap bitmap = getSmallBitmap(path, (int) (density*300), (int) (density*100));
//        insertBitmap(path, bitmap);
//    }
//
//    /**
//     * 插入图片
//     *
//     * @param bitmap
//     * @param path
//     * @return
//     */
//    private void insertBitmap(String path, Bitmap bitmap) {
//        Editable edit_text = getEditableText();
//        int index = getSelectionStart(); // 获取光标所在位置
//        //插入换行符，使图片单独占一行
//        SpannableString newLine = new SpannableString("\n");
//        edit_text.insert(index, newLine);//插入图片前换行
//        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
//        path = mBitmapTag + path + mBitmapTag;
//        SpannableString spannableString = new SpannableString(path);
//        // 根据Bitmap对象创建ImageSpan对象
//        ImageSpan imageSpan = new ImageSpan(context, bitmap);
//        // 用ImageSpan对象替换你指定的字符串
//        spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        // 将选择的图片追加到EditText中光标所在位置
//        if (index < 0 || index >= edit_text.length()) {
//            edit_text.append(spannableString);
//        } else {
//            edit_text.insert(index, spannableString);
//        }
//        edit_text.insert(index, newLine);//插入图片后换行
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        //如果是需要拦截，则再拦截，这个方法会在onScrollChanged方法之后再调用一次
        if (!mBottomFlag)
            getParent().requestDisallowInterceptTouchEvent(true);
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (lastY == 0) {
                lastY = event.getRawY();
            }
            //条件：手指move了一段距离，但是onScrollChanged函数未调用，说明文字无法滚动了，则将触摸处理权交还给ParentView
            if (Math.abs(lastY - event.getRawY()) > MOVE_SLOP) {
                if (!isCanScroll) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
        }
        return result;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得内容面板
        Layout mLayout = getLayout();
        mLayoutHeight = mLayout.getHeight();
        paddingTop = getTotalPaddingTop();
        paddingBottom = getTotalPaddingBottom();
        //获得控件的实际高度
        mHeight = getHeight();
        //计算滑动距离的边界(H_content - H_view = H_scroll)
        mOffsetHeight = mLayoutHeight + paddingTop + paddingBottom - mHeight;
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        isCanScroll = true;
        if (vert == mOffsetHeight || vert == 0) {
            //这里将处理权交还给父控件
            getParent().requestDisallowInterceptTouchEvent(false);
            mBottomFlag = true;
        }
    }

}

