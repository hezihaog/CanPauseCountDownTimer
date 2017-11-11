package com.hzh.common.view;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Hezihao on 2017/8/2.
 * 支持暂停的CountDownTimer
 */

public class CanPauseCountDownTimer {
    //默认4秒倒计时
    private final long DEFAULT_SUM_TIME = 4 * 1000;
    //默认每秒回调一次
    private final long DEFAULT_INTERVAL = 1000;

    private final Handler mainHandler;
    private CountDownTimer countDownTimer;
    private long sumTime = DEFAULT_SUM_TIME;
    private long timeInterval = DEFAULT_INTERVAL;
    private long startTime;
    //剩余的倒计时时间
    private long delayedTime;
    private OnCountDownListener listener;
    private boolean isRunning = false;

    private CanPauseCountDownTimer() {
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static CanPauseCountDownTimer newInstance() {
        return new CanPauseCountDownTimer();
    }

    /**
     * 设置倒计时总时间，会加上500ms的CountDown的偏差
     *
     * @param sumTime
     */
    public CanPauseCountDownTimer setSumTime(long sumTime) {
        this.sumTime = sumTime + 500;
        return this;
    }

    /**
     * 设置回调间隔
     *
     * @param timeInterval
     */
    public CanPauseCountDownTimer setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public void start() {
        if (isRunning && startTime > 0 && delayedTime > 0) {
            //倒计时启动后，重新启动倒计时
            countDownTimer.cancel();
            startTime = System.currentTimeMillis();
            startCountDown(delayedTime, timeInterval);
        } else {
            if (!isRunning && delayedTime == 0) {
                //第一次启动倒计时
                isRunning = true;
                startTime = System.currentTimeMillis();
                startCountDown(sumTime, timeInterval);
            }
        }
    }

    public void pause() {
        if (countDownTimer != null) {
            //记录剩余毫秒数
            delayedTime = System.currentTimeMillis() - startTime;
            countDownTimer.cancel();
        }
    }

    public void cancel() {
        isRunning = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 开启启动倒计时
     *
     * @param millisInFuture
     * @param countDownInterval
     */
    private void startCountDown(long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(final long millisUntilFinished) {
                if (listener == null) {
                    return;
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onTick(millisUntilFinished);
                    }
                });
            }

            @Override
            public void onFinish() {
                isRunning = false;
                if (listener == null) {
                    return;
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFinish();
                    }
                });
            }
        };
        countDownTimer.start();
    }

    public interface OnCountDownListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }

    public CanPauseCountDownTimer setCountDownListener(OnCountDownListener listener) {
        this.listener = listener;
        return this;
    }
}
