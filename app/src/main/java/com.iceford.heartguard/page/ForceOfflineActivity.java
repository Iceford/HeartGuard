package com.iceford.heartguard.page;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.iceford.heartguard.HomePageActivity;

public class ForceOfflineActivity extends AppCompatActivity {

    private ForceOfflineReceiver receiver;

    @Override
    // 将当前活动实例添加到ActivityCollector（活动管理类）中，用于管理所有活动的集合
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    // 注册广播接收器
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        // 用于接收指定的广播动作
        intentFilter.addAction("com.iceford.heartguard.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver, intentFilter);
    }

    // 取消之前注册的广播接收器，确保在活动不可见时不再接收广播
    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    // 从ActivityCollector中移除当前活动实例
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    // 用于接收强制下线的广播通知
    static class ForceOfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            // 显示强制下线提示信息
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("您已退出登录!");
            builder.setCancelable(false);
            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();  // 销毁所有活动
                    Intent i = new Intent(context, HomePageActivity.class); // 启动HomePageActivity活动类
                    context.startActivity(i);
                }
            });
            builder.show();
        }
    }
}

