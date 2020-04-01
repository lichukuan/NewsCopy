package com.person.newscopy.edit.fragment;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.edit.EditActivity;
import com.person.newscopy.search.HistoryView;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ShowSensitiveWordsFragment extends DialogFragment {

    Button ok;
    HistoryView word;
    Activity activity;
    List<String> data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.show_sensitve_word_view,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉对话框的标题
        activity = getActivity();
        ok = view.findViewById(R.id.ok);
        word = view.findViewById(R.id.key_word);
        return view;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (data != null){
            for (int i = 0; i < data.size(); i++) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.history_text,null);
                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT,90);
                textView.setLayoutParams(layoutParams);
                textView.setText(data.get(i));
                word.addView(textView);
            }
        }
        ok.setOnClickListener(v -> dismiss());
    }

}
