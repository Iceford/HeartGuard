package com.iceford.heartguard.monitor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.iceford.heartguard.R;
import com.iceford.heartguard.auth.RegisterLoginActivity;
import com.iceford.heartguard.auth.UserSessionManager;
import com.iceford.heartguard.data.DBHelper;
import com.iceford.heartguard.data.LatestMeasurementData;
import com.iceford.heartguard.utils.ImageUtil;
import com.iceford.heartguard.utils.TimeUtil;
import com.iceford.heartguard.view.CircularRoundImageView;

import java.util.Objects;

import static com.iceford.heartguard.utils.ImageUtil.base64ToImage;


public class MonitorFragment extends Fragment {

    Boolean loginStatus = UserSessionManager.getInstance().isLoggedIn();
    String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
    private ConstraintLayout userInfo;
    private CircularRoundImageView userAvatar;
    private TextView userName;
    private TextView userAge;
    private TextView userHeight;
    private TextView userWeight;
    private TextView tvLastMeasurementTime;
    private TextView tvLowestHeartRate;
    private TextView tvAverageHeartRate;
    private TextView tvHighestHeartRate;
    private DBHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        // 初始化DBHelper对象
        dbHelper = new DBHelper(getActivity());

        userAvatar = view.findViewById(R.id.user_avatar);
        updateDisplayAvatar();

        Button registerOrLogin = view.findViewById(R.id.register_or_login);
        if (UserSessionManager.getInstance().isLoggedIn()) {
            registerOrLogin.setText("切换账号");
        }

        registerOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转至登录界面的操作
                Intent intent = new Intent(getActivity(), RegisterLoginActivity.class);
                startActivity(intent);
            }
        });


        userName = view.findViewById(R.id.user_name);
        userAge = view.findViewById(R.id.user_age);
        userWeight = view.findViewById(R.id.user_weight);
        userHeight = view.findViewById(R.id.user_height);

        tvLastMeasurementTime = view.findViewById(R.id.tv_last_measurement_time);
        tvLowestHeartRate = view.findViewById(R.id.tv_lowest_heart_rate);
        tvAverageHeartRate = view.findViewById(R.id.tv_average_heart_rate);
        tvHighestHeartRate = view.findViewById(R.id.tv_highest_heart_rate);

        // 如果已经登录
        if (loginStatus) {
            String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
            LatestMeasurementData latestMeasurementData = dbHelper.getLatestMeasurementDataByPhoneNumber(currentLoginNumber);
            userName.setText("用户:" + dbHelper.getUserNameByPhoneNumber(currentLoginNumber));
            userAge.setText("年龄:" + dbHelper.calculateUserAgeByPhoneNumber(currentLoginNumber) + "岁");
            userHeight.setText("身高:" + dbHelper.getUserHeightByPhoneNumber(currentLoginNumber) + "cm");
            userWeight.setText("体重:" + dbHelper.getUserWeightByPhoneNumber(currentLoginNumber) + "kg");
            tvLastMeasurementTime.setText(TimeUtil.timestampToBeijingTime(latestMeasurementData.getMeasurementTime()));
            tvLowestHeartRate.setText("最低心率:" + latestMeasurementData.getMinHR() + " bpm");
            tvHighestHeartRate.setText("最高心率:" + latestMeasurementData.getMaxHR() + " bpm");
            tvAverageHeartRate.setText("平均心率:" + latestMeasurementData.getAvgHR() + " bpm");
        } else if (!loginStatus) {
            // 当前尚未登录
            defaultDisplay();
        }

        nullValueDisplay();


        ImageView heartbeatGif = view.findViewById(R.id.heartbeat_gif);
        ImageUtil.loadGifIntoImageView(getActivity(), R.drawable.gif_heartbeat, heartbeatGif);


        Button startMeasure = view.findViewById(R.id.start_measure);
        startMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginStatus) {
                    Intent intent = new Intent(getActivity(), MeasureActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "请登录后测量", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @SuppressLint("SetTextI18n")
    private void defaultDisplay() {
        userName.setText("用户:---");
        userAge.setText("年龄:--岁");
        userWeight.setText("身高:--cm");
        userHeight.setText("体重:--kg");
        tvLastMeasurementTime.setText("暂无测量数据");
        tvLowestHeartRate.setText("最低心率: -- bpm");
        tvHighestHeartRate.setText("最高心率: -- bpm");
        tvAverageHeartRate.setText("平均心率: -- bpm");
    }


    @SuppressLint("SetTextI18n")
    private void nullValueDisplay() {

        if (Objects.equals(userName.getText(), "用户:null")) {
            userName.setText("用户:---");
        }
        if ((Objects.equals(userAge.getText(), "年龄:-1岁")) || (Objects.equals(userAge.getText(), "年龄:null岁"))) {
            userAge.setText("年龄:--岁");
        }

        if (Objects.equals(userHeight.getText(), "身高:cm") || Objects.equals(userHeight.getText(), "身高:nullcm")) {
            userHeight.setText("身高:--cm");
        }
        if ((Objects.equals(userWeight.getText(), "体重:kg")) || (Objects.equals(userWeight.getText(), "体重:nullkg"))) {
            userWeight.setText("体重:--kg");
        }

        if (Objects.equals(tvLastMeasurementTime.getText(), "1970-01-01 08:00:00")) {
            tvLastMeasurementTime.setText("暂无测量数据");
        }
    }


    public void updateDisplayAvatar() {
        String base64Avatar = dbHelper.getAvatarBase64ByPhoneNumber(currentLoginNumber);
        if (base64Avatar == null) {
            // 设置默认头像
            userAvatar.setImageResource(R.drawable.iv_man_on_a_trail);
        } else {
            userAvatar.setImageBitmap(base64ToImage(base64Avatar));
        }
    }

}

