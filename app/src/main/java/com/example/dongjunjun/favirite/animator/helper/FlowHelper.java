package com.example.dongjunjun.favirite.animator.helper;

import android.content.Context;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.dongjunjun.favirite.animator.Renderable;

/**
 * 用来辅助Renderable进行浮动
 * Created by dongjunjun on 2017/12/14.
 */

public class FlowHelper {

    static Scroller mScroller;

    private static int step = 10;

    public static void init(Context context) {
        mScroller = new Scroller(context, new LinearInterpolator(), false);
    }

    /**
     * 自身进行浮动操作
     *
     * @param renderable
     */
    public static void updateFlow(Renderable renderable) {
        if (renderable==null){
            return;
        }
        int left = (int) (Math.random() * 2);
        int top = (int) (Math.random() * 2);
        if (left == 0) {
            renderable.setTranslationX(renderable.getTranslationX() - step);
        } else {
            renderable.setTranslationX(renderable.getTranslationX() + step);
        }
        if (top == 0) {
            renderable.setTranslationY(renderable.getTranslationY() - step);
        } else {
            renderable.setTranslationY(renderable.getTranslationY() + step);
        }
        checkInContent(renderable);
    }

    /**
     * 检查超出边界
     *
     * @param renderable
     * @return
     */
    private static void checkInContent(Renderable renderable) {
        if (renderable == null) {
            return;
        }
        float translationX = renderable.getTranslationX();
        float translationY = renderable.getTranslationY();
        float x = renderable.getX();
        float y = renderable.getY();
        RectF rectF = renderable.getBoundary();
        float horizontal = x+translationX;
        float vertical = y+translationY;
        if (horizontal<rectF.left){
            translationX+=2*step;
        }else if (horizontal>rectF.right){
            translationX-=2*step;
        }
        if (vertical<rectF.top){
            translationY+=2*step;
        }else if (vertical>rectF.bottom){
            translationY-=2*step;
        }
        renderable.setTranslationX(translationX);
        renderable.setTranslationY(translationY);
    }

    /**
     * 在父布局中进行浮动操作
     *
     * @param renderable
     */
    public static void updateFlowFromParent(Renderable renderable) {

    }
}
