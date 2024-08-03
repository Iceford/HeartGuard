package com.iceford.heartguard.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import com.bigkoo.pickerview.TimePickerView;
import com.iceford.heartguard.R;
import com.iceford.heartguard.auth.UserSessionManager;
import com.iceford.heartguard.data.DBHelper;
import com.iceford.heartguard.page.ForceOfflineActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseInformationActivity extends ForceOfflineActivity {
    DBHelper dbHelper = new DBHelper(this);
    private EditText etUserName;
    private EditText etUserGender;
    private EditText etUserBirthday;
    private EditText etUserHeight;
    private EditText etUserWeight;
    private EditText etUserWearPacemaker;
    private Calendar selectedDate;
    private String displayDate;
    private TimePickerView timePickerView;
    private String displayHeight;
    private String displayWeight;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        etUserName = findViewById(R.id.et_user_name);

        etUserGender = findViewById(R.id.et_user_gender);
        etUserBirthday = findViewById(R.id.et_user_birthday);
        etUserHeight = findViewById(R.id.et_user_height);
        etUserWeight = findViewById(R.id.et_user_weight);
        etUserWearPacemaker = findViewById(R.id.et_user_wear_pacemaker);

        String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
        // 获取并显示用户相关信息
        etUserName.setText(dbHelper.getUserNameByPhoneNumber(currentLoginNumber));
        etUserGender.setText(dbHelper.getUserGenderByPhoneNumber(currentLoginNumber));
        etUserBirthday.setText(dbHelper.getUserBirthdayByPhoneNumber(currentLoginNumber));
        etUserHeight.setText(dbHelper.getUserHeightByPhoneNumber(currentLoginNumber));
        etUserWeight.setText(dbHelper.getUserWeightByPhoneNumber(currentLoginNumber));
        etUserWearPacemaker.setText(dbHelper.getUserWearPacemakerByPhoneNumber(currentLoginNumber));

        // 更新用户相关信息
        ImageButton saveUpdate = findViewById(R.id.save_update);
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String userBirthday = etUserBirthday.getText().toString();
                String userGender = etUserGender.getText().toString();
                String userHeight = etUserHeight.getText().toString();
                String userWeight = etUserWeight.getText().toString();
                String userWearPacemaker = etUserWearPacemaker.getText().toString();

                dbHelper.updateUserInformationByPhoneNumber(currentLoginNumber, userName, userGender, userBirthday, userHeight, userWeight, userWearPacemaker);
                Toast.makeText(BaseInformationActivity.this, "用户信息更新成功", Toast.LENGTH_SHORT).show();
            }
        });

        // 返回上一页
        ImageButton returnPrevious = findViewById(R.id.return_previous);
        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 性别选择对话框
        etUserGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderDialog();
            }
        });

        // 日期选择器
        etUserBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                timePickerView.show();
            }
        });

        // 身高选择器
        etUserHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHeightPickerDialog();
            }
        });

        // 体重选择器
        etUserWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightPickerDialog();
            }
        });

        // 是否佩戴起搏器选择器
        etUserWearPacemaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPacemakerPickerDialog();
            }
        });

    }


    // 性别选择对话框
    private void showGenderDialog() {
        final String[] options = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择性别").setPositiveButton(options[0], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = options[0];
                etUserGender.setText(selectedOption);
            }
        }).setNegativeButton(options[1], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = options[1];
                etUserGender.setText(selectedOption);
            }
        }).show();
    }


    // 日期选择对话框
    private void showDatePickerDialog() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);// 起始时间
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 12, 31);// 结束时间
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                // 选中事件回调
                etUserBirthday.setText(getTimes(date));
            }
        })
                // 年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false}).setLabel("年", "月", "日", "时", "", "").isCenterLabel(true).setDividerColor(Color.DKGRAY).setContentSize(21).setDate(selectedDate).setRangDate(startDate, endDate).setDecorView(null).build();
    }


    // 格式化时间
    @SuppressLint("SimpleDateFormat")
    private String getTimes(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    // 身高选择对话框
    private void showHeightPickerDialog() {
        final String[] heights = new String[201];
        // 可选择的身高范围为50cm到250cm
        for (int i = 0; i < heights.length; i++) {
            heights[i] = String.valueOf(i + 50);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择身高(单位:厘米)").setSingleChoiceItems(heights, 170 - 50, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                displayHeight = heights[which];
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                etUserHeight.setText(displayHeight);
                dialog.dismiss();
            }
        }).show();
    }

    // 体重选择对话框
    private void showWeightPickerDialog() {
        final String[] weights = new String[151];
        // 可选择的体重范围为30kg到150kg
        for (int i = 0; i < weights.length; i++) {
            weights[i] = String.valueOf(i + 30);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择体重(单位:千克)").setSingleChoiceItems(weights, 65 - 30, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                displayWeight = weights[which];
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                etUserWeight.setText(displayWeight);
                dialog.dismiss();
            }
        }).show();
    }


    // 是否佩戴起搏器选择对话框
    private void showPacemakerPickerDialog() {
        final String[] options = {"是", "否"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否佩戴起搏器").setPositiveButton(options[0], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = options[0];
                etUserWearPacemaker.setText(selectedOption);
            }
        }).setNegativeButton(options[1], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = options[1];
                etUserWearPacemaker.setText(selectedOption);
            }
        }).show();
    }
}

