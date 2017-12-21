package com.example.dongjunjun.favirite.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.example.dongjunjun.favirite.animator.event.FlowEvent;
import com.example.dongjunjun.favirite.animator.event.SelectBalloonEvent;
import com.example.dongjunjun.favirite.animator.helper.AnimatorHelper;
import com.example.dongjunjun.favirite.animator.helper.RandomHelper;
import com.example.dongjunjun.favirite.animator.listener.BalloonItemClickListener;

import org.greenrobot.eventbus.EventBus;

import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND_TO_SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NONE;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL_TO_EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL_TO_SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.SMALL_TO_EXPAND;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.FLOW_MAX;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.TAG_CAPACITY;

/**
 * 盛放兴趣标签的View
 * Created by dongjunjun on 2017/12/13.
 */

public class BalloonView extends FrameLayout {

    private Balloon mBalloon;
    private MajorTag mMajorTag;
    private SparseArray<SubTagView> mSubTags;

    GestureDetectorCompat mGestureCompat;
    BalloonItemClickListener mItemClickListener;

    public BalloonView(@NonNull Context context) {
        super(context);
        init();
    }

    public BalloonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setClickable(true);
        initBalloon();
        initTags();
        mGestureCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    private void initBalloon() {
        mBalloon = new Balloon(0, 0, 0);
    }

    private void initTags() {
        mMajorTag = new MajorTag(0, 0);
        mMajorTag.setParent(mBalloon);
        mMajorTag.setSelected(mBalloon.isSelected());
        mBalloon.addChild(mMajorTag);
        mSubTags = new SparseArray<>(TAG_CAPACITY);
    }

    public void setItemClickListener(BalloonItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setText(String text) {
        mMajorTag.setText(text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureCompat.onTouchEvent(event)) {
            float x = event.getX();
            float y = event.getY();
            int state = mBalloon.getState();
            if (AnimatorHelper.getInstance().canPlayAnimator() && (state == NORMAL || state == SMALL)) {
                if (mBalloon.inCircle(x, y)) {
                    EventBus.getDefault().post(new FlowEvent(true));
                    EventBus.getDefault().post(new SelectBalloonEvent(mBalloon.position));
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(mMajorTag, mBalloon.getNum(), -1);
                    }
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
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
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //init Balloon
        mBalloon.setRadius((width - 2 * FLOW_MAX) / 2);
        mBalloon.setX(width / 2);
        int num = BalloonConstant.getColumn(mBalloon.getNum());
        if ((num & 1) == 0) {
            //偶数列
            mBalloon.setY(height / 2);
        } else {
            //奇数列
            mBalloon.setY(mBalloon.getRadius());
        }
        //init Tag
        mMajorTag.setX(mBalloon.getX());

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBalloon.setPaint(paint);
        RandomHelper.setBalloonColor(mBalloon);
        RandomHelper.setTextColor(mBalloon);
        if (mMajorTag.getPaint() != null) {
            Paint.FontMetrics metrics = mMajorTag.getPaint().getFontMetrics();
            mMajorTag.setBaseLine((mBalloon.getY() * 2 - paint.getStrokeWidth() - metrics.top - metrics.bottom) / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBalloon.draw(canvas,BalloonMeasure.backPaint);
        mMajorTag.draw(canvas);
    }

    /**
     * 更新浮动坐标
     */
    public void updateFlow() {
        if (mBalloon != null) {
            mBalloon.update(mMajorTag);
        }
    }

    ValueAnimator animator;
    float targetX, targetY, targetR;

    public Animator getAnimator() {
        mBalloon.cutLayoutRect();
        mBalloon.reset();
        mBalloon.match();
        mMajorTag.reset();
        initAnimator();
        return animator;
    }

    /**
     * 设定动画值
     */
    private void initAnimator() {
        int state = mBalloon.getState();
        final BalloonMeasure measure = mBalloon.getMeasure();
        switch (state) {
            case NORMAL_TO_SMALL:
                mBalloon.setSmallPosition(measure.getSmallPosition(mBalloon.getNum()));
            case EXPAND_TO_SMALL:
                targetR = measure.getSmallRadius();
                targetX = measure.getSmallLeftMargin(mBalloon.getSmallPosition());
                targetY = measure.getSmallTopMargin(mBalloon.getSmallPosition());
                break;
            case NORMAL_TO_EXPAND:
                mBalloon.setSmallPosition(measure.getSmallPosition(mBalloon.getNum()));
                targetR = measure.getBigRadius();
                targetX = measure.getBigLeftMargin();
                targetY = measure.getBigTopMargin();
                break;
            case SMALL_TO_EXPAND:
                targetR = measure.getBigRadius();
                targetX = measure.getBigLeftMargin();
                targetY = measure.getBigTopMargin();
                bringToFront();
                break;
        }
        targetX = mBalloon.layoutBoundary.left - targetX;
        targetY = mBalloon.layoutBoundary.top - targetY;
        targetR = mBalloon.getRadius() - targetR;
        animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (mBalloon == null) {
                    return;
                }
                float value = (float) animation.getAnimatedValue();
                float dx = targetX * value;
                float dy = targetY * value;
                float dr = targetR * value;
                int state = mBalloon.getState();
                RectF layoutBoundary = mBalloon.getLayoutBoundary();
                RectF normalRebound = mBalloon.getNormalRebound();
                layoutBoundary.left = normalRebound.left - dx;
                layoutBoundary.top = normalRebound.top - dy;
                layoutBoundary.right = layoutBoundary.left + normalRebound.width() - 2 * dr;
                layoutBoundary.bottom = layoutBoundary.top + normalRebound.height() - 2 * dr;
                mBalloon.setRadius(layoutBoundary.width() / 2);
                mBalloon.match();
                mMajorTag.match((int) layoutBoundary.height());
                RandomHelper.setBalloonColor(mBalloon);
                if (state == NORMAL_TO_EXPAND || state == SMALL_TO_EXPAND) {
                    mMajorTag.setBaseLine(mMajorTag.getBaseLine() + mBalloon.getRadius() * value);
                    if (value >= 0.8) {
                        if (mMajorTag.isSelected()) {
                            mMajorTag.setSelected(false);
                            RandomHelper.setTextColor(mBalloon);
                        }
                        mBalloon.setAlpha(BalloonConstant.getExpandAlpha(value));
                    }
                } else if (state == EXPAND_TO_SMALL) {
                    mMajorTag.setBaseLine(mMajorTag.getBaseLine() + mBalloon.getRadius() * (1 - value));
                    if (!mBalloon.isSelected()) {
                        mBalloon.setSelected(true);
                        RandomHelper.setBalloonColor(mBalloon);
                    }
                    if (value > 0.2) {
                        if (!mMajorTag.isSelected()) {
                            mMajorTag.setSelected(true);
                            RandomHelper.setTextColor(mBalloon);
                        }
                    }else {
                        mBalloon.setAlpha(BalloonConstant.getSmallAlpha(value));
                    }
                }
                requestLayout();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mBalloon == null) {
                    return;
                }
                int state = mBalloon.getState();
                switch (state) {
                    case NORMAL_TO_SMALL:
                    case EXPAND_TO_SMALL:
                        mBalloon.setState(SMALL);
                        break;
                    case NORMAL_TO_EXPAND:
                    case SMALL_TO_EXPAND:
                        mBalloon.setState(EXPAND);
                        if (mBalloon.isSelected()) {
                            mBalloon.setSelected(false);
                            RandomHelper.setBalloonColor(mBalloon);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBalloon.isCurSelected()) {
            mBalloon.setSelected(true);
        }
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
