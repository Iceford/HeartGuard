package com.iceford.heartguard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.iceford.heartguard.auth.UserSessionManager;

public class WelcomeActivity extends AppCompatActivity {
    private final int countdownDuration = 3;
    private MaterialButton skipAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 如果已经登录，则跳过欢迎页面直接进入主页
        boolean loginStatus = UserSessionManager.getInstance().isLoggedIn();
        if (loginStatus) {
            startToActivity();
            return;
        }


        // 获取Logo和底部文字对象
        ImageView launchImage = findViewById(R.id.launch_image);
        TextView launchText = findViewById(R.id.launch_text);
        // 加载两段动画
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.splash_image);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.splash_text);
        // 启动动画
        launchImage.startAnimation(anim1);
        launchText.startAnimation(anim2);
        skipAnimation = findViewById(R.id.skip_animation);
        timer.start();
        skipAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startToActivity();
            }

        });
    }


    private void startToActivity() {
        timer.cancel();
        Intent intent = new Intent(WelcomeActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    private final CountDownTimer timer = new CountDownTimer(countdownDuration * 1000, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            skipAnimation.setText("跳过 " + (millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            startToActivity();
        }
    };

}

