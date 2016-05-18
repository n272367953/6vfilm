package com.nsw.a6vfilm.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nsw.a6vfilm.model.CarouselMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niushuowen on 2016/5/12.
 */
public class CustomCarouselView extends LinearLayout {

    private Context context;

    private ViewPager viewPager;
    private TextView titleText;
    private LinearLayout indexContainer;
    private int lastIndexPosition;

    private List<ImageView> indicatorList;

    /**
     * 指示器小圆点数量
     */
    private int indicatorNum;

    /**
     * 当前显示的指示器Drawable
     */
    private ShapeDrawable indexCurDrawable;

    /**
     * 不是当前正在显示的指示器Drawable
     */
    private ShapeDrawable indexUnCurDrawable;

    /**
     * 当前选择的pager的颜色
     */
    private static final int CUR_CLOLOR = 0XFF3F51B5;

    /**
     * 当前未选择的pager的颜色
     */
    private static final int UNCUR_CLOLOR = 0xFF9c9c9c;

    /**
     * 轮播图的宽高比(固定)
     */
    private static final float Pager_ASPECT_RATIO = 1.55f;

    /**
     * 小圆点直径
     */
    private static final float POINT_WIDTH = 5F;

    public CustomCarouselView(Context context) {
        this(context, null);
    }

    public CustomCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initIndicateDrawable();
        init(context);
    }

    private void init(final Context context) {
        setOrientation(super.VERTICAL);
        setBackgroundColor(0XFFFFFFFF);
        viewPager = new ViewPager(context);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateIndicate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //viewpager 布局参数
        LinearLayout.LayoutParams pagerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (context.getResources().getDisplayMetrics().widthPixels / Pager_ASPECT_RATIO));
        viewPager.setLayoutParams(pagerParams);
        indexContainer = new LinearLayout(context);
        indexContainer.setOrientation(HORIZONTAL);
        titleText = new TextView(context);
        //轮播图对应标题指示器容器的布局参数
        FrameLayout titleLayout = new FrameLayout(context);
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLayout.setLayoutParams(titleLayoutParams);
        addView(viewPager);
        addView(titleLayout);
        //标题布局参数
        FrameLayout.LayoutParams titleTextLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.gravity = Gravity.LEFT;
        initTitleTextParams(titleTextLayoutParams);
        titleText.setLayoutParams(titleTextLayoutParams);
        FrameLayout.LayoutParams indexContainerLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        indexContainerLayoutParams.setMargins(dp2px(10f), 0, dp2px(10f), 0);
        indexContainerLayoutParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        indexContainer.setLayoutParams(indexContainerLayoutParams);
        titleLayout.addView(titleText);
        titleLayout.addView(indexContainer);
        View line_view = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(0.5f));
        line_view.setBackgroundColor(UNCUR_CLOLOR);
        line_view.setLayoutParams(lineParams);
        addView(line_view);

        titleText.setText("火锅英雄,赶紧来看");
    }

    private void autoScrollCarousel() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int curIndex = viewPager.getCurrentItem();
                if (curIndex == indicatorNum - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(curIndex + 1);
                }
                autoScrollCarousel();
            }
        }, 3000);
    }

    private void initTitleTextParams(FrameLayout.LayoutParams params) {
        titleText.setTextSize(15f);
        titleText.setTextColor(0xff000000);
        params.setMargins(dp2px(10f), dp2px(5f), dp2px(10f), dp2px(5f));
        titleText.setMaxLines(1);

    }

    /**
     * 初始化默认的指示器Drawable
     */
    private void initIndicateDrawable() {
        Shape curShape = new OvalShape();
        Shape unCurShape = new OvalShape();
        indexCurDrawable = new ShapeDrawable(curShape);
        indexUnCurDrawable = new ShapeDrawable(unCurShape);
        Paint curPaint = indexCurDrawable.getPaint();
        Paint unCurPaint = indexUnCurDrawable.getPaint();
        curPaint.setColor(CUR_CLOLOR);
        unCurPaint.setColor(UNCUR_CLOLOR);
    }

    /**
     * 更新指示器
     *
     * @param context
     * @param totalPages
     * @param curPageIndex
     */
    private void initIndicates(Context context, int totalPages, int curPageIndex) {

        if (indicatorList == null) {
            indicatorList = new ArrayList<>();
        }
        for (int i = 0; i < totalPages; i++) {
            ImageView view = new ImageView(context);
            if (i == curPageIndex) {
                lastIndexPosition = i;
                setCompatBackground(view, indexCurDrawable);
            } else {
                setCompatBackground(view, indexUnCurDrawable);
            }
            indicatorList.add(view);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(POINT_WIDTH), dp2px(POINT_WIDTH));
            params.gravity = Gravity.CENTER_VERTICAL;
            params.setMargins(dp2px(POINT_WIDTH) / 2, dp2px(POINT_WIDTH), dp2px(POINT_WIDTH) / 2, dp2px(POINT_WIDTH));
            view.setLayoutParams(params);
            indexContainer.addView(view);
        }
    }

    /**
     * dp转px
     */
    private int dp2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void updateIndicate(int position) {
        if (indicatorNum > 0) {
            setCompatBackground(indicatorList.get(lastIndexPosition), indexUnCurDrawable);
            setCompatBackground(indicatorList.get(position), indexCurDrawable);
            lastIndexPosition = position;
        }
    }

    private void setCompatBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }


    private int startX;
    private int startY;




    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int curX = (int) ev.getRawX();
                int curY = (int) ev.getRawY();
                int deltaX = Math.abs(curX - startX);
                int deltaY = Math.abs(curY - startY);

                Log.i("DELTA","deltaX: "+deltaX + "\ndeltaY: "+deltaY);
                if(deltaX > deltaY ){
                   getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setAdapter(CarouselAdapter adapter) {
        viewPager.setAdapter(adapter);
        indicatorNum = adapter.getCount();
        if (indicatorNum > 0) {
            initIndicates(context, indicatorNum, 0);
        }
//        autoScrollCarousel();
    }

    public static class CarouselAdapter extends PagerAdapter {
        private List<CarouselMode> list;
        private Context context;
        private CarouselItemOnClickListener onItemClickListener;


        public CarouselAdapter(Context context, List list) {

            this.context = context;
            this.list = list;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final SimpleDraweeView sdv = new SimpleDraweeView(context);
            sdv.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
            sdv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            sdv.setAspectRatio(10f);
            CarouselMode mode = list.get(position);
            if (!TextUtils.isEmpty(mode.getImgUrl())) {
                sdv.setImageURI(Uri.parse(mode.getImgUrl()));
            }
            sdv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(container, v, position);
                    }
                }
            });
            container.addView(sdv);
            return sdv;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public void setCarouselModeList(List<CarouselMode> list) {
            this.list = list;
        }

        /**
         * set the single view click listener
         *
         * @param listener
         */
        public void setOnItemClickListener(CarouselItemOnClickListener listener) {
            this.onItemClickListener = listener;
        }
    }

    /**
     * an interface ,the method onItemClick() will be invoked when click the carouselView item
     */
    public interface CarouselItemOnClickListener {
        public void onItemClick(ViewGroup container, View view, int position);
    }

}

