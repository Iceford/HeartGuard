package com.iceford.heartguard.profile;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.iceford.heartguard.R;
import com.iceford.heartguard.auth.UserSessionManager;

public class MeasureSettingActivity extends AppCompatActivity {
    private EditText editTextCustomTime;
    private RadioGroup radioGroupTime;

    private RadioGroup radioGroupMagnification;
    private TextView showHint;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_setting);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        showHint = findViewById(R.id.show_hint);
        showHint.setPaintFlags(showHint.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        showHint.setText("当前测量时长为" + UserSessionManager.getInstance().getMeasureDuration() + "秒,放大倍数为" + UserSessionManager.getInstance().getMagnification() + "倍");

        ImageButton returnPrevious = findViewById(R.id.return_previous);
        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // 选择测量时间
        radioGroupTime = findViewById(R.id.radioGroupTime);
        editTextCustomTime = findViewById(R.id.editTextCustomTime);
        Button confirmTime = findViewById(R.id.confirm_time);

        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCustom) {
                    // 当选中“自定义”时，显示EditText
                    editTextCustomTime.setVisibility(View.VISIBLE);
                } else {
                    // 否则，隐藏EditText
                    editTextCustomTime.setVisibility(View.GONE);
                }
            }
        });

        // 设置测量时间
        confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroupTime.getCheckedRadioButtonId();
                if (checkedRadioButtonId != -1) {
                    RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
                    String selectedText = checkedRadioButton.getText().toString();

                    // 执行相应的逻辑，根据选中的时间进行处理
                    switch (selectedText) {
                        case "30s":
                            UserSessionManager.getInstance().setMeasureDuration(30);
                            Toast.makeText(getApplicationContext(), "测量量时长设置为30秒", Toast.LENGTH_SHORT).show();
                            break;
                        case "60s":
                            UserSessionManager.getInstance().setMeasureDuration(60);
                            Toast.makeText(getApplicationContext(), "测量量时长设置为60秒", Toast.LENGTH_SHORT).show();
                            break;
                        case "90s":
                            UserSessionManager.getInstance().setMeasureDuration(90);
                            Toast.makeText(getApplicationContext(), "测量量时长设置为90秒", Toast.LENGTH_SHORT).show();
                            break;
                        case "120s":
                            UserSessionManager.getInstance().setMeasureDuration(120);
                            Toast.makeText(getApplicationContext(), "测量量时长设置为120秒", Toast.LENGTH_SHORT).show();
                            break;
                        case "自定义":
                            String eTCustomTime = editTextCustomTime.getText().toString();
                            if (eTCustomTime.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "自定义测量量时长不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                int customTime = Integer.parseInt(eTCustomTime);
                                // 处理自定义时间的情况，使用 customTime 变量中的时间值
                                if (customTime == 0) {
                                    Toast.makeText(getApplicationContext(), "测量量时长不能为0秒", Toast.LENGTH_SHORT).show();
                                } else {
                                    UserSessionManager.getInstance().setMeasureDuration(customTime);
                                    Toast.makeText(getApplicationContext(), "测量量时长设置为" + customTime + "秒", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请选择测量时长", Toast.LENGTH_SHORT).show();
                }
                showHint.setText("当前测量时长为" + UserSessionManager.getInstance().getMeasureDuration() + "秒,放大倍数为" + UserSessionManager.getInstance().getMagnification() + "倍");
            }
        });


        // 设置放大倍数
        radioGroupMagnification = findViewById(R.id.radioGroup_magnification);
        Button confirmMagnification = findViewById(R.id.confirm_magnification);
        confirmMagnification.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroupMagnification.getCheckedRadioButtonId();
                if (checkedRadioButtonId != -1) {
                    RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
                    String selectedText = checkedRadioButton.getText().toString();

                    // 执行相应的逻辑，根据选中的时间进行处理
                    switch (selectedText) {
                        case "1倍":
                            UserSessionManager.getInstance().setMagnification(1);
                            Toast.makeText(getApplicationContext(), "放大倍数设置为1倍", Toast.LENGTH_SHORT).show();
                            break;
                        case "2倍":
                            UserSessionManager.getInstance().setMagnification(2);
                            Toast.makeText(getApplicationContext(), "放大倍数设置为2倍", Toast.LENGTH_SHORT).show();
                            break;
                        case "3倍":
                            UserSessionManager.getInstance().setMagnification(3);
                            Toast.makeText(getApplicationContext(), "放大倍数设置为3倍", Toast.LENGTH_SHORT).show();
                            break;

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请选择放大倍数", Toast.LENGTH_SHORT).show();
                }
                showHint.setText("当前测量时长为" + UserSessionManager.getInstance().getMeasureDuration() + "秒,放大倍数为" + UserSessionManager.getInstance().getMagnification() + "倍");
            }
        });
    }

}

