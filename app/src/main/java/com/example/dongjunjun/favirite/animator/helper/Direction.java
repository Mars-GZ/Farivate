package com.example.dongjunjun.favirite.animator.helper;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.dongjunjun.favirite.animator.helper.Direction.EN;
import static com.example.dongjunjun.favirite.animator.helper.Direction.ES;
import static com.example.dongjunjun.favirite.animator.helper.Direction.RED_CENTER;
import static com.example.dongjunjun.favirite.animator.helper.Direction.S;
import static com.example.dongjunjun.favirite.animator.helper.Direction.W;
import static com.example.dongjunjun.favirite.animator.helper.Direction.E;
import static com.example.dongjunjun.favirite.animator.helper.Direction.N;
import static com.example.dongjunjun.favirite.animator.helper.Direction.WN;
import static com.example.dongjunjun.favirite.animator.helper.Direction.WS;

/**
 * Created by dongjunjun on 2017/12/14.
 */

@IntDef({RED_CENTER, W, N, E, S, WN, WS, EN, ES})
@Retention(RetentionPolicy.CLASS)
public @interface Direction {

    int RED_CENTER = 0x0000;//红中
    int W = 1 << 0;//西
    int N = 1 << 1;//北
    int E = 1 << 2;//东
    int S = 1 << 3;//南
    int WN = W + N;//西北
    int WS = W + S;//西南
    int EN = E + N;//东北
    int ES = E + S;//东南

}
