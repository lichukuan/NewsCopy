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
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.ItemOperateReadAdapter;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.ReadContent;

import java.util.ArrayList;
import java.util.List;

public class LikeFragment extends Fragment {

    MyActivity myActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_like,container,false);
        myActivity = (MyActivity) getActivity();
        view.findViewById(R.id.back).setOnClickListener(v -> myActivity.finish());
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        myActivity.queryHistory(Users.userId).observe(this, result -> {
            List<ReadContent> r =  result.getResult();
            List<ReadContent> likeContent = new ArrayList<>(r.size());
            for (int i = 0; i < r.size(); i++) {
                ReadContent readContent = r.get(i);
                if (readContent.getIsLike() == 1)
                    likeContent.add(readContent);
            }
            recyclerView.setAdapter(new ItemOperateReadAdapter(likeContent,LikeFragment.this));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(myActivity));
        return view;
    }
}
