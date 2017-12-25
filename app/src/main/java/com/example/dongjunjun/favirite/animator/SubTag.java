package com.example.dongjunjun.favirite.animator;

import android.graphics.Paint;

/**
 * 二级标签
 * Created by dongjunjun on 2017/12/13.
 */

public class SubTag extends Tag {

    private BallInfo leftBall;
    private RectInfo rightRect;
    private RectInfo centerRect;
    private String text;
    private Paint mTextPaint;
    private Paint mBgCenterPaint;
    private Paint mBgOtherPaint;

    public static final int SUBTAG_CAPACITY = 6;

    public SubTag(float x, float y) {
        super(x, y);
    }


    public SubTag(float x, float y, BallInfo leftBall, RectInfo rightRect, RectInfo centerRect, String text,
                  Paint textPaint, Paint bgCenterPaint, Paint bgOtherPaint) {
        this(x, y);
        this.leftBall = leftBall;
        this.rightRect = rightRect;
        this.centerRect = centerRect;
        this.text = text;
        mTextPaint = textPaint;
        mBgCenterPaint = bgCenterPaint;
        mBgOtherPaint = bgOtherPaint;

    }

    public BallInfo getLeftBall() {
        return leftBall;
    }

    public void setLeftBall(BallInfo leftBall) {
        this.leftBall = leftBall;
    }

    public RectInfo getRightRect() {
        return rightRect;
    }

    public void setRightRect(RectInfo rightRect) {
        this.rightRect = rightRect;
    }

    public RectInfo getCenterRect() {
        return centerRect;
    }

    public void setCenterRect(RectInfo centerRect) {
        this.centerRect = centerRect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Paint getTextPaint() {
        return mTextPaint;
    }

    public void setTextPaint(Paint textPaint) {
        mTextPaint = textPaint;
    }

    public Paint getBgCenterPaint() {
        return mBgCenterPaint;
    }

    public void setBgCenterPaint(Paint bgCenterPaint) {
        mBgCenterPaint = bgCenterPaint;
    }

    public Paint getBgOtherPaint() {
        return mBgOtherPaint;
    }

    public void setBgOtherPaint(Paint bgOtherPaint) {
        mBgOtherPaint = bgOtherPaint;
    }


}
