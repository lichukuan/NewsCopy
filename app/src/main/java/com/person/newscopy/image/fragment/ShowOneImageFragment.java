package com.person.newscopy.image.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.view.ScaleImageView;
import com.person.newscopy.image.ImageActivity;

public class ShowOneImageFragment extends Fragment {

    ScaleImageView show;
    String url = null;
    int sum = -1;
    int index = -1;
    Bitmap result = null;
    ProgressBar progressBar;
    ImageActivity imageActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_show,container,false);
        show = view.findViewById(R.id.show_image);
        imageActivity = (ImageActivity) getActivity();
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    public void setData(String url, int sum, int index){
        this.url = url;
        this.sum = sum;
        this.index = index;
    }

    RequestOptions requestOptions = new RequestOptions();

    @Override
    public void onResume() {
        super.onResume();
        TranslucentUtil.setTranslucent(imageActivity,Color.BLACK, (int) (ScreenFitUtil.getDensity()*25));
        Glide.with(this)
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        result = resource;
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(show);
        show.setOnClickListener(v -> {
            Toast.makeText(imageActivity, "退出", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        });
    }

    public void save(){
        if (result == null){
            Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
            return;
        }
        if(imageActivity.saveImageToGallery(result) != null) Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        result = null;
    }
}
