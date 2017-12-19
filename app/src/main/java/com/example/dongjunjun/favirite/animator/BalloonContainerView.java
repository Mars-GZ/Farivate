package com.example.dongjunjun.favirite.animator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dongjunjun.favirite.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.dongjunjun.favirite.animator.Balloon.State.NONE;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.BALLOON_CAPACITY;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.EVEN_TOP;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.FLOW_MAX;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.INIT_RADIUS;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.LINE_COUNT;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.ODD_TOP;

/**
 * 盛放Balloon的容器View
 * 移除Balloon时会更新布局，
 * 若在绘制完成后再添加Balloon，需手动更新布局
 * 动态改变气泡的数量需谨慎
 * Created by dongjunjun on 2017/12/12.
 */

public class BalloonContainerView extends FrameLayout {

    private static final String TAG = "BalloonContainerView";

    private List<BalloonView> mBalloons = new ArrayList<>(BALLOON_CAPACITY);
    private BalloonViewLifeCallBack mBalloonCallBack;
    private BalloonHandlerThread mHandlerThread;

    private int rawHeight;//一行的高度

    FlowRunnable flowRunnable = new FlowRunnable();

    public BalloonContainerView(Context context) {
        super(context);
        initData();
    }

    public BalloonContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initAttrs(attrs);
    }

    private void initData() {
        mBalloonCallBack = new BalloonViewLifeCallBack();
        mHandlerThread = new BalloonHandlerThread(this, TAG);
    }

    @Override
    public void bringToFront() {
        super.bringToFront();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BalloonContainerView);
            int N = array.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.BalloonContainerView_raw_height:
                        setRawHeight(array.getDimensionPixelSize(attr, 0));
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
            mBalloons.add(balloonView);
            addView(balloonView);
        }
    }

    public void removeBalloon(BalloonView balloonView) {
        synchronized (mBalloons) {
            int key = getIndexBalloon(balloonView);
            if (key != -1) {
                mBalloons.remove(key);
            }
        }
    }

    public void removeBalloon(int i) {
        synchronized (mBalloons) {
            if (i >= 0 && i < mBalloons.size()) {
                mBalloons.remove(i);
            }
        }
    }

    public void removeAll() {
        synchronized (mBalloons) {
            mBalloons.clear();
        }
    }

    public void changeBalloonToFront(BalloonView balloonView) {
        synchronized (mBalloons) {
            int index = mBalloons.indexOf(balloonView);
            if (index != -1) {
                mBalloons.remove(index);
                mBalloons.add(balloonView);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void startThread() {
        if (mHandlerThread != null) {
            mHandlerThread.start();
            mHandlerThread.initHandler();
            mHandlerThread.sendFlowRequest();
        }
    }

    public void startFlow() {
        if (mHandlerThread != null) {
            startThread();
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
            int childWidthMeasureSpec = widthSize / LINE_COUNT;
            int childHeightMeasureSpec = (int) (rawHeight * INIT_RADIUS);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child instanceof BalloonView) {
                    Balloon balloon = ((BalloonView) child).getModel();
                    if (balloon.getState() == NONE) {
                        child.measure(MeasureSpec.makeMeasureSpec(childHeightMeasureSpec, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeightMeasureSpec, MeasureSpec.EXACTLY));
                    } else {
                        child.measure(MeasureSpec.makeMeasureSpec(balloon.getLayoutBoundary().width(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(balloon.getLayoutBoundary().height(), MeasureSpec.EXACTLY));
                    }
                }
            }
        }
        setMeasuredDimension(widthSize, getRawCount() * rawHeight);
    }

    private int getRawCount() {
        int count = mBalloons.size();
        return count == 0 ? 0 : BalloonConstant.getRaw(count) + 1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChild();
    }

    private void layoutChild() {
        int size = mBalloons.size();
        int width = getMeasuredWidth();
        int balloonWidth = width / LINE_COUNT;
        int offsetX = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            BalloonView balloonView = mBalloons.get(i);
            Balloon balloon = balloonView.getModel();
            if (balloon.getState() == NONE) {
                if (offsetX == Integer.MIN_VALUE) {
                    offsetX = (balloonWidth - balloonView.getMeasuredWidth()) / 2;
                }
                initChildrenLayout(i, balloonView, balloonWidth, offsetX);
            }
            Rect layout = balloon.getLayoutBoundary();
            balloonView.layout(layout.left, layout.top, layout.right, layout.bottom);
        }
    }

    @Override
    public void bringChildToFront(View child) {
        if (child instanceof BalloonView) {
            changeBalloonToFront((BalloonView) child);
        }
        super.bringChildToFront(child);
    }

    private int getIndexBalloon(BalloonView child) {
        return mBalloons.indexOf(child);
    }

    private void initChildrenLayout(int i, BalloonView balloonView, int balloonWidth, int offsetX) {
        if (mBalloons.size() == 0) {
            return;
        }
        Balloon balloon = balloonView.getModel();
        int raw = BalloonConstant.getRaw(i);//行数
        int column = BalloonConstant.getColumn(i);//列数
        int offsetY;
        if ((column & 1) != 0) {
            //奇数行
            offsetY = (int) (rawHeight * ODD_TOP);
            balloon.setLayoutBoundary(column * balloonWidth + offsetX, raw * rawHeight + offsetY, (column + 1) * balloonWidth + offsetX, (raw + 1) * rawHeight + offsetY);
            Rect rect = balloon.getLayoutBoundary();
            balloon.setBoundary(rect.left-FLOW_MAX,0,rect.right+FLOW_MAX,rect.bottom+2*FLOW_MAX);
        } else {
            //偶数行
            offsetY = (int) (rawHeight * EVEN_TOP);
            balloon.setLayoutBoundary(column * balloonWidth + offsetX, raw * rawHeight + offsetY, (column + 1) * balloonWidth + offsetX, (raw + 1) * rawHeight + offsetY);
            Rect rect = balloon.getLayoutBoundary();
            balloon.setBoundary(rect.left-FLOW_MAX,rect.top-FLOW_MAX,rect.right+FLOW_MAX,rect.bottom+FLOW_MAX);
        }
    }

    /**
     * 更新浮动坐标,非UI线程
     */
    public void updateFlow() {
        synchronized (mBalloons) {
            for (int i = 0; i < mBalloons.size(); i++) {
                BalloonView balloon = mBalloons.get(i);
                balloon.updateFlow();
            }
        }
    }

    public void invalidateChildren() {
        post(flowRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        removeCallbacks(flowRunnable);
    }

    class FlowRunnable implements Runnable {

        @Override
        public void run() {
            updateFlow();
            requestLayout();
            postInvalidate();
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

        public void initHandler() {
            mFlowHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case FLOW:
                            if (mContainerView != null) {
                                mContainerView.invalidateChildren();
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
