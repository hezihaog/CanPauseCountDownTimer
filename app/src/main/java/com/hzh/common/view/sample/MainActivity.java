package com.hzh.common.view.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Package: com.hzh.common.view.sample
 * FileName: MainActivity
 * Date: on 2017/11/11  上午9:52
 * Auther: zihe
 * Descirbe: 主界面
 * Email: hezihao@linghit.com
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转到主界面
     * @param activity
     */
    public static void gotoMain(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.startActivity(new Intent(activity, MainActivity.class));
        }
    }
}
