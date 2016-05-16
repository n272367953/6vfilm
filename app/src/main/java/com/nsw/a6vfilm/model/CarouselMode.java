package com.nsw.a6vfilm.model;

/**
 * Created by niushuowen on 2016/5/16.
 */
public class CarouselMode {

    /**
     * 轮播图标题
     */
    private String title;

    /**
     * 对应的图片链接
     */
    private String imgUrl;

    /**
     * 点击后要打开的链接
     */
    private String clickUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

}
