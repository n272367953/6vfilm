package com.nsw.a6vfilm.fragment;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nsw.a6vfilm.R;
import com.nsw.a6vfilm.model.CarouselMode;
import com.nsw.a6vfilm.utils.SystemUtils;
import com.nsw.a6vfilm.utils.T;
import com.nsw.a6vfilm.view.CustomCarouselView;

import java.util.ArrayList;
import java.util.List;

import listview.PagingBaseAdapter;
import listview.PagingListView;

import static com.nsw.a6vfilm.R.id.status_bar_bg;

/**
 * Created by niushuowen on 2016/5/9.
 */
public class IndexFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private List<CarouselMode> carouselList;
    private FrameLayout container;
    private CustomCarouselView.CarouselAdapter adapter;
    private View indexSecondHeader;
    private PagingListView listview;
    private View statusBarBg;

    private LinearLayout titleStatusBarContainer;

    private float title_status_bar_alpha = 0f;

    public static String[] images = new String[]{
            "http://tu.66vod.net/2015/3841.jpg",
            "http://tu.66vod.net/2015/4286.jpg",
            "http://tu.66vod.net/2015/4169.jpg",
            "http://tu.66vod.net/2015/3180.jpg",
            "http://tu.66vod.net/2015/4071.jpg"
    };

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        initView(view);
        initData();
        return view;
    }


    private CustomCarouselView car_view;

    private void initView(View view) {
        titleStatusBarContainer = (LinearLayout) view.findViewById(R.id.title_status_bar_container);
        if(Build.VERSION.SDK_INT >19){
            statusBarBg = view.findViewById(status_bar_bg);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBarBg.getLayoutParams();
            params.height = SystemUtils.getStatusBarHeight(getActivity());
        }
        listview = (PagingListView) view.findViewById(R.id.listview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        container = (FrameLayout) view.findViewById(R.id.container);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
////        refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
////                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
////                        .getDisplayMetrics()));
////        refreshLayout.setRefreshing(true43);
        refreshLayout.setOnRefreshListener(onRefreshListener);
        car_view = new CustomCarouselView(getActivity());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        car_view.setLayoutParams(params);
//        container.addView(car_view);
        indexSecondHeader = getActivity().getLayoutInflater().inflate(R.layout.index_second_header, car_view, false);
        car_view.addView(indexSecondHeader);
        listview.addHeaderView(car_view);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    title_status_bar_alpha =Float.valueOf( Math.abs(car_view.getTop())) / Float.valueOf(car_view.getMeasuredHeight()-indexSecondHeader.getMeasuredHeight());
                    if(title_status_bar_alpha <1){
                        setTitleStatusBarAlpha(title_status_bar_alpha);
                    }else{
                        setTitleStatusBarAlpha(1);
                    }
                    title_status_bar_alpha = 0.0f;

            }
        });
        MyAdapter adapter = new MyAdapter();
        listview.setAdapter(adapter);
    }

    private void initData() {
        carouselList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            CarouselMode mode = new CarouselMode();
            mode.setTitle(String.valueOf(i));
            mode.setImgUrl(images[i]);
            carouselList.add(mode);
        }
        adapter = new CustomCarouselView.CarouselAdapter(getActivity(), carouselList);
        adapter.setOnItemClickListener(new CustomCarouselView.CarouselItemOnClickListener() {
            @Override
            public void onItemClick(ViewGroup container, View view, int position) {

            }
        });
        car_view.setScrollerListener(new CustomCarouselView.CarouselScrollerListenrer() {
            @Override
            public void startScroller() {
                refreshLayout.setEnabled(false);
            }

            @Override
            public void endsScroller() {
                refreshLayout.setEnabled(true);
            }
        });
        car_view.setAdapter(adapter);

//        adapter.setCarouselModeList(carouselList);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            T.showShort(getActivity(), "正在刷新，请稍等。。。。");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 3000);
        }
    };

    private void setTitleStatusBarAlpha(float alpha) {
        titleStatusBarContainer.setAlpha(alpha);
    }

    class MyAdapter extends PagingBaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.film_item_view, parent, false);
            }

            return convertView;
        }
    }

}
