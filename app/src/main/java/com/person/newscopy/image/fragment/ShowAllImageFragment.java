package com.person.newscopy.image.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.person.newscopy.R;
import com.person.newscopy.image.adapter.ShowAllImageFragmentAdapter;

import java.util.List;

public class ShowAllImageFragment extends Fragment {

    ViewPager pager;
    ShowAllImageFragmentAdapter allImageFragmentAdapter;
    List<String> data;
    int nowIndex = 0;
    TextView index;
    TextView save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_show_all,container,false);
        pager = view.findViewById(R.id.pager);
        index = view.findViewById(R.id.index);
        save = view.findViewById(R.id.save);
        return view;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void setNowIndex(int nowIndex) {
        this.nowIndex = nowIndex;
    }

    @Override
    public void onResume() {
        super.onResume();
        allImageFragmentAdapter = new ShowAllImageFragmentAdapter(getChildFragmentManager(),data);
        pager.setAdapter(allImageFragmentAdapter);
        index.setText((nowIndex+1)+"/"+data.size());
        save.setOnClickListener(v -> {
            allImageFragmentAdapter.save(nowIndex);
        });
        pager.setCurrentItem(nowIndex);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                 nowIndex = i;
                 index.setText((nowIndex+1)+"/"+data.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

}
