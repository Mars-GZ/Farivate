package com.example.dongjunjun.favirite.animator;

import android.graphics.RectF;

public class RectInfo {


    private float left;
    private float top;
    private float width;
    private float height;
    private float r;

    public RectInfo(float left, float top, float width, float height, float r) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.r = r;
    }

    public RectF toRect() {
        return new RectF(left, top, left+width, top+height);
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }
}
