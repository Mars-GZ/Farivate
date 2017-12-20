package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.IntDef;

import com.example.dongjunjun.favirite.animator.helper.FlowHelper;

import static com.example.dongjunjun.favirite.animator.Balloon.State.ANIM;
import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NONE;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.FLOW_MAX;
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

    private Balloon left;
    private Balloon top;
    private Balloon right;
    private Balloon bottom;

    private int state = NONE;

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

    public Balloon getLeft() {
        return left;
    }

    public void setLeft(Balloon left) {
        this.left = left;
    }

    public Balloon getTop() {
        return top;
    }

    public void setTop(Balloon top) {
        this.top = top;
    }

    public Balloon getRight() {
        return right;
    }

    public void setRight(Balloon right) {
        this.right = right;
    }

    public Balloon getBottom() {
        return bottom;
    }

    public void setBottom(Balloon bottom) {
        this.bottom = bottom;
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

    private void setState(@State int state) {
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
        FlowHelper.updateFlow(this,fix);
    }

    @Override
    public int checkedLimitDirection() {
        float l = 0;
        float t = 0;
        float r = layoutBoundary.width();
        float b = layoutBoundary.height();
        int sl = (int) (x + translationX - radius + 0.5);
        int st = (int) (y + translationY - radius + 0.5);
        int sr = (int) (x + translationX + radius + 0.5);
        int sb = (int) (y + translationY + radius + 0.5);
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

    @IntDef({NONE, NORMAL, EXPAND, ANIM})
    @interface State {
        int NONE = -1;//初始时无值，需要主动去赋值
        int NORMAL = 0;
        int EXPAND = 1;
        int ANIM = 2;
    }
}
