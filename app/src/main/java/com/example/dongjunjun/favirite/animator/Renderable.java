package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by dongjunjun on 2017/12/12.
 */

public abstract class Renderable {

    protected float x, y;
    protected float translationX, translationY;

    protected Paint paint;

    protected int priority;//根据优先度来显示

    public Renderable(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPaint(Paint paint){
        this.paint = paint;
    }

    public Paint getPaint(){
        return paint;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    protected abstract void draw(Canvas canvas);

    public void destroy() {
    }

    protected void update(){

    }
}
