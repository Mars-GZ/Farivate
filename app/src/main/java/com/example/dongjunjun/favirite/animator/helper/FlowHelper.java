package com.example.dongjunjun.favirite.animator.helper;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.dongjunjun.favirite.animator.Renderable;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.STEP;
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
 * 用来辅助Renderable进行浮动
 * Created by dongjunjun on 2017/12/14.
 */

public class FlowHelper {

    private static final String TAG = "FlowHelper";

    static Scroller mScroller;
    static final int[] directions = new int[]{N, S, E, W, WN, WS, EN, ES};

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
        int direction = getDirection(renderable);
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
     * 获取本次浮动方向
     *
     * @param renderable
     * @return
     */
    private static int getDirection(Renderable renderable) {
        if (renderable == null) {
            return -1;
        }
        int direction = renderable.getDirection();
        if (direction == RED_CENTER) {
            int random = (int) (Math.random() * 8);
            direction = directions[random];
        } else {
            int random;
            int[] ch;
            int dh = 0;
            int cur;
            if ((cur = renderable.checkedLimitDirection()) > 0) {
                if ((cur & W) != 0) {
                    if ((cur & N) != 0) {
                        ch = new int[2];
                        ch[1] = S;
                    } else if ((cur & S) != 0) {
                        ch = new int[2];
                        ch[1] = N;
                    } else {
                        ch = new int[3];
                        ch[1] = S;
                        ch[2] = N;
                    }
                    dh = E;
                } else if ((cur & E) != 0) {
                    if ((cur & N) != 0) {
                        ch = new int[2];
                        ch[1] = S;
                    } else if ((cur & S) != 0) {
                        ch = new int[2];
                        ch[1] = N;
                    } else {
                        ch = new int[3];
                        ch[1] = S;
                        ch[2] = N;
                    }
                    dh = W;
                } else {
                    ch = new int[3];
                    ch[1] = W;
                    ch[2] = E;
                    if ((cur & N) != 0) {
                        dh = S;
                    } else if ((cur & S) != 0) {
                        dh = N;
                    }
                }
                random = (int) (Math.random() * ch.length);
                direction = dh | ch[random];
            }
        }
        return direction;
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
