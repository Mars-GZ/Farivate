package com.example.dongjunjun.favirite.animator;

import android.content.Context;
import android.view.View;

/**
 * 执行View的生命周期同步
 * Created by dongjunjun on 2017/12/13.
 */

public interface ViewLifeCallBack<T extends View> {

    void onCreateView(T view, Context context);

    void onStart(T view, Context context);

    void onResume(T view, Context context);

    void onPause(T view, Context context);

    void onStop(T view, Context context);

    void onDestroyView(T view, Context context);

}
