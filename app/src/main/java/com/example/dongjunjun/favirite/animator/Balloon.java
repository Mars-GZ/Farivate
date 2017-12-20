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
    protected void update() {
        FlowHelper.updateFlow(this);
    }

    @Override
    public boolean checkedInLimit() {
        return boundary.contains(layoutBoundary);
    }

    @Override
    public int checkedLimitDirection() {
        float l = layoutBoundary.left;
        float t = layoutBoundary.top;
        float r = layoutBoundary.right;
        float b = layoutBoundary.bottom;
        if (l<boundary.left){
            if (t<boundary.top){
                return WN;
            }
            if (b>boundary.bottom){
                return WS;
            }
            return W;
        }
        if (r>boundary.right){
            if (t<boundary.top){
                return EN;
            }
            if (b>boundary.bottom){
                return ES;
            }
            return E;
        }
        if (t<boundary.top){
            return N;
        }
        if (b>boundary.bottom){
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
