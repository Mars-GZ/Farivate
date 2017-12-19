package com.example.dongjunjun.favirite.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.FrameLayout;

import static com.example.dongjunjun.favirite.animator.Balloon.State.NONE;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.EVEN_TOP;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.INIT_RADIUS;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.ODD_TOP;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.TAG_CAPACITY;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.TAG_SIZE;

/**
 * 盛放兴趣标签的View
 * Created by dongjunjun on 2017/12/13.
 */

public class BalloonView extends FrameLayout {

    private Balloon mBalloon;
    private MajorTag mMajorTag;

    private SparseArray<SubTagView> mSubTags;

    public BalloonView(@NonNull Context context) {
        super(context);
        init();
    }

    public BalloonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initBalloon();
        initTags();
    }

    private void initBalloon() {
        mBalloon = new Balloon(0, 0, 0);
    }

    private void initTags() {
        mMajorTag = new MajorTag(0, 0);
        mSubTags = new SparseArray<>(TAG_CAPACITY);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setText(String text) {
        mMajorTag.setText(text);
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
        layoutChildren();
    }

    private void layoutChildren() {
        boolean isNone = isNone();
        if (isNone) {
            initChildrenWithData();
        }
        mBalloon.calculateState();
    }

    private void initChildrenWithData() {
        //init Balloon
        mBalloon.setX(getMeasuredWidth() / 2);
        mBalloon.setY(getMeasuredHeight() / 2);
        mBalloon.setRadius(getMeasuredWidth() / 2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        mBalloon.setPaint(paint);

        //init Tag
        mMajorTag.setX(mBalloon.getX());
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(TAG_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        mMajorTag.setBaseLine((getMeasuredHeight() - metrics.bottom + metrics.top) / 2 - metrics.top);
        mMajorTag.setPaint(paint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mBalloon.draw(canvas);
        mMajorTag.draw(canvas);
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

    public Balloon getModel() {
        return mBalloon;
    }

    private boolean isNone() {
        return mBalloon.getState() == NONE;
    }
}
