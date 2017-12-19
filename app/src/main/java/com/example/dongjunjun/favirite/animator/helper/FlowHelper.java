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
    public static void updateFlow(Renderable renderable) {
        if (renderable == null) {
            return;
        }
        int direction = getDirection(renderable);
        if (renderable.getDirection() != direction) {
            renderable.setDirection(direction);
        }
        Log.e(TAG, "第" + renderable.getNum() + "个气泡的方向是" + Integer.toBinaryString(direction));
        if ((direction & N) != 0) {
            renderable.getLayoutBoundary().offset(0, -STEP);
        } else if ((direction & S) != 0) {
            renderable.getLayoutBoundary().offset(0, STEP);
        }
        if ((direction & E) != 0) {
            renderable.getLayoutBoundary().offset(STEP, 0);
        } else if ((direction & W) != 0) {
            renderable.getLayoutBoundary().offset(-STEP, 0);
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
        Rect rect = renderable.getBoundary();
        if (rect == null) {
            return;
        }
        float horizontal = x + translationX;
        float vertical = y + translationY;
        if (horizontal < rect.left) {
            translationX += 2 * STEP;
        } else if (horizontal > rect.right) {
            translationX -= 2 * STEP;
        }
        if (vertical < rect.top) {
            translationY += 2 * STEP;
        } else if (vertical > rect.bottom) {
            translationY -= 2 * STEP;
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
