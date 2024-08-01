package com.iceford.heartguard.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.iceford.heartguard.R;

public class AboutApplicationActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_application);

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


        TextView contactMe = findViewById(R.id.contact_me);
        String email = "rongquanhuang01@gmail.com";
        String emailText = "联系我：" + email;
        SpannableString spannableStringEmail = new SpannableString(emailText);
        ClickableSpan clickableSpanEmail = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // 在这里调用手机邮箱应用发送邮件的代码
                sendEmail();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(android.R.color.holo_blue_dark)); // 设置超链接文本颜色
                ds.setUnderlineText(true); // 添加下划线
            }
        };
        spannableStringEmail.setSpan(clickableSpanEmail, emailText.indexOf(email), emailText.indexOf(email) + email.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contactMe.setText(spannableStringEmail);
        contactMe.setMovementMethod(LinkMovementMethod.getInstance());


        // 在合适的位置获取TextView实例
        TextView projectRepository = findViewById(R.id.project_repository);

        // 设置文本内容
        String projectInfo = "项目仓库：Iceford/HeartGuard";
        SpannableString spannableString = new SpannableString(projectInfo);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // 在点击事件中打开链接
                Uri uri = Uri.parse("https://github.com/Iceford/HeartGuard");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置蓝色字体样式
                ds.setColor(getResources().getColor(android.R.color.holo_blue_dark)); // 设置超链接文本颜色
                ds.setUnderlineText(true); // 可选，添加下划线
            }
        };

        // 将ClickableSpan应用到特定的文本范围
        spannableString.setSpan(clickableSpan, 5, projectInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置TextView的显示文本
        projectRepository.setText(spannableString);
        projectRepository.setMovementMethod(LinkMovementMethod.getInstance()); // 必须设置该方法才能使链接可点击
        projectRepository.setHighlightColor(Color.TRANSPARENT); // 可选，设置点击时的高亮颜色为透明
    }


    // 发送电子邮件
    @SuppressLint("QueryPermissionsNeeded")
    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + "rongquanhuang01@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题");
        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "没有找到可用的邮箱应用", Toast.LENGTH_SHORT).show();
        }
    }
}

