package com.example.dongjunjun.favirite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.dongjunjun.favirite.animator.BalloonContainerView;
import com.example.dongjunjun.favirite.animator.BalloonView;

public class MainActivity extends AppCompatActivity {

    BalloonContainerView mBalloonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBalloonContainer = findViewById(R.id.test);
        for (int i = 0 ; i< 6 ; i++){
            mBalloonContainer.addBalloon("这是"+i);
        }
        mBalloonContainer.post(new Runnable() {
            @Override
            public void run() {
                mBalloonContainer.startFlow();
            }
        });
    }
}
