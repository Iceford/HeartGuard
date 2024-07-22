package com.iceford.heartguard.auth;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.iceford.heartguard.HomePageActivity;
import com.iceford.heartguard.R;
import com.iceford.heartguard.data.DBHelper;
import com.iceford.heartguard.utils.ImageUtil;
import com.iceford.heartguard.utils.PasswordUtil;
import com.iceford.heartguard.utils.VerifyUtil;

public class RegisterLoginActivity extends AppCompatActivity {

    DBHelper dbHelper;
    private ConstraintLayout loginArea;
    private EditText phoneNumberLogin;
    private EditText accountPasswordLogin;
    private CheckBox rememberMe;
    private ConstraintLayout registerArea;
    private EditText phoneNumberRegister;
    private EditText accountPasswordRegister;
    private EditText repeatPasswordRegister;
    private TextView selectTips;
    private CheckBox agreeCheck;
    private SharedPreferences.Editor editor;    // 用于编辑偏好设置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        dbHelper = new DBHelper(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        ImageView appLogo = findViewById(R.id.app_logo);
        ImageUtil.loadGifIntoImageView(this, R.drawable.gif_heartbeat, appLogo);

        loginArea = findViewById(R.id.login_area);
        registerArea = findViewById(R.id.register_area);

        // 用户服务政策和隐私协议
        agreeCheck = findViewById(R.id.agree_check);
        String checkboxText = "我已经阅读并接受《用户服务政策》和《隐私协议》。";
        SpannableString spannableString = new SpannableString(checkboxText);

