package com.person.newscopy.edit.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.edit.EditActivity;

public class AddItemFragment extends ListFragment {

    EditActivity activity;
    ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.tag, android.R.layout.simple_spinner_item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        activity.setTag(((TextView)v).getText().toString());
    }

}
