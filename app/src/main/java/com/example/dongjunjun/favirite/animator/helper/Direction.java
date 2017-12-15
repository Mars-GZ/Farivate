package com.example.dongjunjun.favirite.animator.helper;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.dongjunjun.favirite.animator.helper.Direction.BOTTOM;
import static com.example.dongjunjun.favirite.animator.helper.Direction.IDLE;
import static com.example.dongjunjun.favirite.animator.helper.Direction.LEFT;
import static com.example.dongjunjun.favirite.animator.helper.Direction.RIGHT;
import static com.example.dongjunjun.favirite.animator.helper.Direction.TOP;

/**
 * Created by dongjunjun on 2017/12/14.
 */

@IntDef({IDLE,LEFT, TOP, RIGHT, BOTTOM})
@Retention(RetentionPolicy.CLASS)
public @interface Direction {

    int IDLE = 0;
    int LEFT = 1;
    int TOP = 2;
    int RIGHT = 3;
    int BOTTOM = 4;

}
