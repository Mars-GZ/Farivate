package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.dongjunjun.favirite.animator.helper.Direction;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.FLOW_MAX;

/**
 * Created by dongjunjun on 2017/12/12.
 */

public abstract class Renderable {

    protected float x, y;
    protected float translationX, translationY;
    protected Paint paint;
    private int num = -1;//控件编号

    protected int priority;//根据优先度来显示
    private @Direction
    int direction = Direction.RED_CENTER;//浮动方向

    protected Rect layoutBoundary = new Rect();//view的布局边界,相对于view的父布局
    protected Rect normalRebound = new Rect();//view的初始布局边界
    protected Rect boundary = new Rect();//view的浮动边界

    public Renderable(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(@Direction int direction) {
        this.direction = direction;
    }

    public Rect getLayoutBoundary() {
        return layoutBoundary;
    }

    public void setLayoutBoundary(Rect layoutBoundary) {
        this.layoutBoundary = layoutBoundary;
    }

    public void setLayoutBoundary(int l, int t, int r, int b) {
        this.layoutBoundary.set(l, t, r, b);
    }

    public Rect getBoundary() {
        return boundary;
    }

    public void setBoundary(Rect boundary) {
        this.boundary.set(boundary);
    }

    public void setBoundary(int l, int t, int r, int b) {
        this.boundary.set(l, t, r, b);
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

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public float getTranslationX() {
        return translationX;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    public float getTranslationY() {
        return translationY;
    }

    protected abstract void draw(Canvas canvas);

    public void destroy() {
    }

    protected void update() {

    }

    /**
     * 判断是否在边界内
     *
     * @return
     */
    public boolean checkedInLimit() {
        return boundary.contains((int) (x + translationX), (int) (y + translationY));
    }

    /**
     * 判断超出的方向
     *
     * @return
     */
    public int checkedLimitDirection() {
        return -1;
    }
}
