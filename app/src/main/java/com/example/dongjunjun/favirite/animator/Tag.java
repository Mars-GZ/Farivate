package com.example.dongjunjun.favirite.animator;

import android.graphics.Canvas;

/**
 * Created by dongjunjun on 2017/12/13.
 */

public class Tag extends Renderable {

    private String text;

    public Tag(float x, float y) {
        super(x, y);
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    @Override
    protected void draw(Canvas canvas) {

    }

}
