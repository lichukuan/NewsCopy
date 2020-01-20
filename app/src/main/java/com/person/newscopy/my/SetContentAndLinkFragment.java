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
import android.widget.Toast;
import com.person.newscopy.R;

public class SetContentAndLinkFragment extends DialogFragment {

    EditText content;
    EditText link;
    Button ok;
    Button cancel;
    MyActivity activity;
    private OnOkListener listener;

    public void setListener(OnOkListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.take_link_pop_view,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉对话框的标题
        activity = (MyActivity) getActivity();
        content = view.findViewById(R.id.tag);
        link = view.findViewById(R.id.link);
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cancel.setOnClickListener(v -> {
            dismiss();
            listener = null;
        });
        ok.setOnClickListener(v -> {
            String tag = content.getText().toString();
            if (tag.equals("")) {
                Toast.makeText(getContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            String l = link.getText().toString();
            if (l.equals("")){
                Toast.makeText(getContext(), "链接不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (listener != null)listener.ok(tag,l);
            dismiss();
        });
    }

    public interface OnOkListener{
        void ok(String tag,String l);
    }
}
