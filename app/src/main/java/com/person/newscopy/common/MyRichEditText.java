package com.person.newscopy.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.BaseMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;
import android.widget.Toast;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyRichEditText extends android.support.v7.widget.AppCompatEditText{

    public static final int MAX_WORD_COUNT = 500;

    private List<String> images = new ArrayList<>();

    public static final int MAX_INSERT_ARTICLE_IMAGE_COUNT = 5;

    public static final String IMAGE_FLAG = "※";

    private float density = ScreenFitUtil.getDensity();

    private Context context;

    public MyRichEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyRichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyRichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        setMovementMethod(new CopyMovementMethod());
    }

    private int getInsertImageCount(){
        MyImageSpan[] words = getText().getSpans(0, getText().length(), MyImageSpan.class);
        return words.length;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath,int degree) {
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
        if (degree == 0)
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) reqWidth, (int) (reqWidth/width*height), false);
        else {
            //旋转图片 动作
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            // 创建新的图片
            bitmap = Bitmap.createBitmap(bitmap, (int) (10*density),0, (int) (bitmap.getWidth()-10*density), bitmap.getHeight(), matrix, true);
        }
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

    public String getHtml() {
        StringBuilder html = new StringBuilder();
        final Editable editable = getText();
        MyImageSpan[] words = editable.getSpans(0, getText().length(), MyImageSpan.class);
        Arrays.sort(words, (arg0, arg1) -> {
            int index0 = editable.getSpanStart(arg0);
            int index1 = editable.getSpanStart(arg1);
            return Integer.compare(index0, index1);
        });
        MyURLSpan[] urlSpans = editable.getSpans(0,getText().length(),MyURLSpan.class);
        Arrays.sort(urlSpans,(a,b)->{
            int index0 = editable.getSpanStart(a);
            int index1 = editable.getSpanStart(b);
            return Integer.compare(index0, index1);
        });
        html.append("<html>");
        html.append(getText().toString());
        for (MyImageSpan word : words) {
            int start = html.indexOf(IMAGE_FLAG);
            int end = start+1;
            images.add(word.url);
            html.replace(start,end,"<img src = \""+word.url+"\" />");
        }
        for (MyURLSpan urlSpan : urlSpans) {
            int start = html.indexOf(urlSpan.tag);
            int end = start+urlSpan.tag.length();
            html.replace(start,end,createHtmlLink(urlSpan.getURL(),urlSpan.tag));
        }
        html.append("</html>");
        return html.toString().replace("\n","</br>");
    }

    private String createHtmlLink(String url,String content){
        return "<a href = " + "\"" + url + "\">" + content + "</a>";
    }

    public void insertImage(String path,String url){
      if (getInsertImageCount()>=MAX_INSERT_ARTICLE_IMAGE_COUNT){
          Toast.makeText(context, "最多插入五张图片", Toast.LENGTH_SHORT).show();
          return;
      }
      int degree = readPictureDegree(path);
      insertBitmap(url,getSmallBitmap(path,degree));
    }
    public void insertUrl(String content,String url){
        Editable edit_text = getEditableText();
        int index = getSelectionStart(); // 获取光标所在位置
        if (index <0){
            Toast.makeText(context, "请先选择插入位置", Toast.LENGTH_SHORT).show();
            return;
        }
        //edit_text.insert(index, newLine);//插入图片前换行
        SpannableString spannableString = new SpannableString(content);
        MyURLSpan urlSpan = new MyURLSpan(url,content);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(urlSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
    }

    public List<String> getImages() {
        return images;
    }

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 插入图片
     * @param bitmap
     * @param path
     * @return
     */
    private void insertBitmap(String path, Bitmap bitmap) {
        if (getSelectionStart() < 0){
            Toast.makeText(context, "请先选择插入位置", Toast.LENGTH_SHORT).show();
            return;
        }
        Editable edit_text = getEditableText();
        //插入换行符，使图片单独占一行
        SpannableString newLine = new SpannableString("\n");
        edit_text.insert(getSelectionStart(), newLine);//插入图片前换行
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        SpannableString spannableString = new SpannableString(IMAGE_FLAG);
        // 根据Bitmap对象创建ImageSpan对象
        MyImageSpan imageSpan = new MyImageSpan(context, bitmap,ImageSpan.ALIGN_BASELINE,path);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int index = getSelectionStart(); // 获取光标所在位置
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.insert(getSelectionStart(), newLine);//插入图片后换行
        setHeight((int) (getHeight()+bitmap.getHeight()+200*density));
    }

    class MyImageSpan extends ImageSpan{
        String url;

        MyImageSpan(Context context, Bitmap bitmap, int verticalAlignment, String url) {
            super(context, bitmap, verticalAlignment);
            this.url = url;
        }
    }

    class MyURLSpan extends URLSpan{
        String tag;
        public MyURLSpan(String url,String tag) {
            super(url);
            this.tag = tag;
        }
    }

    class CopyMovementMethod extends BaseMovementMethod{
        @Override
        protected boolean down(TextView widget, Spannable buffer) {
            Log.d("======","down执行");
            ClickableSpan[] clickableSpans = buffer.getSpans(0,buffer.length(),ClickableSpan.class);
            if (clickableSpans.length == 1){
                ClickableSpan span = clickableSpans[0];
                if (span instanceof TextLinks.TextLinkSpan){
                    copy(((TextLinks.TextLinkSpan)span).getTextLink().toString());
                    span.onClick(widget);
                }
            }
            return super.down(widget, buffer);
        }
    }

    private void copy(String text){
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("rich", text);
      //把数据放置到剪切板
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }
}
