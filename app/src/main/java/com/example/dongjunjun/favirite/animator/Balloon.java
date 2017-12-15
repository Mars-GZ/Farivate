package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.support.annotation.IntDef;

import com.example.dongjunjun.favirite.animator.helper.FlowHelper;

import static com.example.dongjunjun.favirite.animator.Balloon.State.ANIM;
import static com.example.dongjunjun.favirite.animator.Balloon.State.EXPAND;
import static com.example.dongjunjun.favirite.animator.Balloon.State.NORMAL;

/**
 * 气泡
 * Created by dongjunjun on 2017/12/12.
 */

public class Balloon extends Renderable {

    private float radius;
    private float scale = 1f;
    private int num;//气泡编号

    private Balloon left;
    private Balloon top;
    private Balloon right;
    private Balloon bottom;

    private int state = NORMAL;

    public Balloon(float radius, float x, float y) {
        super(x, y);
        this.radius = radius;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    @IntDef({NORMAL, EXPAND, ANIM})
    @interface State {
        int NORMAL = 0;
        int EXPAND = 1;
        int ANIM = 2;
    }
}
