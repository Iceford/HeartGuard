package com.iceford.heartguard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CircularRoundImageView extends AppCompatImageView {

    private Paint paint;
    private RectF rectF;


    public CircularRoundImageView(Context context) {
        super(context);
        init();
    }


    public CircularRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public CircularRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        rectF = new RectF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = getBitmapFromDrawable();
        if (bitmap == null) {
            super.onDraw(canvas);
            return;
        }

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        float size = Math.min(getWidth(), getHeight());
        float radius = size / 2;

        rectF.set(0, 0, size, size);

        canvas.drawRoundRect(rectF, radius, radius, paint);
    }


    private Bitmap getBitmapFromDrawable() {
        if (getDrawable() == null) {
            return null;
        }

        int width = getWidth();
        int height = getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        getDrawable().draw(canvas);

        return bitmap;
    }

}

