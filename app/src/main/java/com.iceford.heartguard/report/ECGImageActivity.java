package com.iceford.heartguard.report;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iceford.heartguard.R;
import com.iceford.heartguard.adapter.ECGImageAdapter;
import com.iceford.heartguard.auth.UserSessionManager;
import com.iceford.heartguard.data.DBHelper;

import java.util.List;

import kotlin.Triple;

public class ECGImageActivity extends AppCompatActivity {

    String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
    DBHelper dbHelper = new DBHelper(this);

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg_image);

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


        List<Triple<Long, Integer, Integer>> ecgDataList = dbHelper.getECGInfoByPhoneNumber(currentLoginNumber);
        RecyclerView recyclerView = findViewById(R.id.playback_recyclerView);
        // 创建一个线性布局管理器，并将其设置给recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 创建一个ECGLineChartAdapter对象，并将其recyclerView关联，以显示ECG 数据
        ECGImageAdapter adapter = new ECGImageAdapter(this, ecgDataList, dbHelper);
        recyclerView.setAdapter(adapter);

    }
}

