package com.nsw.a6vfilm;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.nsw.a6vfilm.activity.BaseActivity;
import com.nsw.a6vfilm.fragment.CategoryFragment;
import com.nsw.a6vfilm.fragment.FragmentHelper;
import com.nsw.a6vfilm.fragment.IndexFragment;
import com.nsw.a6vfilm.fragment.MyFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    //底部3个Tab标志
    public static final String MENU_TAB_1 = "6vfilm.index";
    public static final String MENU_TAB_2 = "6vfilm.category";
    public static final String MENU_TAB_3 = "6vfiml.my";


    private FragmentHelper fmHelper;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(this);
        fmHelper = new FragmentHelper(this, getSupportFragmentManager(), R.id.main_container);
        if(savedInstanceState == null){
            //默认显示首页
            fmHelper.switchFragment(MENU_TAB_1,new Intent(this,IndexFragment.class));
        }else{
            fmHelper.restoreFromBundle(savedInstanceState);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        fmHelper.saveStateSimple(outState);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Intent intent = null;
        String id = null;
        switch (checkedId) {
            case R.id.index_radioBtn:
                intent = new Intent(this, IndexFragment.class);
                id = MENU_TAB_1;
                break;
            case R.id.cate_radioBtn:
                intent = new Intent(this, CategoryFragment.class);
                id = MENU_TAB_2;
                break;
            case R.id.my_radioBtn:
                intent = new Intent(this, MyFragment.class);
                id = MENU_TAB_3;
                break;
        }
        fmHelper.switchFragment(id,intent);
    }
}
