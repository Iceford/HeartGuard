package com.iceford.heartguard.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.iceford.heartguard.R;
import com.iceford.heartguard.auth.UserSessionManager;
import com.iceford.heartguard.data.DBHelper;
import com.iceford.heartguard.utils.CacheUtil;
import com.iceford.heartguard.utils.PasswordUtil;
import com.iceford.heartguard.view.CircularRoundImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.iceford.heartguard.utils.BitmapUtil.convertBitmapToBase64;
import static com.iceford.heartguard.utils.ImageUtil.base64ToImage;
import static com.iceford.heartguard.utils.ImageUtil.resizeBitmap;

public class ProfileFragment extends Fragment {
    // 用于标识从相册选取照片的请求
    private static final int PICK_IMAGE_REQUEST = 1;
    // 用于标识拍摄照片的请求
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    // 用于标识请求相机权限的请求
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
    Boolean whetherLogin = UserSessionManager.getInstance().isLoggedIn();
    DBHelper dbHelper;
    private CircularRoundImageView userAvatar;
    private String base64Avatar = "";
    private Uri imageUri;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new DBHelper(requireContext()); // 通过传递有效的 Context 实例化 DBHelper
        String currentUserName = dbHelper.getUserNameByPhoneNumber(currentLoginNumber);

