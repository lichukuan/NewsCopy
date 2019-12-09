package com.person.newscopy.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.user.Users;

public class ChangeRecommendDialogFragment extends DialogFragment {

    EditText editContent;
    Button ok;
    Button cancel;
    MyActivity activity;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pop_my_change_recommend_view,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉对话框的标题
        activity = (MyActivity) getActivity();
        editContent = view.findViewById(R.id.edit_recommend);
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        cancel.setOnClickListener(v -> dismiss());
        ok.setOnClickListener(v -> {
            ok.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
            String newName = editContent.getText().toString();
            activity.updateUserRecommend(Users.userId,newName).observe(this,baseResult -> {
                if (baseResult.getCode() == Config.FAIL)
                    Toast.makeText(activity, "更新失败", Toast.LENGTH_SHORT).show();
                else {
                    Users.userRecommend = newName;
                    dismiss();
                }
                progressBar.setVisibility(View.GONE);
                ok.setClickable(true);
            });
        });
    }
}
