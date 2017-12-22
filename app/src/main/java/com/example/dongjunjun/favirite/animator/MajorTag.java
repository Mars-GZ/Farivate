package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.dongjunjun.favirite.animator.helper.RandomHelper;

import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND_TO_SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL_TO_EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.SMALL_TO_EXPAND;

/**
 * 一级标签
 * Created by dongjunjun on 2017/12/13.
 */

public class MajorTag extends Tag {

    private float textWidth;//文字宽度
    private float textHeight;//文字高度
    private float backRadius;//文字背景半径
    private RectF back = new RectF();

    public Paint backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MajorTag(float x, float y) {
        super(x, y);
    }

    public RectF getBack() {
        return back;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(translationX, translationY);
        if (isSelected) {
            if (parent instanceof Balloon) {
                int state = ((Balloon) parent).getState();
                if (state == NORMAL_TO_EXPAND || state == SMALL_TO_EXPAND
                        || state == EXPAND || state == EXPAND_TO_SMALL) {
                    float top = baseLine + (paint.getFontMetrics().top + paint.getFontMetrics().bottom) / 2;
                    back.set(x - textWidth / 2 - backRadius,
                            top - backRadius,
                            x + textWidth / 2 + backRadius, top + backRadius);
                    RandomHelper.setMajorTagBackColor(this, backPaint);
                    canvas.drawRoundRect(back,
                            backRadius, backRadius, backPaint);
                }
            }
        }
        canvas.drawText(text, x, y + baseLine, paint);
        canvas.restore();
    }

    /**
     * 计算文字相关参数
     */
    public void calculateTextData() {
        if (paint != null && text != null) {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            textHeight = fontMetrics.descent - fontMetrics.ascent;
            textWidth = paint.measureText(text);
            backRadius = textHeight * BalloonConstant.TAG_BACK_MULTI / 2;
        }
    }
}
