package com.person.newscopy.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RichView extends FrameLayout {
    public RichView(@NonNull Context context) {
        super(context);
    }

    public RichView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RichView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }
}
