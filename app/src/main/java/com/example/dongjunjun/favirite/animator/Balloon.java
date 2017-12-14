package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;

/**
 * 气泡
 * Created by dongjunjun on 2017/12/12.
 */

public class Balloon extends Renderable {

    private float radius;
    private float scale = 1f;

    public Balloon(float radius, float x, float y) {
        super(x, y);
        this.radius = radius;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public float getScale(){
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
        canvas.scale(scale,scale);
        canvas.drawCircle(x, y, radius, paint);
        canvas.restore();
    }

    /**
     * 根据当前位置确定下次浮动的坐标
     */
    @Override
    protected void update() {

    }

}
