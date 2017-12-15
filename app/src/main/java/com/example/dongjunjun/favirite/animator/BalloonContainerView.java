package com.example.dongjunjun.favirite.animator;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dongjunjun.favirite.R;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.BIG_BALLOON_PROCENT;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.LINE_COUNT;

/**
 * 盛放Balloon的容器View
 * 移除Balloon时会更新布局，若在绘制完成后
 * 再添加Balloon，需手动更新布局
 * 动态改变气泡的数量需谨慎
 * Created by dongjunjun on 2017/12/12.
 */

public class BalloonContainerView extends FrameLayout {

    private static final String TAG = "BalloonContainerView";

    private SparseArray<BalloonView> mBalloons;
    private BalloonViewLifeCallBack mBalloonCallBack;
    private BalloonHandlerThread mHandlerThread;
    private BalloonView mSelectedBalloonView;

    private int rawHeight;//一行的高度


    public BalloonContainerView(Context context) {
        super(context);
        initData();
    }

    public BalloonContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initAttrs(attrs);
    }

    public BalloonContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initAttrs(attrs);
    }

    private void initData() {
        mBalloonCallBack = new BalloonViewLifeCallBack();
        mHandlerThread = new BalloonHandlerThread(this, TAG);
        mBalloons = new SparseArray<>(10);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BalloonContainerView);
            int N = array.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.BalloonContainerView_row_height:
                        setRawHeight(array.getInteger(attr, 0));
                        break;
                }
            }
        }
    }

    public void setRawHeight(int rawHeight) {
        this.rawHeight = rawHeight;
    }

    public void addBalloon(String text) {
        addBalloon(mBalloons.size(), text);
    }

    public void addBalloon(int i, String text) {
        BalloonView balloonView = new BalloonView(getContext());
        balloonView.setText(text);
        addBalloon(i, balloonView);
    }

    public void addBalloon(BalloonView balloonView) {
        addBalloon(mBalloons.size(), balloonView);
    }

    public void addBalloon(int i, BalloonView balloonView) {
        synchronized (mBalloons) {
            balloonView.getModel().setNum(i);
            mBalloons.put(i, balloonView);
        }
    }

    public void removeBalloon(BalloonView balloonView) {
        synchronized (mBalloons) {
            int key = mBalloons.indexOfValue(balloonView);
            if (key != -1) {
                mBalloons.remove(key);
                requestLayout();
                invalidate();
            }
        }
    }

    public void removeAll() {
        synchronized (mBalloons) {
            mBalloons.clear();
        }
        requestLayout();
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void startThread() {
        if (mHandlerThread != null && mHandlerThread.isAlive()) {
            mHandlerThread.start();
        }
    }

    public void startFlow() {
        if (mHandlerThread != null) {
            startThread();
            mHandlerThread.sendFlowRequest();
        }
    }

    public void stopFlow() {
        if (mHandlerThread != null) {
            mHandlerThread.stopFlow();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        handleHorizontalMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void handleHorizontalMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == 0 || heightMode == 0) {
            widthSize = 0;
            heightSize = 0;
            setMeasuredDimension(widthSize, heightSize);
        } else {
            int childWidthMeasureSpec = 0;
            int childHeightMeasureSpec = 0;
            if (mSelectedBalloonView == null) {
                childWidthMeasureSpec = widthSize / LINE_COUNT;
                childHeightMeasureSpec = rawHeight;
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    child.measure(MeasureSpec.makeMeasureSpec(childWidthMeasureSpec, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeightMeasureSpec, MeasureSpec.EXACTLY));
                }
            } else {

                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    if (child == mSelectedBalloonView) {
                        int size = (int) (heightSize * BIG_BALLOON_PROCENT);
                        child.measure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
                    } else {
                        child.measure(MeasureSpec.makeMeasureSpec(childWidthMeasureSpec, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeightMeasureSpec, MeasureSpec.EXACTLY));
                    }
                }
            }
            setMeasuredDimension(getMeasuredWidth(), getRaw() * rawHeight);
        }
    }

    private int getRaw() {
        int count = mBalloons.size();
        return count == 0 ? 0 : count % LINE_COUNT == 0 ? count / LINE_COUNT : count / LINE_COUNT + 1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int raw = getRaw();
        for (int i = 0; i < raw; i++) {
            handleHorizontalLayout(raw);
        }
    }

    /**
     * @param raw 当前行数
     */
    private void handleHorizontalLayout(int raw) {
        if (mBalloons.size() == 0) {
            return;
        }
        int width = getWidth();
        int count = mBalloons.size();
        int start = raw * count;
        int end = Math.min(start + LINE_COUNT - 1, count);
        int balloonWidth = width / LINE_COUNT;
        for (int i = start; i <= end; i++) {
            BalloonView balloonView = mBalloons.get(i);
            int j = i % LINE_COUNT;
            balloonView.layout(j * balloonWidth, raw * rawHeight, (j + 1) * balloonWidth, (raw + 1) * rawHeight);
        }
    }

    /**
     * 更新浮动坐标,非UI线程
     */
    public void updateFlow() {
        synchronized (mBalloons) {
            for (int i = 0; i <= mBalloons.size(); i++) {
                BalloonView balloon = mBalloons.get(i);
                balloon.updateFlow();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
    }

    private static class BalloonHandlerThread extends HandlerThread {

        private Handler mFlowHandler;
        private static final int FLOW = 1;

        private BalloonContainerView mContainerView;

        public BalloonHandlerThread(BalloonContainerView containerView, String name) {
            super(name);
            this.mContainerView = containerView;
        }

        @Override
        protected void onLooperPrepared() {
            mFlowHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case FLOW:
                            if (mContainerView != null) {
                                mContainerView.updateFlow();
                                mContainerView.postInvalidate();
                            }
                            sendFlowRequest();
                            break;
                    }
                }
            };
        }

        public void sendFlowRequest() {
            if (mFlowHandler.hasMessages(FLOW)) {
                return;
            }
            mFlowHandler.sendEmptyMessageDelayed(FLOW, 16);
        }

        public void stopFlow() {
            mFlowHandler.removeMessages(FLOW);
        }
    }
}
