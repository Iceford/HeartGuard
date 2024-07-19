package com.iceford.heartguard.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    // 加载gif动图
    public static void loadGifIntoImageView(Context context, int gifDrawableResId, ImageView imageView) {
        if (context == null || imageView == null) {
            return; // 添加对上下文和ImageView参数的空值检查
        }

        Glide.with(context)
                .asGif()
                .load(gifDrawableResId)
                .into(new ImageViewTarget<GifDrawable>(imageView) {
                    @Override
                    protected void setResource(@Nullable GifDrawable resource) {
                        if (imageView != null && resource != null) {
                            imageView.setImageDrawable(resource);
                            resource.start();
                        }
                    }
                });
    }


    // 将图像转换为Base64字符串
    public static String imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // 将Base64字符串转换为图像
    public static Bitmap base64ToImage(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // 调整图像大小
    public static Bitmap resizeBitmap(Bitmap originalBitmap, int width, int height) {
        return Bitmap.createScaledBitmap(originalBitmap, width, height, false);
    }

}


