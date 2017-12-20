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
    public static int HORIZONTAL_MARGIN = 30;//初始气泡的左右间隔

    //气泡浮动相关
    public static float STEP = 1f;//每次浮动气泡的位移
    public static int FLOW_MAX = 10;//气泡浮动边界限定
    public static int FLOW_DELAY = 60;//两次浮动之间的时间间隔

    //显示个数相关
    public static final int BALLOON_CAPACITY = 10;//气泡的初始容量
    public static final int TAG_CAPACITY = 6;//标签的初始容量
    public static int LINE_COUNT = 3;//每行气泡的个数

    //标签大小相关
    public static final int TAG_SIZE = 30;

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
}
