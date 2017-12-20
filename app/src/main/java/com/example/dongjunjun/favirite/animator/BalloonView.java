package com.example.dongjunjun.favirite.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dongjunjun.favirite.animator.event.FlowEvent;
import com.example.dongjunjun.favirite.animator.helper.AnimatorHelper;
import com.example.dongjunjun.favirite.animator.listener.BalloonItemClickListener;

import org.greenrobot.eventbus.EventBus;

import static com.example.dongjunjun.favirite.animator.Balloon.State.NONE;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.FLOW_MAX;
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
            if (AnimatorHelper.getInstance().canPlayAnimator()) {
                if (mBalloon.isCircle(x, y)) {
                    EventBus.getDefault().post(new FlowEvent(true));
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
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        mBalloon.setPaint(paint);

        //init Tag
        mMajorTag.setX(mBalloon.getX());
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(TAG_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        mMajorTag.setBaseLine((int) ((height + (mBalloon.getY() - height / 2) - paint.getStrokeWidth() - metrics.top - metrics.bottom) / 2));
        mMajorTag.setPaint(paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBalloon.draw(canvas);
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
