package com.example.dongjunjun.favirite.animator;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 盛放兴趣标签的View
 * Created by dongjunjun on 2017/12/13.
 */

public class BalloonView extends FrameLayout {

    private Balloon mBalloon;
    private MajorTag mMajorTag;

    private SubTagView[] mSubTags;

    public BalloonView(@NonNull Context context) {
        super(context);
    }

    public BalloonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BalloonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BalloonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initBalloon(){
        mBalloon = new Balloon(getWidth()/4,getWidth()/2,getHeight()/2);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setText(String text){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initBalloon();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBalloon.draw(canvas);
    }

    /**
     * 更新浮动坐标
     */
    public void updateFlow() {
        if (mBalloon != null) {
            mBalloon.update();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void releaseResources() {

    }

    public Balloon getModel(){
        return mBalloon;
    }
}
