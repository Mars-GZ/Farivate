package com.example.dongjunjun.favirite.animator.helper;

import android.graphics.Paint;
import android.text.TextUtils;

import com.example.dongjunjun.favirite.animator.Balloon;
import com.example.dongjunjun.favirite.animator.Renderable;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.TAG_TEXT_SIZE;

/**
 * Created by dongjunjun on 2017/12/27.
 */

public class TextHelper {

    public static String processBalloonText(String text, Balloon balloon) {
        String result = "";
        if (!TextUtils.isEmpty(text)) {
            if (balloon != null && balloon.getChildren() != null && balloon.getChildren().size() > 0) {
                Renderable child = balloon.getChildren().get(0);
                Paint paint = child.getPaint();
                float minWidth = balloon.getMeasure().getSmallRadius() * 2;
                return processText(text, paint, minWidth);
            }
        }
        return result;
    }

    public static String processText(String text, Paint paint, float minWidth) {
        String result = "";
        if (!TextUtils.isEmpty(text)) {
            result = text;
            if (paint == null) {
                paint = new Paint();
                paint.setTextSize(TAG_TEXT_SIZE);
            }
            float width = paint.measureText(text);
            if (width > minWidth) {
                return processText(text.substring(0, text.length() - 1), paint, minWidth);
            }
        }
        return result;
    }
}
