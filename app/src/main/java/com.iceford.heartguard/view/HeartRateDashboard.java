package com.iceford.heartguard.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.iceford.heartguard.R;

public class HeartRateDashboard extends View {

    // 上下文对象
    private final Context context;
    // 区间范围节点
    private final int[] indicatorColor = {0xffffffff, 0x00ffffff, 0x99ffffff, 0xffffffff};
    // 用于绘制图形的画笔
    private Paint paint;
    private Paint paint_2;
    private Paint paint_3;
    private Paint paint_4;
    private Paint paint_5;
    private Paint paint_6;
    // 最大值
    private int maxNum;
    // 起始角度
    private int startAngle;
    // 扫描角度
    private int sweepAngle;
    // 半径
    private int radius;
    // 视图的宽度
    private int mWidth;
    // 内圆的宽度
    private int sweepInWidth;
    // 外圆的宽度
    private int sweepOutWidth;
    //当前数值，默认为0，需设置setter、getter 供属性动画使用
    private int CurrentHeartRate = 0;

    // 构造函数，调用了带有两个参数的构造函数，并将第二个参数置为null
    public HeartRateDashboard(Context context) {
        this(context, null);
    }

    // 构造函数，调用了带有三个参数的构造函数，并将第三个参数置为0
    public HeartRateDashboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 构造函数，初始化视图的属性，调用了父类的构造函数
    public HeartRateDashboard(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // 背景颜色
        // setBackgroundColor(0xFF00CED1);
        setBackgroundColor(0x00FFFFFF);
        initAttr(attrs);
        initPaint();
    }


    // 用于获取屏幕的显示度量
    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    public void setCurrentNum(int CurrentHeartRate) {
        this.CurrentHeartRate = CurrentHeartRate;
        invalidate();
    }


