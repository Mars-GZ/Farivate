package com.example.dongjunjun.favirite.animator;

import android.content.Context;

/**
 * Created by dongjunjun on 2017/12/13.
 */

public class BalloonViewLifeCallBack implements ViewLifeCallBack<BalloonView> {

    @Override
    public void onCreateView(BalloonView view, Context context) {

    }

    @Override
    public void onStart(BalloonView view, Context context) {

    }

    @Override
    public void onResume(BalloonView view, Context context) {

    }

    @Override
    public void onPause(BalloonView view, Context context) {

    }

    @Override
    public void onStop(BalloonView view, Context context) {

    }

    @Override
    public void onDestroyView(BalloonView view, Context context) {
        if (view != null) {
            view.releaseResources();
        }
    }
}
