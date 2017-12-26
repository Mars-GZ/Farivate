package com.example.dongjunjun.favirite.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dongjunjun on 2017/12/14.
 */

public class SubTagView extends View {

    Paint mBgCenterPaint;
    Paint mTextPaint;
    Paint mBgOtherPaint;

    RectF rectCenter;
    RectF rectRight;
    SubTag subTag;

    GestureDetectorCompat mGestureCompat;


    public SubTagView(Context context) {
        super(context);
        init();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SubTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SubTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SubTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        //setClipChildren(false);
        mGestureCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTextPaint = subTag.getTextPaint();
        mBgCenterPaint = subTag.getBgCenterPaint();
        mBgOtherPaint = subTag.getBgOtherPaint();

        rectCenter = subTag.getCenterRect().toRect();
        rectRight = subTag.getRightRect().toRect();


        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(subTag.getLeftBall().getX(), subTag.getLeftBall().getY(), subTag.getLeftBall().getR(), mBgOtherPaint);
        canvas.drawRoundRect(rectRight, subTag.getRightRect().getR(), subTag.getRightRect().getR(), mBgOtherPaint);
        mBgCenterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawRoundRect(rectCenter, subTag.getCenterRect().getR(), subTag.getCenterRect().getR(), mBgCenterPaint);
        mBgCenterPaint.setXfermode(null);
        canvas.restoreToCount(saved);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float textCenterVerticalBaselineY = subTag.getCenterRect().getHeight() / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2;
        canvas.drawText(subTag.getText(), (subTag.getCenterRect().getWidth()) / 2 + subTag.getCenterRect().getLeft(), textCenterVerticalBaselineY, mTextPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setSubTag(SubTag subTag) {
        this.subTag = subTag;
        invalidate();
    }

    public SubTag getSubTag() {
        return subTag;
    }

    public ValueAnimator getAlphaTo0Animator(final SubTagView target, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);

        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private SubTag tagInfo = target.getSubTag();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float fraction = animation.getAnimatedFraction();
                tagInfo.getBgOtherPaint().setAlpha(0);
                tagInfo.getBgCenterPaint().setAlpha((int) (-255 * fraction + 255));
                tagInfo.getTextPaint().setAlpha((int) (-255 * fraction + 255));
                target.invalidate();
            }
        });

        return valueAnimator;
    }

    public ValueAnimator getAlphaTo255Animator(final SubTagView target, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);

        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private SubTag tagInfo = target.getSubTag();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                target.setVisibility(VISIBLE);

                float fraction = animation.getAnimatedFraction();
                tagInfo.getBgOtherPaint().setAlpha((int) (255 * fraction));
                tagInfo.getBgCenterPaint().setAlpha((int) (255 * fraction));
                tagInfo.getTextPaint().setAlpha((int) (255 * fraction));
                target.invalidate();
            }
        });

        return valueAnimator;
    }

    public ValueAnimator getTranslateAnimator(final SubTagView target, boolean left, int duration) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        final int end;
        if (left) {
            end = (int) (-0.375 * BalloonMeasure.getBigRadius());
        } else {
            end = (int) (0.375 * BalloonMeasure.getBigRadius());
        }
        valueAnimator.setDuration(duration);


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private SubTag tagInfo = target.getSubTag();

            Shader shader = new LinearGradient(
                    0,
                    0,
                    (int) (BalloonMeasure.getBigRadius() * 1.0625),
                    (int) (BalloonMeasure.getBigRadius() * 0.25),
                    Color.parseColor("#54c4ff"),
                    Color.parseColor("#3d73ff"),
                    Shader.TileMode.CLAMP);

            int targetOldLeft = target.getLeft();
            int targetOldRight = target.getRight();
            int targetOldTop = target.getTop();
            int targetOldBottom = target.getBottom();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float fraction = animation.getAnimatedFraction();
                if (fraction <= 0.5) {
                    if (fraction < 0.25) {
                        tagInfo.getBgOtherPaint().setAlpha(0);
                        tagInfo.getBgCenterPaint().setAlpha((int) (255 - fraction * 1020));
                        tagInfo.getTextPaint().setAlpha((int) (255 - fraction * 1020));
                    } else {
                        tagInfo.getBgCenterPaint().setShader(shader);
                        tagInfo.getBgOtherPaint().setShader(shader);
                        tagInfo.getTextPaint().setColor(Color.parseColor("#ffffff"));
                        tagInfo.getBgCenterPaint().setAlpha((int) (fraction * 1020 - 255));
                        tagInfo.getTextPaint().setAlpha((int) (fraction * 1020 - 255));
                    }

                    target.layout((int) (targetOldLeft + fraction * 2 * end), targetOldTop, (int) (targetOldRight + fraction * 2 * end), targetOldBottom);

                } else {
                    tagInfo.getBgOtherPaint().setAlpha(255);
                    tagInfo.getLeftBall().setX((int)(tagInfo.getCenterRect().getLeft() + BalloonMeasure.getBigRadius()*0.125 - fraction * BalloonMeasure.getBigRadius()*0.375 + BalloonMeasure.getBigRadius()*0.1875));
                    tagInfo.getRightRect().setLeft((int)(tagInfo.getCenterRect().getLeft() + BalloonMeasure.getBigRadius()*0.4375 + BalloonMeasure.getBigRadius()*0.375 * fraction - BalloonMeasure.getBigRadius()*0.1875));

                }
                target.invalidate();
            }
        });

        return valueAnimator;
    }

    public ValueAnimator getRetractionAnimator(final SubTagView target, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);

        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private SubTag tagInfo = target.getSubTag();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float fraction = animation.getAnimatedFraction();
                tagInfo.getLeftBall().setX((int)(tagInfo.getCenterRect().getLeft() - BalloonMeasure.getBigRadius()*0.0625 + fraction * BalloonMeasure.getBigRadius()*0.1875));
                tagInfo.getRightRect().setLeft((int)(tagInfo.getCenterRect().getLeft() + BalloonMeasure.getBigRadius()*0.625 - BalloonMeasure.getBigRadius()*0.1875 * fraction));
                target.invalidate();
            }
        });

        return valueAnimator;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureCompat.onTouchEvent(event)) {
            if (!this.getSubTag().isSelected()) {
                final SubTag subTag = this.getSubTag();

                //还有些别的什么逻辑
                int index = this.getSubTag().getIndex();
                subTag.isSelected = true;
                if (index == 2 || index == 4) {
                    ValueAnimator animator = getTranslateAnimator(this, false,  (int) BalloonConstant.SUBTAG_TRANSLATE_DURATION);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });
                    animator.start();
                } else {
                    ValueAnimator animator = getTranslateAnimator(this, true, (int) BalloonConstant.SUBTAG_TRANSLATE_DURATION);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //subTag.isSelected = true;
                        }
                    });
                    animator.start();
                }
                this.setClickable(false);
                return true;
            }
        }
        return true;

    }
}
