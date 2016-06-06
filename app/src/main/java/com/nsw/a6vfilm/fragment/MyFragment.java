package com.nsw.a6vfilm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nsw.a6vfilm.Dialog.TestBulrDialog;
import com.nsw.a6vfilm.R;

/**
 * Created by niushuowen on 2016/5/9.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        return view;
    }

    public void openDialog(View view){
        TestBulrDialog dialog = new TestBulrDialog(getActivity());

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        openDialog(v);
    }
}
