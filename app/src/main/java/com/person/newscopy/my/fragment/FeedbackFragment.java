package com.person.newscopy.my.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.MyTranslucentUtil;
import com.person.newscopy.my.MyActivity;

public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback,container,false);
        MyActivity myActivity = (MyActivity) getActivity();
        MyTranslucentUtil.setTranslucent(myActivity, Color.parseColor("#fecc11"), (int) (25* ScreenFitUtil.getDensity()));
        view.findViewById(R.id.back).setOnClickListener(v -> myActivity.back());
        view.findViewById(R.id.commit).setOnClickListener(v -> {
            Toast.makeText(myActivity, "提交成功", Toast.LENGTH_SHORT).show();
            myActivity.back();
        });
        return view;
    }
}
