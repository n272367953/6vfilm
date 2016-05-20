package com.nsw.a6vfilm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nsw.a6vfilm.R;

/**
 * Created by niushuowen on 2016/5/9.
 */
public class CategoryFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup tabs;
    private RadioButton tab_cate;
    private RadioButton tab_coun;
    private ViewPager viewPager;
    private View pagerByCate;
    private View pagerByCountry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        tabs = (RadioGroup) view.findViewById(R.id.id_tabs);
        tab_coun = (RadioButton) view.findViewById(R.id.id_tab_2);
        tab_cate = (RadioButton) view.findViewById(R.id.id_tab_1);
        viewPager = (ViewPager) view.findViewById(R.id.id_tab_pager);
        CategroyPagerAdapter adapter = new CategroyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
                    tabs.check(R.id.id_tab_1);
                } else {
                    tabs.check(R.id.id_tab_2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs.setOnCheckedChangeListener(this);
        clickByCountry();
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.id_tab_1:
                clickByCountry();
                break;
            case R.id.id_tab_2:
                clickByCategory();
                break;
        }
    }

    /**
     * 点击按类别
     */
    private void clickByCategory() {
        tabs.check(R.id.id_tab_2);
        viewPager.setCurrentItem(1);
    }

    /**
     * 点击按国家
     */
    private void clickByCountry() {
        tabs.check(R.id.id_tab_1);
        viewPager.setCurrentItem(0);
    }

    public class CategroyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            if (position == 0) {
                view = View.inflate(getActivity(), R.layout.by_country_view, null);
            } else {
                view = View.inflate(getActivity(), R.layout.by_category_view, null);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }

}