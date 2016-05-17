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

    public static String[] images = new String[]{
            "http://g.hiphotos.baidu.com/image/pic/item/0e2442a7d933c895f7056f7ad31373f0820200b3.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/bd3eb13533fa828b207b80edff1f4134970a5a67.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/d50735fae6cd7b89206f2b760d2442a7d8330ec7.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600cd9258b5eb50352ac65cb750.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/aa64034f78f0f7365edc7d030855b319ebc41395.jpg"
    };

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        initView(view);
        initData();
        return view;
    }



    private void initView(View view) {
//        listview = (PagingListView) view.findViewById(R.id.listview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        container = (FrameLayout) view.findViewById(R.id.container);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary);
//        refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
//        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(onRefreshListener);
        CustomCarouselView car_view = new CustomCarouselView(getActivity());
        adapter = new CustomCarouselView.CarouselAdapter(getActivity(),carouselList);
        adapter.setOnItemClickListener(new CustomCarouselView.CarouselItemOnClickListener() {
            @Override
            public void onItemClick(ViewGroup container, View view, int position) {
                T.showShort(getActivity(),"点击了");
            }
        });
        car_view.setAdapter(adapter);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(800,500);
        car_view.setLayoutParams(params);
        container.addView(car_view);
        
    }

    private void initData() {
          carouselList = new ArrayList<>();
          for(int i=0; i<images.length;i++){
              CarouselMode mode = new CarouselMode();
              mode.setTitle(String.valueOf(i));
              mode.setImgUrl(images[i]);
              carouselList.add(mode);
          }
        adapter.setCarouselModeList(carouselList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            T.showShort(getActivity(),"正在刷新，请稍等。。。。");
        }
    };
}
