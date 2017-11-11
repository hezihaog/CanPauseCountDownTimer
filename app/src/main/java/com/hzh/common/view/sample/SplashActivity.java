package com.hzh.common.view.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzh.common.view.CanPauseCountDownTimer;

/**
 * Package: com.hzh.common.view.sample
 * FileName: SplashActivity
 * Date: on 2017/11/11  上午9:52
 * Auther: zihe
 * Descirbe: 启动屏
 * Email: hezihao@linghit.com
 */
public class SplashActivity extends AppCompatActivity {
    private LinearLayout skipAdLayout;
    private TextView countDownText;
    private CanPauseCountDownTimer countDownTimer;

    @Override
    protected void onStart() {
        super.onStart();
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.pause();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        skipAdLayout = (LinearLayout) findViewById(R.id.skipAdLayout);
        countDownText = (TextView) findViewById(R.id.countDownText);
        skipAdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
        countDownTimer = CanPauseCountDownTimer.newInstance();
        countDownTimer.setSumTime(4 * 1000)
                .setTimeInterval(1000)
                .setCountDownListener(new CanPauseCountDownTimer.OnCountDownListener() {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        countDownText.setText((millisUntilFinished / 1000) + "s");
                    }

                    @Override
                    public void onFinish() {
                        gotoMain();
                    }
                }).start();
    }

    @Override
    public void finish() {
        super.finish();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void gotoMain() {
        MainActivity.gotoMain(SplashActivity.this);
        finish();
    }
}
