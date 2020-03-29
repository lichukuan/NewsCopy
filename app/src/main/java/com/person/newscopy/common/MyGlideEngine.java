package com.person.newscopy.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.impl.GlideEngine;

public class MyGlideEngine extends GlideEngine {

        @Override
        public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(placeholder)
                    .centerCrop()
                    .override(resize,resize);
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                             Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(placeholder)
                    .centerCrop()
                    .override(resize,resize);
            Glide.with(context)
                    .asBitmap()
                    .apply(requestOptions)
                    .load(uri)
                    .into(imageView);
        }

        @Override
        public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .override(resizeX,resizeY);
            Glide.with(context)
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            RequestOptions requestOptions = new RequestOptions()
                    .override(resizeX,resizeY)
                    .priority(Priority.HIGH);
            Glide.with(context)
                    .asGif()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public boolean supportAnimatedGif() {
            return true;
        }
}
