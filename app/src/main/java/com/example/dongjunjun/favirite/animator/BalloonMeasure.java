package com.example.dongjunjun.favirite.animator;

import android.graphics.Color;
import android.graphics.Paint;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.BIG_RADIUS;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.BOTTOM_MARGIN;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.LINE_COUNT;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.SMALL_RADIUS;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.TOP_MARGIN;

/**
 * 保存Balloon的一些尺寸值
 * Created by dongjunjun on 2017/12/21.
 */

public class BalloonMeasure {

    public int width;
    public int height;
    public int rawHeight;
    public int lines = 2;//总行数
    public int balloon_counts = 6;//气泡总个数
    private int small_margin;//气泡变小后中间的间距
    private int small_radius;//小气泡的大小
    private int big_radius;//大气泡的大小
    private int top_margin;
    private int bottom_margin;

    public static Paint backPaint;

    public static void initPaint(){
        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(Color.WHITE);
    }

    public static void releasePaint(){
        backPaint = null;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRawHeight(int rawHeight) {
        this.rawHeight = rawHeight;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void setCounts(int counts) {
        this.balloon_counts = counts;
    }

    public void calculateMargin() {
        small_radius = (int) (rawHeight * SMALL_RADIUS / 2);
        big_radius = (int) (rawHeight * BIG_RADIUS / 2);
        top_margin = (int) (rawHeight * TOP_MARGIN);
        bottom_margin = (int) (rawHeight * BOTTOM_MARGIN);
        int raw = balloon_counts / 2;//变成小球后的行数
        int height = lines * rawHeight;//总高度
        small_margin = (height - raw * 2 * small_radius - top_margin - bottom_margin) / (raw - 1);
    }

    /**
     * 点击后第i个气泡的位置
     *
     * @param i
     * @return
     */
    public int getSmallPosition(int i) {
        int raw = BalloonConstant.getRaw(i);
        int column = BalloonConstant.getColumn(i);
        int pos = -1;
        int target_column = (LINE_COUNT - 1) >> 1;//从0开始
        int target_column_v = target_column + 1;//从1开始
        if ((raw & 1) == 0) {
            //偶数行
            if ((LINE_COUNT & 1) == 0) {
                //一行为偶数个
                if (column <= target_column) {
                    pos = target_column_v * raw + column;
                } else {
                    pos = lines * target_column_v + raw * target_column_v + (column - target_column_v);
                }
            } else {
                //一行为奇数个
                int o = (raw + 1) >> 1;//前面偶数行的个数
                int e = raw - o;//前面奇数行的个数
                if (column <= target_column) {
                    pos = target_column_v * o + (target_column_v - 1) * e + column;
                } else if (column > target_column) {
                    o = (lines + 1) >> 1;
                    e = lines - o;
                    pos = target_column_v * o + (target_column_v - 1) * e;
                    o = (raw + 1) >> 1;
                    e = raw - o;
                    pos += e * target_column_v + o * (target_column_v - 1) + (column - target_column_v);
                }
            }
        } else {
            //奇数行
            if ((LINE_COUNT & 1) == 0) {
                //一行为偶数个
                if (column <= target_column) {
                    pos = target_column_v * raw + column;
                } else {
                    pos = lines * target_column_v + raw * target_column_v + (column - target_column_v);
                }
            } else {
                //一行为奇数个
                int o = (raw + 1) >> 1;//前面偶数行的个数
                int e = raw - o;//前面奇数行的个数
                if (column < target_column) {
                    pos = target_column_v * o + (target_column_v - 1) * e + column;
                } else if (column >= target_column) {
                    o = (lines + 1) >> 1;
                    e = lines - o;
                    pos = target_column_v * o + (target_column_v - 1) * e;
                    o = (raw + 1) >> 1;
                    e = raw - o;
                    pos += e * target_column_v + o * (target_column_v - 1) + (column - target_column);
                }
            }
        }
        return pos;
    }

    /**
     * 变小后气泡在左或在右
     *
     * @param pos
     * @return
     */
    public boolean isInLeft(int pos) {
        return pos < (balloon_counts >> 1);
    }

    public int getSmallTopMargin(int position) {
        if (!isInLeft(position)) {
            position = position - balloon_counts / 2;
        }
        return position * (small_margin + small_radius * 2) + top_margin;
    }

    public int getSmallLeftMargin(int pos) {
        if (isInLeft(pos)) {
            return 0;
        } else {
            return width - small_radius * 2;
        }
    }

    public int getBigLeftMargin() {
        return (width >> 1) - big_radius;
    }

    public int getBigTopMargin() {
        return (height >> 1) - big_radius;
    }

    public int getSmallRadius() {
        return small_radius;
    }

    public int getBigRadius() {
        return big_radius;
    }
}