        // 设置"用户服务政策"的点击事件和样式
        ClickableSpan policyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                // 在此处添加点击"用户服务政策"时的处理逻辑
                showUserServicePolicyDialog();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); // 去掉下划线
                ds.setColor(getResources().getColor(R.color.blue)); // 设置颜色为蓝色
            }
        };
        int policyStart = checkboxText.indexOf("《用户服务政策》");
        int policyEnd = policyStart + "《用户服务政策》".length();
        spannableString.setSpan(policyClickableSpan, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置"隐私协议"的点击事件和样式
        ClickableSpan privacyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                // 在此处添加点击"隐私协议"时的处理逻辑
                showPrivacyAgreementDialog();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); // 去掉下划线
                ds.setColor(getResources().getColor(R.color.blue)); // 设置颜色为蓝色
            }
        };
        int privacyStart = checkboxText.indexOf("《隐私协议》");
        int privacyEnd = privacyStart + "《隐私协议》".length();
        spannableString.setSpan(privacyClickableSpan, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        agreeCheck.setText(spannableString);
        agreeCheck.setMovementMethod(LinkMovementMethod.getInstance());


        // 用户登录逻辑处理
        phoneNumberLogin = findViewById(R.id.phone_number_login);
        accountPasswordLogin = findViewById(R.id.account_password_login);
        rememberMe = findViewById(R.id.remember_me);
        Button userLogin = findViewById(R.id.user_login);

        // 用于存储应用程序的偏好设置
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // 获取SharedPreferences的编辑器
        SharedPreferences.Editor editor = preferences.edit();
        // 从SharedPreferences中获取记住我的设置，并根据设置填充电话号码和登录密码的文本编辑框
        boolean whetherRemember = preferences.getBoolean("remember_me", false);
        if (whetherRemember) {
            String remember_phone_number = preferences.getString("phone_number", "");
            String remember_login_password = preferences.getString("login_password", "");
            phoneNumberLogin.setText(remember_phone_number);
            accountPasswordLogin.setText(remember_login_password);
            rememberMe.setChecked(true);
        }

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取输入的手机号码和密码
                String phone = phoneNumberLogin.getText().toString();
                String password = accountPasswordLogin.getText().toString();
                String encryptedPassword = PasswordUtil.md5Encrypt(password);
                // 如果检查手机号和密码
                if (!VerifyUtil.validatePhoneNumber(phone)) {
                    Toast.makeText(RegisterLoginActivity.this, "请输入有效的11位数字手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.isEmpty()) {
                        Toast.makeText(RegisterLoginActivity.this, "密码不为空", Toast.LENGTH_SHORT).show();
                    } else {
                        // 检查用户账号是否已经注册存在
                        if (dbHelper.authenticateUser(phone, encryptedPassword) == AuthenticationResult.USER_NOT_FOUND) {
                            Toast.makeText(RegisterLoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
                            // 比对输入的账号与密码是否正确
                        } else if (dbHelper.authenticateUser(phone, encryptedPassword) == AuthenticationResult.INVALID_PASSWORD) {
                            Toast.makeText(RegisterLoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        } else if (dbHelper.authenticateUser(phone, encryptedPassword) == AuthenticationResult.SUCCESS) {
                            // 如果未勾选同意用户服务政策和隐私协议
                            if (!agreeCheck.isChecked()) {
                                Toast.makeText(RegisterLoginActivity.this, "请勾选同意我们的服务政策和隐私协议", Toast.LENGTH_SHORT).show();
                            } else if (agreeCheck.isChecked()) {
                                // 登录成功则会显示一个短暂的提示消息
                                Toast.makeText(RegisterLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                // 登录成功后，将手机号码存储在 UserDataManager 中
                                UserSessionManager.getInstance().setPhoneNumber(phone);
                                // 更新登录状态
                                UserSessionManager.getInstance().setLoggedIn(true);
                                // 如果用户选择记住用户
                                if (rememberMe.isChecked()) {
                                    // 将记住我标记、手机号码和登录密码存储到SharedPreferences中
                                    editor.putBoolean("remember_me", true);
                                    editor.putString("phone_number", phone);
                                    editor.putString("login_password", password);
                                }

                                editor.apply();

                                // 启动主页的Activity
                                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // 登录失败的处理,显示一个短暂的提示消息
                            Toast.makeText(RegisterLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        phoneNumberRegister = findViewById(R.id.phone_number_register);
        accountPasswordRegister = findViewById(R.id.account_password_register);
        repeatPasswordRegister = findViewById(R.id.repeat_password_register);
        Button userRegister = findViewById(R.id.user_register);

        // 用户注册逻辑实现
        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取输入的用户名称、手机号码、登录密码、重复密码
                String phone_number = phoneNumberRegister.getText().toString();
                String login_password = accountPasswordRegister.getText().toString();
                String repeat_password = repeatPasswordRegister.getText().toString();

                if (!VerifyUtil.validatePhoneNumber(phone_number)) {
                    Toast.makeText(RegisterLoginActivity.this, "请输入有效的11位数字手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(phone_number) || TextUtils.isEmpty(login_password) || TextUtils.isEmpty(repeat_password)) {
                        Toast.makeText(RegisterLoginActivity.this, "所有字段必填", Toast.LENGTH_SHORT).show();
                    } else {
                        // 如果前后密码匹配
                        if (!login_password.equals(repeat_password)) {
                            // 前后密码不一致提示信息
                            Toast.makeText(RegisterLoginActivity.this, "前后两次密码不一致", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!VerifyUtil.validatePassword(login_password)) {
                                Toast.makeText(RegisterLoginActivity.this, "密码至少包含一个小写字母、一个大写字母、一个数字和一个特殊字符，并且长度至少为 8 位", Toast.LENGTH_SHORT).show();
                            } else {
                                Boolean checkUser = dbHelper.checkPhoneNumber(phone_number);
                                // 如果用户尚未注册
                                if (!checkUser) {
                                    if (!agreeCheck.isChecked()) {
                                        Toast.makeText(RegisterLoginActivity.this, "请勾选同意我们的服务政策和隐私协议", Toast.LENGTH_SHORT).show();
                                    } else if (agreeCheck.isChecked()) {    // 如果手机号码不存在则将用户信息插入数据库中
                                        String encryptedPassword = PasswordUtil.md5Encrypt(login_password);
                                        Boolean whetherInsert = dbHelper.insertRegisteredUser(phone_number, encryptedPassword);
                                        // 用户数据插入成功后提示成功信息
                                        if (whetherInsert) {
                                            Toast.makeText(RegisterLoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // 用户数据插入失败后提示失败信息
                                            Toast.makeText(RegisterLoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    // 如果用户已经注册
                                    Toast.makeText(RegisterLoginActivity.this, "该手机号码已经注册", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        });


        selectTips = findViewById(R.id.select_tips);
        final boolean[] isRegistered = {false};

        selectTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegistered[0]) {
                    selectTips.setText("还没有账号，先注册");
                    loginArea.setVisibility(View.VISIBLE);
                    registerArea.setVisibility(View.GONE);
                    isRegistered[0] = false;

                } else {
                    selectTips.setText("已有账号，直接登录");
                    loginArea.setVisibility(View.GONE);
                    registerArea.setVisibility(View.VISIBLE);
                    isRegistered[0] = true;
                }
            }
        });


    }


    // 用户服务政策对话框
    void showUserServicePolicyDialog() {
        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_user_service_policy, null);

        // 加载ScrollView
        ScrollView scrollView = dialogView.findViewById(R.id.user_service_policy);

        // 设置对话框的布局
        builder.setView(dialogView);

        // 可根据需要设置对话框的标题、按钮等属性
        builder.setTitle("用户服务政策");

        // 添加“确定”按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 关闭对话框
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    // 隐私协议对话框
    void showPrivacyAgreementDialog() {
        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_privacy_agreement, null);

        // 加载ScrollView
        ScrollView scrollView = dialogView.findViewById(R.id.privacy_agreement);

        // 设置对话框的布局
        builder.setView(dialogView);

        // 可根据需要设置对话框的标题、按钮等属性
        builder.setTitle("隐私协议");

        // 添加“确定”按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 关闭对话框
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

