package com.person.newscopy.camera.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.camera.adapter.ListVideoAdapter;
import com.person.newscopy.camera.adapter.ListVideoGroupAdapter;
import com.person.newscopy.camera.bean.VideoBean;
import com.person.newscopy.camera.bean.VideoGroupBean;
import com.person.newscopy.image.adapter.ListImageGroupAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class VideoListFragment extends Fragment {

    List<VideoGroupBean> group;
    RecyclerView recycler;
    FrameLayout parent;
    LinearLayout flag;
    TextView cancel;
    Button selector;
    String groupIndex;
    View other;
    LinearLayout toolbar;

    HashMap<String,List<VideoBean>> res;
    Handler handler = new Handler(msg -> {
        if (msg.what == 1){
            initData();
        }
        return false;
    });

    private void initData(){
        if (group.size() == 0){
            Log.d("====","group size = "+group.size()+"  res size  = "+res.size());
            return;
        }
        String groupId = group.get(0).getGroupId();
        String groupName = group.get(0).getGroupName();
        listVideoAdapter.setData(res.get(groupId));
        selector.setText(groupName);
    }

    private ListVideoAdapter listVideoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pick_video,container,false);
        TranslucentUtil.setTranslucent(getActivity(),Color.BLACK, (int) (20* ScreenFitUtil.getDensity()));
        recycler = view.findViewById(R.id.recycler);
        parent = view.findViewById(R.id.parent);
        flag = view.findViewById(R.id.flag);
        cancel = view.findViewById(R.id.cancel);
        selector = view.findViewById(R.id.select);
        toolbar = view.findViewById(R.id.toolbar);
        other = view.findViewById(R.id.other);
        group = new ArrayList<>();
        if (listVideoAdapter == null)
            listVideoAdapter = new ListVideoAdapter(this);
        new Thread(()->{
            res = readAllImageDirectory();
            handler.sendEmptyMessage(1);
        }).start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recycler.setAdapter(listVideoAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        selector.setOnClickListener(v -> {
           createPop();
        });
    }

    public void selected(String groupId){
        groupIndex = groupId;
        List<VideoBean> l = res.get(groupId);
        if (l == null)return;
        selector.setText(l.get(0).getGroupName());
        listVideoAdapter.setData(res.get(groupId));
        popupWindow.dismiss();
    }

    private PopupWindow popupWindow;
    private ListVideoGroupAdapter listImageGroupAdapter = null;

    private void createPop(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_pick_images,null);
        RecyclerView recyclerView1 = view.findViewById(R.id.recycler);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        if (listImageGroupAdapter == null)
            listImageGroupAdapter = new ListVideoGroupAdapter(this,group);
        if (group.size() == 0)
            group = listImageGroupAdapter.getData();
        recyclerView1.setAdapter(listImageGroupAdapter);
        Log.d("=====","group size = "+group.size());
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, (int) ( 500* ScreenFitUtil.getDensity()));
        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationStyle);
        //菜单背景色
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(() ->{
            other.setVisibility(View.GONE);
        });
        other.setVisibility(View.VISIBLE);
        popupWindow.showAsDropDown(toolbar);
    }

    private HashMap<String,List<VideoBean>> readAllImageDirectory(){
        String[] columns = {MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.BUCKET_ID,
                MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,MediaStore.Video.VideoColumns.DURATION,MediaStore.MediaColumns.MIME_TYPE,MediaStore.MediaColumns.SIZE
        };
        String sortOrder = MediaStore.Video.VideoColumns.DATE_ADDED+" desc";
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, sortOrder);
        LinkedHashMap<String,List<VideoBean>> map = new LinkedHashMap<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));
            String path =  cursor.getString(cursor.getColumnIndex( MediaStore.Video.VideoColumns.DATA));
            String groupId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.BUCKET_ID));
            String groupName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME));
            String type = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
            String size = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
            String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
            VideoBean imageBean = new
                    VideoBean(id,path,groupId,groupName,duration,type,size);
            if (map.containsKey(groupId)){
                map.get(groupId).add(imageBean);
            }else {
                VideoGroupBean imageGroupBean = new VideoGroupBean(groupId,groupName,path);
                group.add(imageGroupBean);
                List<VideoBean> l = new ArrayList<>();
                l.add(imageBean);
                map.put(groupId,l);
            }
        }
        cursor.close();
        return map;
    }
}
