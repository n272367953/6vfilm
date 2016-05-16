package com.nsw.a6vfilm.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nsw.a6vfilm.model.CarouselMode;

import java.util.List;

/**
 * Created by niushuowen on 2016/5/12.
 */
public class CustomCarouselView extends LinearLayout {

    private ViewPager viewPager;
    private TextView titleText;
    private LinearLayout indexContainer;
    /**
     * 当前显示的指示器Drawable
     */
    private ShapeDrawable indexCurDrawable;

    /**
     * 不是当前正在显示的指示器Drawable
     */
    private ShapeDrawable indexUnCurDrawable;


    public CustomCarouselView(Context context) {
        super(context);
    }

    public CustomCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initIndicateDrawable();
    }

    private void init(Context context) {
        setOrientation(super.VERTICAL);
        viewPager = new ViewPager(context);
        LinearLayout.LayoutParams pagerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewPager.setLayoutParams(pagerParams);
        indexContainer = new LinearLayout(context);
        titleText = new TextView(context);
        FrameLayout titleLayout = new FrameLayout(context);
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLayout.setLayoutParams(titleLayoutParams);
        addView(viewPager);
        addView(titleLayout);
        FrameLayout.LayoutParams titleTextLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.gravity = Gravity.LEFT;
        titleText.setLayoutParams(titleTextLayoutParams);
        FrameLayout.LayoutParams indexContainerLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        indexContainerLayoutParams.gravity = Gravity.RIGHT;
        indexContainer.setLayoutParams(indexContainerLayoutParams);
        titleLayout.addView(titleText);
        titleLayout.addView(indexContainer);
    }


    /**
     * 初始化默认的指示器
     */
    private void initIndicateDrawable() {
        Shape curShape = new OvalShape();
        Shape unCurShape = new OvalShape();
        indexCurDrawable = new ShapeDrawable(curShape);
        indexUnCurDrawable = new ShapeDrawable(unCurShape);
        Paint curPaint = indexCurDrawable.getPaint();
        Paint unCurPaint = indexUnCurDrawable.getPaint();
        curPaint.setColor(Color.BLUE);
        unCurPaint.setColor(Color.WHITE);
    }

    public class CarouselAdapter extends PagerAdapter {

        private List<CarouselMode> list;
        private Context context;


        public CarouselAdapter(Context context,List list) {
            this.list = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView sdv = new SimpleDraweeView(context);
            CarouselMode mode = list.get(position);
            if(!TextUtils.isEmpty(mode.getImgUrl())){
                sdv.setImageURI(Uri.parse(mode.getImgUrl()));
            }
            sdv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClick();
                }
            });
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        public void setOnItemClick(CarouselItemOnClickListener listener){
                listener.onItemClick();
        }
    }

    public interface CarouselItemOnClickListener{
        public void onItemClick();
    }

}

