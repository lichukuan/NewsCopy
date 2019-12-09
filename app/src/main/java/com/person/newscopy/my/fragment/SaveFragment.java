package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.ItemOperateReadAdapter;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.ReadContent;

import java.util.ArrayList;
import java.util.List;

public class SaveFragment extends Fragment {

    MyActivity myActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_save,container,false);
        myActivity = (MyActivity) getActivity();
        view.findViewById(R.id.back).setOnClickListener(v -> myActivity.finish());
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        myActivity.queryHistory(Users.userId).observe(this, result -> {
            List<ReadContent> r =  result.getResult();
            List<ReadContent> saveContent = new ArrayList<>(r.size());
            for (int i = 0; i < r.size(); i++) {
                ReadContent readContent = r.get(i);
                if (readContent.getIsSave() == 1)
                    saveContent.add(readContent);
            }
            recyclerView.setAdapter(new ItemOperateReadAdapter(saveContent,SaveFragment.this));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(myActivity));
        return view;
    }
}
