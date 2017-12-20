package com.example.dongjunjun.favirite.animator;

/**
 * Created by dongjunjun on 2017/12/15.
 */

public class BalloonConstant {

    //气泡大小相关
    public static float BIG_BALLOON_PERCENT = 0.64f;//选中气泡展总高度的百分比
    public static float SMALL_BALLOON_PERCENT = 0.625f;//选中气泡后小气泡为原来的百分比
    public static float EVEN_TOP = 30 / 210f;//偶数行中心所在y坐标百分比
    public static float ODD_TOP = 0 / 210f;//奇数行中心所在y坐标百分比
    public static float INIT_RADIUS = 160 / 210f;//初始气泡所占百分比大小
    public static float SMALL_RADIUS = 100 / 210f;//小气泡所占百分比大小
    public static float BIG_RADIUS = 320 / 210F;//大气泡所占百分比大小
    public static int HORIZONTAL_MARGIN = 30;//初始气泡的左右间隔

    //气泡浮动相关
    public static float STEP = 0.3f;//每次浮动气泡的位移
    public static int FLOW_MAX = 15;//气泡浮动边界限定
    public static int FLOW_DELAY = 16;//两次浮动之间的时间间隔

    //显示个数相关
    public static final int BALLOON_CAPACITY = 10;//气泡的初始容量
    public static final int TAG_CAPACITY = 6;//标签的初始容量
    public static int LINE_COUNT = 3;//每行气泡的个数
    public static int LINES = 2;//总行数

    //标签大小相关
    public static final int TAG_SIZE = 30;

    //气泡动画相关
    public static final long BALLOON_SELECT_DURATION = 1500;

    public static void setLines(int lines) {
        LINES = lines;
    }

    public static int getLines() {
        return LINES;
    }

    /**
     * 获取所在行数
     *
     * @param i
     * @return
     */
    public static int getRaw(int i) {
        return i / LINE_COUNT;
    }

    /**
     * 获取所在列数
     *
     * @param i
     * @return
     */
    public static int getColumn(int i) {
        return i % LINE_COUNT;
    }

    /**
     * 点击后第i个气泡的位置
     *
     * @param i
     * @return
     */
    public static int getSmallPosition(int i) {
        int raw = getRaw(i);
        int column = getColumn(i);
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
                    pos = LINES * target_column_v + raw * target_column_v + (column - target_column_v);
                }
            } else {
                //一行为奇数个
                int o = (raw + 1) >> 1;//前面偶数行的个数
                int e = raw - o;//前面奇数行的个数
                if (column <= target_column) {
                    pos = target_column_v * o + (target_column_v - 1) * e + column;
                } else if (column > target_column) {
                    o = (LINES + 1) >> 1;
                    e = LINES - o;
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
                    pos = LINES * target_column_v + raw * target_column_v + (column - target_column_v);
                }
            } else {
                //一行为奇数个
                int o = (raw + 1) >> 1;//前面偶数行的个数
                int e = raw - o;//前面奇数行的个数
                if (column < target_column) {
                    pos = target_column_v * o + (target_column_v - 1) * e + column;
                } else if (column >= target_column) {
                    o = (LINES + 1) >> 1;
                    e = LINES - o;
                    pos = target_column_v * o + (target_column_v - 1) * e;
                    o = (raw + 1) >> 1;
                    e = raw - o;
                    pos += e * target_column_v + o * (target_column_v - 1) + (column - target_column);
                }
            }
        }
        return pos;
    }
}
