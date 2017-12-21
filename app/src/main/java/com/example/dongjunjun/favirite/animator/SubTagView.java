package com.example.dongjunjun.favirite.animator;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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

    public SubTagView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SubTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SubTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SubTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        canvas.drawText(subTag.getText(), (subTag.getCenterRect().getWidth())/2+ subTag.getCenterRect().getLeft(), textCenterVerticalBaselineY, mTextPaint);
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
}
