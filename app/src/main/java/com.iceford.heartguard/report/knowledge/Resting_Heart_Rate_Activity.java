package com.iceford.heartguard.report.knowledge;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.iceford.heartguard.R;

public class Resting_Heart_Rate_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resting_heart_rate);

        // 隐藏顶部栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 初始化 WebView
        WebView webView = findViewById(R.id.webView);

        // 隐藏 ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton returnPrevious = findViewById(R.id.return_previous);
        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 加载 assets 文件夹下的 index.html 文件
        webView.loadUrl("file:///android_asset/resting_heart_rate.html");
    }
}

