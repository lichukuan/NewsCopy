package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;

public class ChangePasFragment extends Fragment {

    ImageView back;
    EditText oldPas;
    EditText newPas;
    Button ok;
    MyActivity myActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_change_pas,container,false);
        back = view.findViewById(R.id.back);
        oldPas = view.findViewById(R.id.old_pas);
        newPas = view.findViewById(R.id.new_pas);
        ok = view.findViewById(R.id.change_pas);
        back.setOnClickListener(v -> myActivity.back());
        myActivity = (MyActivity) getActivity();
        ok.setOnClickListener(v->{
            myActivity.changeUserPas(Users.userId,oldPas.getText().toString(),newPas.getText().toString()
            ,createSalt()).observe(ChangePasFragment.this, baseResult -> {
                if (baseResult.getCode() == Config.SUCCESS){
                    myActivity.finish();
                }else
                Toast.makeText(myActivity,baseResult.getResult(), Toast.LENGTH_SHORT).show();
            });
        });
        return view;
    }

    private String createSalt(){
        return "";
    }
}
