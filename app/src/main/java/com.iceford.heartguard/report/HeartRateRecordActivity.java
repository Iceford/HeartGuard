package com.iceford.heartguard.report;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iceford.heartguard.R;
import com.iceford.heartguard.adapter.RecordTableAdapter;
import com.iceford.heartguard.auth.UserSessionManager;
import com.iceford.heartguard.data.DBHelper;
import com.iceford.heartguard.data.TableRecordHeader;
import com.iceford.heartguard.data.TableRecordRow;
import com.iceford.heartguard.page.ForceOfflineActivity;

import java.util.ArrayList;
import java.util.List;

public class HeartRateRecordActivity extends ForceOfflineActivity {

    String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
    // 数据库操作对象
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_record);

        // 隐藏ActionBar，以便全屏显示内容
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


        // 表格头部数据
        List<TableRecordHeader> tableRecordHeaderList = new ArrayList<>();
        // 表格行数据列表
        List<TableRecordRow> tableRecordRowList = dbHelper.getAllMeasurementDataByPhoneNumber(currentLoginNumber);

        for (TableRecordRow tableRecordRow : tableRecordRowList) {
            tableRecordHeaderList.add(new TableRecordHeader(
                    tableRecordRow.getMeasureTime(),
                    tableRecordRow.getDuration(),
                    tableRecordRow.getMinHR(),
                    tableRecordRow.getAvgHR(),
                    tableRecordRow.getMaxHR(),
                    tableRecordRow.getAvgHRV(),
                    tableRecordRow.getAvgQTC(),
                    tableRecordRow.getAvgStress()
            ));
        }


        RecyclerView recyclerView = findViewById(R.id.history_recyclerView);
        RecordTableAdapter tableAdapter = new RecordTableAdapter(tableRecordHeaderList);

        recyclerView.setAdapter(tableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}


