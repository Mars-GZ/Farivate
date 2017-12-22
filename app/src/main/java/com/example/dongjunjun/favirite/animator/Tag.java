package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by dongjunjun on 2017/12/13.
 */

public class Tag extends Renderable {

    protected String text;
    protected float baseLine;

    public Tag(float x, float y) {
        super(x, y);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void setPaint(Paint paint) {
        super.setPaint(paint);
    }

    public float getBaseLine(){
        return baseLine;
    }

    public void setBaseLine(float baseLine){
        this.baseLine = baseLine;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(translationX, translationY);
        canvas.drawText(text, x, y+baseLine, paint);
        canvas.restore();
    }

    public void match(int height){
        if (paint==null){
            return;
        }
        Paint.FontMetrics metrics = paint.getFontMetrics();
        setBaseLine((int) ((height - metrics.top - metrics.bottom) / 2));
        setX(parent.getX());
    }

    public void reset(){
        translationX = translationY = 0;
    }
}
