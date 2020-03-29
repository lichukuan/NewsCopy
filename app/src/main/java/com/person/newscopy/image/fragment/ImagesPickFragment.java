package com.person.newscopy.image.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.edit.PopupWindowUtil;
import com.person.newscopy.image.adapter.ListImageAdapter;
import com.person.newscopy.image.adapter.ListImageGroupAdapter;
import com.person.newscopy.image.bean.ImageBean;
import com.person.newscopy.image.bean.ImageGroupBean;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ImagesPickFragment extends Fragment {

    RecyclerView recyclerView;
    TextView cancel;
    TextView preview;
    Button selector;
    Button ok;
    ImagesPickFragmentHandler handler;
    ListImageAdapter listImageAdapter;
    List<List<ImageBean>> res;
    List<ImageGroupBean> group;
    View other;

    private static class ImagesPickFragmentHandler extends Handler {

        WeakReference<ImagesPickFragment> reference;

        ImagesPickFragmentHandler(ImagesPickFragment fragment){
            reference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){//完成
                ImagesPickFragment imagesPickFragment = reference.get();
                if (imagesPickFragment!=null)imagesPickFragment.initData();
            }
        }
    }

    public void selected(String groupId){
        int index = -1;
        for (int i = 0; i <group.size() ; i++) {
            if (group.get(i).getGroupId().equals(groupId)){
                index = i;
                break;
            }
        }
        listImageAdapter.setData(res.get(index));
        popupWindowUtil.dismiss();
    }

    public void initData(){
        listImageAdapter.setData(res.get(0));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_images,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        cancel = view.findViewById(R.id.cancel);
        preview = view.findViewById(R.id.preview);
        selector = view.findViewById(R.id.select);
        other = view.findViewById(R.id.other);
        ok = view.findViewById(R.id.ok);
        group = new ArrayList<>();
        if (listImageAdapter == null)
            listImageAdapter = new ListImageAdapter(this);
        handler = new ImagesPickFragmentHandler(this);
        new Thread(()->{
            res = new ArrayList<>(readAllImageDirectory());
            handler.sendEmptyMessage(1);
        }).start();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(listImageAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        selector.setOnClickListener(v -> {
            createPop();
        });
    }
    PopupWindowUtil popupWindowUtil;
    private void createPop(){
        //android.provider.DocumentsContract.CalendarContract.Browser.BlockedNumberContract.AlarmClock
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_pick_images,null);
        RecyclerView recyclerView1 = view.findViewById(R.id.recycler);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(new ListImageGroupAdapter(this,group));
        popupWindowUtil = new PopupWindowUtil();
        popupWindowUtil.create(view, ViewGroup.LayoutParams.MATCH_PARENT, (int) ( 500* ScreenFitUtil.getDensity()),getActivity(),other);
        popupWindowUtil.showAsDropDown(selector);
    }

    private Collection<List<ImageBean>> readAllImageDirectory(){
        String[] columns = {MediaStore.Images.Media._ID, MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.MediaColumns.MIME_TYPE,MediaStore.MediaColumns.SIZE
        };
        String sortOrder = MediaStore.Images.Media.DATE_ADDED+" desc";
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, sortOrder);
        LinkedHashMap<String,List<ImageBean>> map = new LinkedHashMap<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            String thumbnailsPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            String path =  cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String groupId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
            String groupName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            String type = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
            String size = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
            ImageBean imageBean = new
                    ImageBean(id,thumbnailsPath,path,groupId,groupName,type,size);
            if (map.containsKey(groupId)){
                map.get(groupId).add(imageBean);
            }else {
                ImageGroupBean imageGroupBean = new ImageGroupBean(groupId,groupName,path);
                group.add(imageGroupBean);
                List<ImageBean> l = new ArrayList<>();
                l.add(imageBean);
                map.put(groupId,l);
            }
        }
        cursor.close();
        return map.values();
    }
}
