package com.example.dongjunjun.favirite.animator.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;

import com.example.dongjunjun.favirite.animator.Balloon;
import com.example.dongjunjun.favirite.animator.BalloonView;

import java.util.List;

import static com.example.dongjunjun.favirite.animator.BalloonConstant.BALLOON_EXCHANGE_DELAY;
import static com.example.dongjunjun.favirite.animator.BalloonConstant.BALLOON_SELECT_DURATION;

/**
 * Created by dongjunjun on 2017/12/20.
 */

public class AnimatorHelper {

    private AnimatorSet animatorSet;
    private Animator exchangeAnimator;

    private AnimatorHelper() {
    }

    public static AnimatorHelper getInstance() {
        return AnimatorHelperHolder.animatorHelper;
    }

    public boolean canPlayAnimator() {
        return animatorSet == null || (animatorSet != null && !(animatorSet.isStarted() || animatorSet.isRunning()));
    }

    public void balloonsPlayTogether(BalloonView... balloons) {
        if (balloons == null) {
            return;
        }
        animatorSet = new AnimatorSet();
        for (BalloonView balloon : balloons) {
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
        int size = balloons.size();
        for (int i = 0; i < size; i++) {
            BalloonView balloonView = balloons.get(i);
            if (balloonView == null || balloonView.getModel() == null) {
                return;
            }
            Animator animator = balloonView.getAnimator();
            if (animator != null) {
                animatorSet.playTogether(animator);
            }
        }
        animatorSet.setDuration(BALLOON_SELECT_DURATION);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    /**
     * 播放交换动画(从中间变小的气泡动画)
     */
    public void playExchangeAnimator(BalloonView balloonView) {
        if (balloonView == null) {
            return;
        }
        exchangeAnimator = balloonView.getAnimator();
        exchangeAnimator.setDuration(BALLOON_SELECT_DURATION - BALLOON_EXCHANGE_DELAY);
        exchangeAnimator.setStartDelay(BALLOON_EXCHANGE_DELAY);
        exchangeAnimator.start();
    }

    public void cancelAllAnimator() {
        if (isRunning(animatorSet)) {
            animatorSet.cancel();
        }
        if (isRunning(exchangeAnimator)) {
            exchangeAnimator.cancel();
        }
    }

    public boolean isRunning(Animator animator) {
        return animator != null && (animator.isStarted() || animator.isRunning());
    }

    private static class AnimatorHelperHolder {
        static final AnimatorHelper animatorHelper = new AnimatorHelper();
    }
}
