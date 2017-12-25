package com.example.dongjunjun.favirite.animator;

/**
 * Created by dongjunjun on 2017/12/15.
 */

public class BalloonConstant {

    //气泡大小相关
    public static final float BIG_BALLOON_PERCENT = 0.64f;//选中气泡展总高度的百分比
    public static final float SMALL_BALLOON_PERCENT = 0.625f;//选中气泡后小气泡为原来的百分比
    public static final float EVEN_TOP = 30 / 210f;//偶数行中心所在y坐标百分比
    public static final float ODD_TOP = 0 / 210f;//奇数行中心所在y坐标百分比
    public static final float INIT_RADIUS = 160 / 210f;//初始气泡所占百分比大小
    public static final float SMALL_RADIUS = 100 / 210f;//小气泡所占百分比大小
    public static final float SMALL_MULTI = 100 / 160f;
    public static final float BIG_RADIUS = 320 / 210F;//大气泡所占百分比大小
    public static final float BIG_MULTI = 320 / 160f;
    public static final float TOP_MARGIN = 0 / 210f;//顶部气泡距顶的距离
    public static final float BOTTOM_MARGIN = 20 / 210f;//底部气泡距底的距离
    public static final int HORIZONTAL_MARGIN = 30;//初始气泡的左右间隔
    public static final int GRADIENT_ANGLE = -45;//选中气泡渐变颜色角度

    //气泡浮动相关
    public static final float STEP = 0.3f;//每次浮动气泡的位移
    public static final int FLOW_MAX = 15;//气泡浮动边界限定
    public static final int FLOW_DELAY = 16;//两次浮动之间的时间间隔

    //显示个数相关
    public static final int BALLOON_CAPACITY = 10;//气泡的初始容量
    public static final int TAG_CAPACITY = 6;//标签的初始容量
    public static final int LINE_COUNT = 4;//每行气泡的个数
    //标签大小相关
    public static final int TAG_TEXT_SIZE = 42;
    public static final float TAG_BACK_MULTI = 40 / 26f;//文字背景对文字的倍数
    //气泡动画相关
    public static final long BALLOON_SELECT_DURATION = 600;
    public static final long BALLOON_EXCHANGE_DELAY = 80;

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
     * 背景色扩展透明度函数关系式
     * <p>
     * x=0.8,y=255
     * x=1,y=0
     * y=-1275x+1275
     *
     * @param value
     * @return
     */
    public static int getExpandAlpha(float value) {
        return (int) (-1275 * value + 1275);
    }

    /**
     * 文字颜色扩展透明度函数关系式
     * <p>
     * x=0.8,y=128
     * x=1,y=255
     *
     * @param value
     * @return
     */
    public static int getExpandTextAlpha(float value) {
         return (int) (635*value-380);
    }

    /**
     * 背景色缩小透明度函数关系式
     * <p>
     * x=0,y=0
     * x=0.2,y=255
     * y=6375x^2
     *
     * @param value
     * @return
     */
    public static int getSmallAlpha(float value) {
        return (int) (6375 * value * value);
    }

    /**
     * 文字颜色缩小透明度函数关系式
     * <p>
     * x=0,y=128
     * x=0.2,y=255
     *
     * @param value
     * @return
     */
    public static int getSmallTextAlpha(float value) {
        return (int) (635*value);
    }
}
