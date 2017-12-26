package com.example.dongjunjun.favirite.animator.helper;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.example.dongjunjun.favirite.animator.Balloon;
import com.example.dongjunjun.favirite.animator.BalloonConstant;
import com.example.dongjunjun.favirite.animator.MajorTag;
import com.example.dongjunjun.favirite.animator.Renderable;
import com.example.dongjunjun.favirite.animator.Tag;

import java.util.List;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.TAG_TEXT_SIZE;
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
 * 处理各类随机事件(颜色随机、浮动随机)
 * Created by dongjunjun on 2017/12/15.
 */

public class RandomHelper {

    //气泡浮动方向
    static final int[] directions = new int[]{N, S, E, W, WN, WS, EN, ES};

    //气泡颜色
    public static final int[][] color = new int[][]{{0xffffdb00, 0xffffa400}, {0xff00daae, 0xff1ddb1a},
            {0xff54c4ff, 0xff3d73ff}, {0xffffaa5a, 0xffff566f}};

    //文字颜色
    static final int[] text_color = new int[]{0xffffcb15, 0xff04da00, 0xff2faefe, 0xffff4e56};

    /**
     * 获取本次浮动方向
     *
     * @param renderable
     * @return
     */
    public static int getDirection(Renderable renderable) {
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
     * 气泡的颜色随机
     *
     * @param balloon
     * @return
     */
    public static void setBalloonColor(Balloon balloon) {
        if (balloon == null) {
            return;
        }
        boolean isSelected = balloon.isSelected();
        Paint paint = balloon.getPaint();
        int pos = getColorPosition(balloon);
        if (isSelected) {
            float x = balloon.getX();
            float y = balloon.getY();
            float r = balloon.getRadius();
            LinearGradient gradient = new LinearGradient(x - r, y - r, x + r, y + r,
                    color[pos], null, Shader.TileMode.MIRROR);
            paint.setShader(gradient);
        } else {
            paint.setColor(Color.WHITE);
            paint.setShader(null);
        }
    }

    public static int getColorPosition(Balloon balloon) {
        if (balloon == null) {
            return 0;
        }
        int num = balloon.getNum();
        int raw = BalloonConstant.getRaw(num) & 1;
        int column = BalloonConstant.getColumn(num) & 1;
        int pos = 0;
        if (raw == 1) {
            pos++;
        }
        pos += raw + column;
        return pos;
    }

    /**
     * 文字的颜色随机
     *
     * @param balloon
     * @return
     */
    public static void setTextColor(Balloon balloon) {
        if (balloon == null) {
            return;
        }
        int pos = getColorPosition(balloon);
        List<Renderable> children = balloon.getChildren();
        if (children == null) {
            return;
        }
        for (Renderable child : children) {
            if (child instanceof Tag) {
                boolean isSelected = child.isSelected();
                Tag tag = (Tag) child;
                if (tag == null) {
                    continue;
                }
                Paint tagPaint;
                if (tag.getPaint() == null) {
                    tagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                } else {
                    tagPaint = tag.getPaint();
                }
                tagPaint.setTextSize(TAG_TEXT_SIZE);
                tagPaint.setTextAlign(Paint.Align.CENTER);
                if (isSelected) {
                    tagPaint.setColor(Color.WHITE);
                } else {
                    tagPaint.setColor(text_color[pos]);
                }
                tag.setPaint(tagPaint);
            }
        }
    }

    public static void setTagTextColor(Tag tag) {
        boolean isSelected = tag.isSelected();
        int pos = getColorPosition((Balloon) tag.getParent());
        if (tag == null) {
            return;
        }
        Paint tagPaint;
        if (tag.getPaint() == null) {
            tagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            tagPaint = tag.getPaint();
        }
        tagPaint.setTextSize(TAG_TEXT_SIZE);
        tagPaint.setTextAlign(Paint.Align.CENTER);
        if (isSelected) {
            tagPaint.setColor(Color.WHITE);
        } else {
            tagPaint.setColor(text_color[pos]);
        }
        tag.setPaint(tagPaint);
    }

    public static void setMajorTagBackColor(MajorTag tag, Paint paint) {
        if (tag == null || paint == null) {
            return;
        }
        int pos = getColorPosition((Balloon) tag.getParent());
        RectF rectF = tag.getBack();
        LinearGradient gradient = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom,
                color[pos], null, Shader.TileMode.MIRROR);
        paint.setShader(gradient);
    }
}
