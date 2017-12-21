package com.example.dongjunjun.favirite.animator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.support.annotation.IntDef;

import com.example.dongjunjun.favirite.animator.helper.FlowHelper;

import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND_TO_SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NONE;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL_TO_EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL_TO_SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.SMALL;
import static com.example.dongjunjun.favirite.animator.Balloon.State.SMALL_TO_EXPAND;
import static com.example.dongjunjun.favirite.animator.helper.Direction.E;
import static com.example.dongjunjun.favirite.animator.helper.Direction.EN;
import static com.example.dongjunjun.favirite.animator.helper.Direction.ES;
import static com.example.dongjunjun.favirite.animator.helper.Direction.N;
import static com.example.dongjunjun.favirite.animator.helper.Direction.RED_CENTER;
import static com.example.dongjunjun.favirite.animator.helper.Direction.S;
import static com.example.dongjunjun.favirite.animator.helper.Direction.W;
import static com.example.dongjunjun.favirite.animator.helper.Direction.WN;
import static com.example.dongjunjun.favirite.animator.helper.Direction.WS;

/**
 * 气泡
 * Created by dongjunjun on 2017/12/12.
 */

public class Balloon extends Renderable {

    private float radius;
    private float scale = 1f;
    private int state = NONE;
    int position;//在list中的位置,和编号不一样，用来确定绘制顺序和首次判断smallPos
    int smallPos;//变小后的排列位置
    private BalloonMeasure measure;

    public Balloon(float radius, float x, float y) {
        super(x, y);
        this.radius = radius;
    }

    public void calculateState() {
        if (state == NONE) {
            state = NORMAL;
            normalRebound.set(layoutBoundary);
        }
    }

    public BalloonMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(BalloonMeasure measure) {
        this.measure = measure;
    }

    /**
     * 切割布局矩形，使其与圆贴边
     */
    public void cutLayoutRect() {
        float dx = x + translationX - radius;
        float dy = y + translationY - radius;
        layoutBoundary.offset(dx, dy);
        layoutBoundary.intersect(layoutBoundary.left, layoutBoundary.top, layoutBoundary.left + 2 * radius, layoutBoundary.top + 2 * radius);
        normalRebound.set(layoutBoundary);
    }

    public void match() {
        x = layoutBoundary.width() / 2;
        y = layoutBoundary.height() / 2;
    }

    public void reset() {
        translationY = translationX = 0;
        scale = 1f;
    }

    public int getSmallPosition() {
        return smallPos;
    }

    public void setSmallPosition(int smallPos) {
        this.smallPos = smallPos;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(translationX, translationY);
        canvas.scale(scale, scale);
        canvas.drawCircle(x, y, radius, paint);
        canvas.restore();
    }

    public boolean isCircle(float dx, float dy) {
        float l = x + translationX - radius;
        float t = y + translationY - radius;
        float r = x + translationX + radius;
        float b = y + translationY + radius;
        if (dx >= l && dx <= r && dy >= t && dy <= b) {
            return true;
        }
        return false;
    }

    public void setState(@State int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    /**
     * 根据当前位置确定下次浮动的坐标
     */
    @Override
    protected void update(Renderable... fix) {
        FlowHelper.updateFlow(this, fix);
    }

    @Override
    public int checkedLimitDirection() {
        float l = 0;
        float t = 0;
        float r = layoutBoundary.width();
        float b = layoutBoundary.height();
        float sl = x + translationX - radius;
        float st = y + translationY - radius;
        float sr = x + translationX + radius;
        float sb = y + translationY + radius;
        if (sl < l) {
            if (st < t) {
                return WN;
            }
            if (sb > b) {
                return WS;
            }
            return W;
        }
        if (sr > r) {
            if (st < t) {
                return EN;
            }
            if (sb > b) {
                return ES;
            }
            return E;
        }
        if (st < t) {
            return N;
        }
        if (sb > b) {
            return S;
        }
        return RED_CENTER;
    }

    @IntDef({NONE, NORMAL, NORMAL_TO_EXPAND, NORMAL_TO_SMALL, SMALL_TO_EXPAND, EXPAND_TO_SMALL, SMALL, EXPAND})
    @interface State {
        int NONE = -1;//初始时无值，需要主动去赋值
        int NORMAL = 0;
        int NORMAL_TO_EXPAND = 1;
        int NORMAL_TO_SMALL = 2;
        int SMALL_TO_EXPAND = 3;
        int EXPAND_TO_SMALL = 4;
        int SMALL = 5;
        int EXPAND = 6;
    }
}
