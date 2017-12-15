package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by dongjunjun on 2017/12/12.
 */

public abstract class Renderable {

    protected float x, y;
    protected float translationX, translationY;
    protected Paint paint;

    protected int priority;//根据优先度来显示
    private int direction;//浮动方向

    private RectF boundary;//view的边界

    public Renderable(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setBoundary(RectF boundary) {
        this.boundary = boundary;
    }

    public RectF getBoundary() {
        return boundary;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX(){
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY(){
        return y;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public float getTranslationX(){
        return translationX;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    public float getTranslationY(){
        return translationY;
    }

    protected abstract void draw(Canvas canvas);

    public void destroy() {
    }

    protected void update() {

    }
}
