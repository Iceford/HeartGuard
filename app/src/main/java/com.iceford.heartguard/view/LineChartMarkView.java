package com.iceford.heartguard.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.iceford.heartguard.R;


/**
 * 用于在 Android 的图表库 MPAndroidChart 中显示一个标记视图
 */

@SuppressLint("ViewConstructor")
public class LineChartMarkView extends MarkerView {
    // 一个 TextView 对象，用于显示标记的值
    private final TextView tvValue;
    // 一个轴值格式化器接口，用于对X轴的值进行自定义格式化
    private final IAxisValueFormatter xAxisValueFormatter;


    // 用于设置 X 轴值的格式
    public LineChartMarkView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        // 调用父类 MarkerView 的构造函数，并传入 Context 对象和布局资源 R.layout.make_view。
        // 这个布局资源定义了标记视图的外观
        super(context, R.layout.chart_make_view);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvValue = findViewById(R.id.tv_value);
    }


    // 告诉 Android Lint 工具忽略指定的警告，这里是忽略使用字符串连接可能会产生的本地化问题
    @SuppressLint("SetTextI18n")
    @Override
    // 重写 MarkerView 类的 refreshContent 方法，该方法用于更新标记视图的内容
    public void refreshContent(Entry e, Highlight highlight) {
        // 调用父类的 refreshContent 方法，传入 Entry 对象和 Highlight 对象
        super.refreshContent(e, highlight);
        // 设置 tvValue 的文本内容为 Entry 对象的 Y 值。这里将 Y 值转换为字符串，以便在 TextView 中显示
        tvValue.setText(e.getY() + "");
    }


    @Override
    // 用于确定标记视图相对于触摸点的偏移量
    public MPPointF getOffset() {
        // 这个对象指定了标记视图的偏移量。通过这种方式，标记视图将会以触摸点为中心显示。
        return new MPPointF(-((float) getWidth() / 2), -getHeight());
    }

}
