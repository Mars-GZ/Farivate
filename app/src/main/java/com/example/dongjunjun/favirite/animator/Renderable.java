package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

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

    protected RectF layoutBoundary = new RectF();//view的布局边界,相对于view的父布局
    protected RectF normalRebound = new RectF();//view的初始布局边界
//    protected RectF boundary = new RectF();//view的浮动边界

    public Renderable(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public RectF getNormalRebound(){
        return normalRebound;
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

    public RectF getLayoutBoundary() {
        return layoutBoundary;
    }

    public void setLayoutBoundary(RectF layoutBoundary) {
        this.layoutBoundary = layoutBoundary;
    }

    public void setLayoutBoundary(float l, float t, float r, float b) {
        this.layoutBoundary.set(l, t, r, b);
    }

//    public RectF getBoundary() {
//        return boundary;
//    }
//
//    public void setBoundary(RectF boundary) {
//        this.boundary.set(boundary);
//    }
//
//    public void setBoundary(float l, float t, float r, float b) {
//        this.boundary.set(l, t, r, b);
//    }

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

    public void addTranslationX(float dx){
        this.translationX+=dx;
    }

    public float getTranslationX() {
        return translationX;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    public void addTranslationY(float dy){
        this.translationY+=dy;
    }

    public float getTranslationY() {
        return translationY;
    }

    protected abstract void draw(Canvas canvas);

    public void destroy() {
    }

    protected void update(Renderable... fix) {

    }

    /**
     * 判断是否在边界内
     *
     * @return
     */
    public boolean checkedInLimit() {
        return false;
//        return boundary.contains((int) (x + translationX), (int) (y + translationY));
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