        userAvatar = view.findViewById(R.id.user_avatar);
        updateDisplayAvatar();

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whetherLogin) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                    View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_avatar_selection, null);
                    bottomSheetDialog.setContentView(bottomSheetView);
                    TextView Photograph = bottomSheetView.findViewById(R.id.photograph);
                    TextView openPhotoAlbum = bottomSheetView.findViewById(R.id.open_photo_album);
                    TextView cancel = bottomSheetView.findViewById(R.id.cancel);

                    // 拍照
                    Photograph.setOnClickListener(view -> {
                        // 权限检查
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            // 请求权限
                            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                        } else {
                            // 启动相机
                            takePhoto();
                            Toast.makeText(getContext(), "打开相机", Toast.LENGTH_SHORT).show();
                        }
                        bottomSheetDialog.cancel();
                    });
                    // 打开相册
                    openPhotoAlbum.setOnClickListener(view -> {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        Toast.makeText(getContext(), "打开相册", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.cancel();
                    });
                    // 取消
                    cancel.setOnClickListener(view -> {
                        bottomSheetDialog.cancel();
                    });
                    // 底部弹窗显示
                    bottomSheetDialog.show();
                } else {
                    Toast.makeText(getContext(), "当前尚未登录", Toast.LENGTH_SHORT).show();
                }
            }


        });


        TextView userName = view.findViewById(R.id.user_name);
        userName.setText("用户:" + currentUserName);
        TextView userPhoneNumber = view.findViewById(R.id.user_phone_number);
        userPhoneNumber.setText("账号:" + currentLoginNumber);


        // 用户信息编辑栏
        ConstraintLayout basicInformation = view.findViewById(R.id.basic_information);
        basicInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whetherLogin) {
                    Intent intent = new Intent(getActivity(), BaseInformationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "当前尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 测量设置
        TextView displaySetting = view.findViewById(R.id.tv_measure_setting);
        displaySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeasureSettingActivity.class);
                startActivity(intent);
            }
        });


        // 关于应用
        TextView aboutApplication = view.findViewById(R.id.tv_about_application);
        aboutApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutApplicationActivity.class);
                startActivity(intent);
            }
        });


        // 版本更新
        TextView versionUpdates = view.findViewById(R.id.tv_version_updates);
        versionUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VersionUpdateActivity.class);
                startActivity(intent);
            }
        });


        // 注销账号
        TextView logoutUser = view.findViewById(R.id.tv_logout_user);
        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whetherLogin) {
                    showLogoutConfirmationDialog();
                } else {
                    Toast.makeText(getContext(), "当前尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 修改密码
        TextView changePassword = view.findViewById(R.id.tv_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whetherLogin) {
                    showChangePasswordDialog();
                } else {
                    Toast.makeText(getContext(), "当前尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 退出登录
        TextView signOut = view.findViewById(R.id.tv_sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whetherLogin) {
                    showSignOutConfirmationDialog();
                } else {
                    Toast.makeText(getContext(), "当前尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // 问题反馈
        TextView feedback = view.findViewById(R.id.tv_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whetherLogin) {
                    feedbackDialog();
                } else {
                    Toast.makeText(getContext(), "当前尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 清理缓存
        TextView clearCache = view.findViewById(R.id.tv_clear_cache);
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showClearCacheDialog();
            }
        });

        return view;
    }


    // 修改密码对话框
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("修改密码");

        // 设置自定义布局
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 获取输入的原密码、新密码和重复新密码
                EditText etOldPassword = dialogView.findViewById(R.id.et_old_password);
                EditText etNewPassword = dialogView.findViewById(R.id.et_new_password);
                EditText etRepeatPassword = dialogView.findViewById(R.id.et_repeat_password);

                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String repeatPassword = etRepeatPassword.getText().toString();

                // 验证密码并执行更改密码操作
                if (verifyPasswords(oldPassword, newPassword, repeatPassword)) {
                    String encryptedPassword = PasswordUtil.md5Encrypt(newPassword);
                    dbHelper.updateLoginPasswordByPhoneNumber(currentLoginNumber, encryptedPassword);
                    Toast.makeText(getContext(), "密码修改成功,请牢记你的新密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    // 问题反馈对话款
    private void feedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("问题反馈");

        // 设置自定义布局
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
        builder.setView(dialogView);

        builder.setPositiveButton("确认提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 获取输入的反馈问题
                EditText etProblemContent = dialogView.findViewById(R.id.problem_content);
                String problemContent = etProblemContent.getText().toString();
                Boolean operationResult = dbHelper.updateFeedbackByPhoneNumber(currentLoginNumber, problemContent);
                if (operationResult) {
                    Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    // 注销账号对话框
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("注销账号是不可恢复的操作，为了保护您的合法权益，请再三确定是否注销账号?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 先强制退出登录
                        Intent intent = new Intent("com.iceford.heartguard.FORCE_OFFLINE");
                        Toast.makeText(getContext(), "账号注销成功", Toast.LENGTH_SHORT).show();
                        requireActivity().sendBroadcast(intent);
                        // 执行注销账号操作
                        dbHelper.deleteAccountByPhoneNumber(currentLoginNumber);
                        dbHelper.deleteConnectedDeviceByPhoneNumber(currentLoginNumber);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 用户选择否时的操作
                    }
                })
                .show();
    }


    // 退出登录对话框
    private void showSignOutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("退出后不会删除任何历史数据，是否确定退出登录?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent("com.iceford.heartguard.FORCE_OFFLINE");
                        requireActivity().sendBroadcast(intent);
                        UserSessionManager.getInstance().setLoggedIn(false);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 用户选择否时的操作
                    }
                })
                .show();
    }


    // 修改密码--验证输入
    private boolean verifyPasswords(String oldPassword, String newPassword, String repeatPassword) {

        if (!Objects.equals(oldPassword, dbHelper.getLoginPasswordByPhoneNumber(currentLoginNumber))) {
            Toast.makeText(getContext(), "原密码错误", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Objects.equals(newPassword, repeatPassword)) {
                Toast.makeText(getContext(), "前后两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }


    // 清理缓存对话框
    private void showClearCacheDialog() {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("您确定现在清理应用缓存？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 取消操作
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 执行清理缓存操作
                            clearCache();
                            Toast.makeText(getContext(), "缓存清理成功", Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            // 设置确定按钮的颜色
            // dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        }
    }

    // 清理缓存
    private void clearCache() {
        Context context = requireContext(); // 获取当前 Fragment 的上下文对象
        CacheUtil.clearInternalCache(context); // 清理应用内部缓存
        CacheUtil.clearExternalCache(context); // 清理应用外部缓存（如果有）
    }


    // 更新用户头像
    public boolean updateDisplayAvatar() {
        base64Avatar = dbHelper.getAvatarBase64ByPhoneNumber(currentLoginNumber);
        if (base64Avatar == null) {
            // 设置默认头像
            userAvatar.setImageResource(R.drawable.iv_man_on_a_trail);
            return false;
        } else {
            userAvatar.setImageBitmap(base64ToImage(base64Avatar));
            return true;
        }
    }


    // 更新用户头像数据
    public void updateAvatarData(Bitmap originalBitmap) {
        // 调整图像大小到200x200
        Bitmap resizedBitmap = resizeBitmap(originalBitmap, 200, 200);
        // 将调整大小后的Bitmap转换为Base64字符串
        base64Avatar = convertBitmapToBase64(resizedBitmap);
        // 更新数据表的base64Avatar
        if (dbHelper.updateUserAvatarByPhoneNumber(currentLoginNumber, base64Avatar)
                && updateDisplayAvatar()) {
            Toast.makeText(getContext(), "头像更新成功", Toast.LENGTH_LONG).show();
        }
    }


    // 根据不同的请求码和结果码，处理从相册选取照片或拍摄照片返回的结果，并将获取到的照片的Bitmap 对象传递给updateAvatarData方法进行进一步处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* 对请求码( requestCode) 和结果码( resultCode) 进行检查，以及确保返回的数据( data) 和数据的Uri ( data.getData()) 不为null */

        // 检查是否是从相册选取照片的返回结果
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // 如果条件满足，将从data中获取选取的照片的Uri，并将其赋值给uri变量
                if (getActivity() != null) {
                    // 将Uri 转换为Bitmap 对象
                    // 获取到当前活动的内容解析器，并传入uri和内容解析器来获取位图
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    updateAvatarData(originalBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 检查是否是从拍摄照片的返回结果
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            try {
                if (getActivity() != null) {
                    // 如果条件满足，将尝试从之前保存的imageUri获取拍摄的照片的Uri，并将其转换为Bitmap 对象
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    updateAvatarData(originalBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 调用相机进行拍照
    @SuppressLint("QueryPermissionsNeeded")
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // 错误处理
            }
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(getActivity(), getContext().getApplicationContext().getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // 在应用程序的外部存储器目录中创建一个唯一的图像文件
    private File createImageFile() throws IOException {
        // 创建一个唯一的文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",   /* suffix */
                storageDir      /* 目录 */
        );
        return image;
    }


    // 处理权限请求的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予，启动相机
                takePhoto();
            } else {
                // 权限被拒绝，向用户解释为什么需要这些权限
                Toast.makeText(getContext(), "请允许使用相机拍照", Toast.LENGTH_LONG).show();
            }
        }
    }

}

