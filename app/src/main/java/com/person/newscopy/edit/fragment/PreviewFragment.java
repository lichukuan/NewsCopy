package com.person.newscopy.edit.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.edit.EditActivity;
import com.person.newscopy.user.Users;
import com.zzhoujay.richtext.RichText;

public class PreviewFragment extends Fragment {

    FloatingActionButton edit;
    String html;
    ShapeImageView icon;
    TextView name;
    TextView detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_preview,container,false);
        RichText.initCacheDir(getContext());
        edit = view.findViewById(R.id.edit);
        name = view.findViewById(R.id.name);
        icon = view.findViewById(R.id.icon);
        edit.setOnClickListener(v -> {
            ((EditActivity)getActivity()).edit();
        });
        detail = view.findViewById(R.id.detail);
        name.setText(Users.userName);
        Glide.with(this)
                .asBitmap()
                .load(Users.userIcon)
                .into(icon);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        html = html.substring(0,html.indexOf("<json>"));
        RichText.fromHtml(html.replaceAll("&nbsp"," "))
                .bind(this)
                .into(detail);
    }

    public void setHtml(String html){
         this.html = html;
    }
}
