package com.nsw.a6vfilm.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nsw.a6vfilm.R;
import com.nsw.a6vfilm.model.CarouselMode;
import com.nsw.a6vfilm.utils.T;
import com.nsw.a6vfilm.view.CustomCarouselView;

import java.util.ArrayList;
import java.util.List;

import listview.PagingListView;

/**
 * Created by niushuowen on 2016/5/9.
 */
public class IndexFragment extends Fragment {

    private PagingListView listview;
    private SwipeRefreshLayout refreshLayout;
    private List<CarouselMode> carouselList;
    private FrameLayout container;
    private CustomCarouselView.CarouselAdapter adapter;
    private View indexSecondHeader;

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
//        listview = (PagingListView) view.findViewById(R.id.listview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        container = (FrameLayout) view.findViewById(R.id.container);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary);
////        refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
////                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
////                        .getDisplayMetrics()));
////        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(onRefreshListener);
        car_view = new CustomCarouselView(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        car_view.setLayoutParams(params);
        container.addView(car_view);
        indexSecondHeader = getActivity().getLayoutInflater().inflate(R.layout.index_second_header,car_view,false);
        car_view.addView(indexSecondHeader);
    }

    private void initData() {
          carouselList = new ArrayList<>();
          for(int i=0; i<images.length;i++){
              CarouselMode mode = new CarouselMode();
              mode.setTitle(String.valueOf(i));
              mode.setImgUrl(images[i]);
              carouselList.add(mode);
          }
        adapter = new CustomCarouselView.CarouselAdapter(getActivity(),carouselList);
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
            T.showShort(getActivity(),"正在刷新，请稍等。。。。");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   refreshLayout.setRefreshing(false);
                }
            },3000);
        }
    };
}
