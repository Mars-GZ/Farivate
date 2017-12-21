package com.example.dongjunjun.favirite.animator.helper;

import android.content.Context;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.dongjunjun.favirite.animator.Renderable;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.STEP;
import static com.example.dongjunjun.favirite.animator.helper.Direction.E;
import static com.example.dongjunjun.favirite.animator.helper.Direction.N;
import static com.example.dongjunjun.favirite.animator.helper.Direction.S;
import static com.example.dongjunjun.favirite.animator.helper.Direction.W;

/**
 * 用来辅助Renderable进行浮动
 * Created by dongjunjun on 2017/12/14.
 */

public class FlowHelper {

    private static final String TAG = "FlowHelper";

    static Scroller mScroller;

    public static void init(Context context) {
        mScroller = new Scroller(context, new LinearInterpolator(), false);
    }

    /**
     * 自身进行浮动操作
     *
     * @param renderable
     */
    public static void updateFlow(Renderable renderable,Renderable... fix) {
        if (renderable == null) {
            return;
        }
        int direction = RandomHelper.getDirection(renderable);
        if (renderable.getDirection() != direction) {
            renderable.setDirection(direction);
        }
        if ((direction & N) != 0) {
            renderable.addTranslationY(-STEP);
        } else if ((direction & S) != 0) {
            renderable.addTranslationY(STEP);
        }
        if ((direction & E) != 0) {
            renderable.addTranslationX(STEP);
        } else if ((direction & W) != 0) {
            renderable.addTranslationX(-STEP);
        }
        if (fix!=null){
            for (Renderable renderable1:fix){
                renderable1.setTranslationX(renderable.getTranslationX());
                renderable1.setTranslationY(renderable.getTranslationY());
            }
        }
    }

    /**
     * 检查是否在边界内
     *
     * @param renderable
     * @return
     */
    private static boolean checkedInLimit(Renderable renderable) {
        if (renderable == null) {
            return false;
        }
        return renderable.checkedInLimit();
    }

    /**
     * 在父布局中进行浮动操作
     *
     * @param renderable
     */
    public static void updateFlowFromParent(Renderable renderable) {

    }
}