    // 在设置当前数值的同时播放动画效果，实现连续动态显示
    public void setCurrentNumAnim(int num) {
        // 创建一个 ValueAnimator 对象，从当前的数值 CurrentHeartRate 平滑过渡到目标数值 num
        ValueAnimator animator = ValueAnimator.ofInt(CurrentHeartRate, num);
        // 设置动画持续时间，这里设置为100毫秒
        animator.setDuration(100);
        // 添加动画更新监听器，用于更新当前数值和背景颜色
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取动画当前的属性值
                int value = (int) animation.getAnimatedValue();
                // 更新当前数值
                setCurrentNum(value);
                // 根据当前属性值计算颜色
                // int color = calculateColor(value);
                // 将计算得到的颜色设置为当前视图的背景色，实现动画效果
                //setBackgroundColor(color);
            }
        });
        // 启动动画，开始播放动画效果
        animator.start();
    }


    // 用于初始化绘制所需的画笔对象
    private void initPaint() {
        // 创建一个新的画笔对象 paint，并设置抗锯齿标志，以确保绘制的图形边缘更加平滑
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 启用抖动效果。抖动效果可以在绘制颜色深度较低的图形时，通过模拟色彩过渡，使得图像看起来更加平滑
        paint.setDither(true);
        // 设置画笔的绘制样式为描边。这意味着绘制的图形只有轮廓线，没有填充色
        paint.setStyle(Paint.Style.STROKE);
        // 设置画笔的颜色为白色
        // paint.setColor(0xffffffff);
        paint.setColor(0xFF00CED1);
        paint_2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_6 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    // 用于初始化自定义视图的属性
    @SuppressLint("CustomViewStyleable")
    private void initAttr(AttributeSet attrs) {
        // 通过上下文（context）获取一个属性数组 TypedArray，该数组包含了在 XML 布局文件中定义的自定义视图的属性值。attrs 是在 XML 布局文件中定义的视图属性集合
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundIndicatorView);
        // 从属性数组中获取名为 maxNum 的整数属性值，并赋值给 maxNum 变量。如果未在 XML 中指定该属性，则默认值为 200
        maxNum = array.getInt(R.styleable.RoundIndicatorView_maxNum, 200);
        // 获取名为 startAngle 的整数属性值，并赋值给 startAngle 变量。如果未在 XML 中指定该属性，则默认值为 160
        startAngle = array.getInt(R.styleable.RoundIndicatorView_startAngle, 160);
        // 获取名为 sweepAngle 的整数属性值，并赋值给 sweepAngle 变量。如果未在 XML 中指定该属性，则默认值为 220
        sweepAngle = array.getInt(R.styleable.RoundIndicatorView_sweepAngle, 220);
        // 设置内圆的宽度为 8 像素，通过 dp2px() 方法将 dp 单位转换为像素单位
        sweepInWidth = dp2px(8);
        // 设置外圆的宽度为 3 像素，同样通过 dp2px() 方法将 dp 单位转换为像素单位
        sweepOutWidth = dp2px(3);
        // 回收属性数组，以便在使用完毕后释放内存资源
        array.recycle();
    }


    @Override
    // 用于确定 View 的大小，以便在屏幕上正确布局和显示
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 调用了父类的 super.onMeasure(widthMeasureSpec, heightMeasureSpec) 方法，以确保测量过程的正确执行
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = dp2px(400);
        int mHeight = dp2px(300);
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    // 在 View 的大小发生改变时被调用
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
    }


    @Override
    // 用于绘制View的内容,在每次 View 需要被绘制时被调用
    protected void onDraw(@NonNull Canvas canvas) {
        // 调用父类的 onDraw 方法，以确保父类的绘制逻辑得以执行
        super.onDraw(canvas);
        // 计算圆的半径。这里使用了 getMeasuredWidth() 方法来获取 View 的测量宽度，然后除以4来得到半径
        radius = 4 * (getMeasuredWidth() / 10);
        // 保存当前画布的状态，以便后续的操作可以在此状态下进行
        canvas.save();
        // 将画布移动到 View 的中心位置。这里使用了 (mWidth/2, mWidth/2) 作为平移的距离，使得画布的原点位于 View 的中心
        canvas.translate((float) mWidth / 2, (float) (mWidth) / 2);
        // 绘制内外圆
        drawRound(canvas);
        // 绘制刻度
        drawScale(canvas);
        // 用于绘制当前进度值的指示器
        drawIndicator(canvas);
        // 用于绘制中间的文字
        drawCenterText(canvas);
        // 恢复画布之前保存的状态，以便后续的操作不受之前的状态影响
        canvas.restore();
    }


    // 用于在中心位置绘制文字
    private void drawCenterText(Canvas canvas) {
        // 保存当前画布的状态，以便后续的操作可以在此状态下进行
        canvas.save();
        // 设置绘制风格为填充
        paint_4.setStyle(Paint.Style.FILL);
        paint_5.setStyle(Paint.Style.FILL);
        paint_6.setStyle(Paint.Style.FILL);
        // 设置文字大小
        paint_4.setTextSize((float) radius / 4);
        paint_5.setTextSize((float) radius / 3);
        paint_6.setTextSize((float) radius / 9);
        paint_4.setColor(0xFF00CED1);
        paint_5.setColor(0xFF00CED1);
        paint_6.setColor(0xFF00CED1);
        String content = "";
        // 创建一个矩形对象，用于存放文字的边界信息
        Rect r = new Rect();
        // 获取文字的边界信息，并存放在矩形对象 r 中
        paint_4.getTextBounds(content, 0, content.length(), r);
        // 在画布上绘制文字内容
        canvas.drawText(content, (float) -r.width() / 2, r.height() + 20, paint_4);
        // 在当前画布上绘制文字
        canvas.drawText(CurrentHeartRate + "", -paint_5.measureText(CurrentHeartRate + "") + 45, -paint_5.measureText(" ") * 2 + 100, paint_5);
        canvas.drawText("   bpm", paint_6.measureText(" ") * 6, -paint_6.measureText(" ") * 4 + 100, paint_6);
        // 恢复画布之前保存的状态，以便后续的操作不受之前的状态影响
        canvas.restore();
    }


    // 用于绘制当前进度值的指示器
    private void drawIndicator(Canvas canvas) {
        // 保存当前画布的状态，以便后续的操作可以在此状态下进行
        canvas.save();
        // 设置画笔样式为描边，用于绘制指示器的外轮廓
        paint_2.setStyle(Paint.Style.STROKE);
        // 计算指示器的扫描角度 sweep
        int sweep;
        if (CurrentHeartRate <= maxNum) {
            // 如果当前进度值 CurrentHeartRate 小于等于最大值 maxNum，则计算当前进度值在总角度 sweepAngle 中所占的比例，并将其转换为扫描角度
            sweep = (int) ((float) CurrentHeartRate / (float) maxNum * sweepAngle);
        } else {
            // 如果当前进度值超过最大值，则将扫描角度设置为总角度，表示指示器完全填充
            sweep = sweepAngle;
        }
        // 设置指示器的描边宽度为预定义的宽度
        paint_2.setStrokeWidth(sweepOutWidth);
        // 创建渐变着色器 Shader
        Shader shader = new SweepGradient(0, 0, indicatorColor, null);
        // 使用 SweepGradient 类创建一个扫描渐变，从指定中心点开始，以指定颜色数组进行渐变
        paint_2.setShader(shader);
        // 将预定义的像素值转换为实际像素值，用于指示器的宽度
        int w = dp2px(10);
        // 创建绘制指示器的矩形区域 RectF:以圆心为中心，以圆的半径为基准，再加上指示器的宽度，构造一个矩形区域
        RectF rectf = new RectF(-radius - w, -radius - w, radius + w, radius + w);
        // 如果扫描角度大于 0，则绘制指示器的弧线
        if (sweep > 0) {
            // 使用 canvas.drawArc() 方法绘制指示器的弧线，参数包括矩形区域、起始角度、扫描角度、是否使用中心以及画笔
            canvas.drawArc(rectf, startAngle, sweep, false, paint_2);
        }
        // 计算指示器的终点坐标 (x, y):根据当前进度值所对应的扫描角度，以及圆的半径和指示器的宽度，计算指示器的终点坐标
        float x = (float) ((radius + dp2px(10)) * Math.cos(Math.toRadians(startAngle + sweep)));
        float y = (float) ((radius + dp2px(10)) * Math.sin(Math.toRadians(startAngle + sweep)));
        // 设置画笔样式为填充，并配置模糊遮罩效果
        paint_3.setStyle(Paint.Style.FILL);
        // 设置画笔颜色为
        paint_3.setColor(0xffffffff);
        // 使用 BlurMaskFilter 类创建一个模糊遮罩效果，以模糊指示器内部的填充效果
        paint_3.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.SOLID)); //需关闭硬件加速
        // 绘制指示器的内部圆形：使用 canvas.drawCircle() 方法绘制一个圆形，参数包括圆心坐标 (x, y)、半径以及画笔
        canvas.drawCircle(x, y, dp2px(3), paint_3);
        // 恢复画布之前保存的状态，以便后续的操作不受之前的状态影响
        canvas.restore();
    }


    // 用于绘制刻度线和刻度值
    private void drawScale(Canvas canvas) {
        // 保存当前画布的状态，以便后续的操作可以在此状态下进行
        canvas.save();
        // 计算每个刻度之间的角度间隔 angle：将总角度 sweepAngle 分成 30 份，表示每个刻度之间的角度间隔
        float angle = (float) sweepAngle / 30;
        // 将起始刻度点旋转到正上方（270 度）:使用 canvas.rotate() 方法将画布旋转到指定角度，以便从正上方开始绘制刻度
        canvas.rotate(-270 + startAngle);
        // 使用循环遍历每个刻度，总共有 31 个刻度，包括粗刻度和细刻度：
        for (int i = 0; i <= 30; i++) {
            // 如果当前刻度能被 3 整除，则表示为粗刻度，绘制粗刻度和刻度值
            if (i % 3 == 0) {
                // 设置画笔的描边宽度为粗刻度对应的像素值
                paint.setStrokeWidth(dp2px(4));
                // 设置画笔的透明度为 0x70
                paint.setAlpha(255);
                // 使用 canvas.drawLine() 方法绘制刻度线
                canvas.drawLine(0, -radius - (float) sweepInWidth / 2, 0, -radius + (float) sweepInWidth / 2 + dp2px(1), paint);
                // 设置刻度值的字体大小为 14sp
                paint.setTextSize(sp2px(14));
                // 调用 drawText() 方法绘制刻度值
                drawText(canvas, i * maxNum / 30 + "", paint);
            } else { // 否则为细刻度，仅绘制细刻度
                // 设置画笔的描边宽度为细刻度对应的像素值
                paint.setStrokeWidth(dp2px(2));
                // 设置画笔的透明度为 0x50
                paint.setAlpha(255);
                // 使用 canvas.drawLine() 方法绘制细刻度
                canvas.drawLine(0, -radius - (float) sweepInWidth / 2, 0, -radius + (float) sweepInWidth / 2, paint);
            }
            // 使用 canvas.rotate() 方法逆时针旋转画布，角度为刚计算出的角度间隔 angle
            canvas.rotate(angle); //逆时针
        }
        // 恢复画布之前保存的状态，以便后续的操作不受之前的状态影响
        canvas.restore();
    }


    // 用于绘制文字
    private void drawText(Canvas canvas, String heartRateStatus, Paint paint) {
        // 设置画笔样式为填充，用于绘制文字
        paint.setStyle(Paint.Style.FILL);
        // 设置文字大小为指定大小，其中 sp2px() 方法用于将 sp 单位转换为像素单位
        paint.setTextSize(sp2px(12));
        // 获取文字的宽度:measureText() 方法用于测量文字的宽度，返回的是文字的宽度值，类型为 float，相比 getTextBounds() 方法更精确
        float width = paint.measureText(heartRateStatus);
        canvas.drawText(heartRateStatus, -width / 2, -radius + dp2px(15), paint);
        // 恢复画笔样式为描边，以便后续的绘制操作
        paint.setStyle(Paint.Style.STROKE);
    }


    // 用于绘制内外两个圆环
    private void drawRound(Canvas canvas) {
        // 保存当前画布的状态，以便后续的操作可以在此状态下进行
        canvas.save();

        /* 绘制内圆 */
        // 设置画笔的透明度
        paint.setAlpha(160);
        // 设置画笔的描边宽度为 sweepInWidth，即内圆环的宽度
        paint.setStrokeWidth(sweepInWidth);
        // 创建一个矩形区域 rectf，用于定义内圆环的范围
        RectF rectf = new RectF(-radius, -radius, radius, radius);
        // 使用 canvas.drawArc() 方法绘制圆弧，参数包括矩形区域 rectf、起始角度 startAngle、扫过的角度 sweepAngle、是否包含圆心（这里设置为 false）、画笔
        canvas.drawArc(rectf, startAngle, sweepAngle, false, paint);

        /* 绘制外圆 */
        // 设置画笔的描边宽度为 sweepOutWidth，即外圆环的宽度
        paint.setStrokeWidth(sweepOutWidth);
        int w = dp2px(10);
        // 定义一个较大的矩形区域 rectf2，用于包含外圆环
        RectF rectf2 = new RectF(-radius - w, -radius - w, radius + w, radius + w);
        // 使用 canvas.drawArc() 方法绘制外圆环，参数同样包括矩形区域 rectf2、起始角度 startAngle、扫过的角度 sweepAngle、是否包含圆心（这里设置为 false）、画笔。
        canvas.drawArc(rectf2, startAngle, sweepAngle, false, paint);
        // 恢复画布之前保存的状态，以便后续的操作不受之前的状态影响
        canvas.restore();
    }


    // 用于将设备独立像素（dp）转换为像素（px）
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }


    // 用于将可伸缩像素（sp）转换为像素（px）
    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                getResources().getDisplayMetrics());
    }
}

