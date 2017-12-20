package com.example.dongjunjun.favirite.animator.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;

import com.example.dongjunjun.favirite.animator.Balloon;
import com.example.dongjunjun.favirite.animator.BalloonView;

import java.util.List;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.BALLOON_SELECT_DURATION;

/**
 * Created by dongjunjun on 2017/12/20.
 */

public class AnimatorHelper {

    private AnimatorSet animatorSet;

    private AnimatorHelper() {
    }

    public static AnimatorHelper getInstance() {
        return AnimatorHelperHolder.animatorHelper;
    }

    public boolean canPlayAnimator(){
        return animatorSet!=null&&!(animatorSet.isStarted()||animatorSet.isRunning());
    }

    public void balloonsPlayTogether(Balloon... balloons) {
        if (balloons == null) {
            return;
        }
        animatorSet = new AnimatorSet();
        for (Balloon balloon : balloons) {
            if (balloon != null) {
                Animator animator = balloon.getAnimator();
                if (animator != null) {
                    animatorSet.playTogether(animator);
                }
            }
        }
        animatorSet.setDuration(BALLOON_SELECT_DURATION);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    public void balloonsPlayTogether(List<BalloonView> balloons) {
        if (balloons == null) {
            return;
        }
        animatorSet = new AnimatorSet();
        for (BalloonView balloonView : balloons) {
            if (balloonView == null || balloonView.getModel() == null) {
                return;
            }
            Animator animator = balloonView.getModel().getAnimator();
            if (animator != null) {
                animatorSet.playTogether(animator);
            }
        }
        animatorSet.setDuration(BALLOON_SELECT_DURATION);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    public void cancelAllAnimator() {
        animatorSet.cancel();
    }

    private static class AnimatorHelperHolder {
        static final AnimatorHelper animatorHelper = new AnimatorHelper();
    }
}
