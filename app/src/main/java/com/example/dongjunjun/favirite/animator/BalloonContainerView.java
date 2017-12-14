package com.example.dongjunjun.favirite.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 盛放Balloon的容器View
 * Created by dongjunjun on 2017/12/12.
 */

public class BalloonContainerView extends FrameLayout {

    private static final String TAG = "BalloonContainerView";

    private BalloonView[] mBalloons;
    private BalloonViewLifeCallBack mBalloonCallBack;

    private BalloonHandlerThread mHandlerThread;

    public BalloonContainerView(Context context) {
        super(context);
        init();
    }

    public BalloonContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BalloonContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBalloonCallBack = new BalloonViewLifeCallBack();
        mHandlerThread = new BalloonHandlerThread(this, TAG);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void startFlow() {
        if (mHandlerThread != null && mHandlerThread.isAlive()) {
            mHandlerThread.start();
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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i <= mBalloons.length; i++) {
            BalloonView balloon = mBalloons[i];
            balloon.draw(canvas);
        }
    }

    /**
     * 更新浮动坐标
     */
    public void updateFlow(){
        for (int i = 0; i <= mBalloons.length; i++) {
            BalloonView balloon = mBalloons[i];
            balloon.updateFlow();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
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
                            try {
                                Thread.sleep(16);//16ms执行一次
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
            mFlowHandler.sendEmptyMessage(FLOW);
        }

        public void stopFlow() {
            quit();
        }
    }
}
