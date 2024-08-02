package com.iceford.heartguard.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.iceford.heartguard.BuildConfig;
import com.iceford.heartguard.R;
import com.iceford.heartguard.utils.ImageUtil;

public class VersionUpdateActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_update);

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

        ImageView logoHeartRate = findViewById(R.id.logo_heart_rate);
        ImageUtil.loadGifIntoImageView(VersionUpdateActivity.this, R.drawable.gif_heartbeat, logoHeartRate);


        TextView appName = findViewById(R.id.app_name);
        appName.setText("HeartGuard");

        TextView currentVersion = findViewById(R.id.current_version);
        currentVersion.setText("当前版本：" + BuildConfig.VERSION_NAME);

        TextView lastUpdatedTime = findViewById(R.id.last_updated_time);
        lastUpdatedTime.setText("上次更新时间：2024-05-21");


        // 获取更新
        Button getUpdates = findViewById(R.id.get_updates);
        getUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentVersion = BuildConfig.VERSION_NAME;
                Toast.makeText(getApplicationContext(), "当前已是最新版本:" + currentVersion, Toast.LENGTH_SHORT).show();
            }
        });

    }
}

