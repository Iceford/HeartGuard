package com.iceford.heartguard.view;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class LineChartView {
    // LineChart 对象，用于显示折线图
    private final LineChart lineChart;
    // LineData 对象，用于存储折线图的数据
    private final LineData lineData;
    // 存储 LineDataSet 对象的列表，每个 LineDataSet 对象代表一个数据集
    private final List<LineDataSet> lineDataSets;
    // 上下文对象
    private final Context context;
    // 数据集的数量
    private final int lineNums;
    // X 轴的长度
    private final int size;
    // 用于存储折线图线条的颜色
    private final int[] COLORS = {Color.parseColor("#D4237A"), Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY};
    // X 轴和 Y 轴的对象
    private XAxis xAxis;
    private YAxis yAxis;
    // 用于跟踪数据点的索引
    private int index = 0;


    // 构造方法初始化 LineChartView 对象，设置 LineChart 的基本属性，并创建 LineDataSet 对象
    public LineChartView(LineChart mLineChart, Context messageContext, String[] legend, int Xsize, float[] boundary) {
        // 初始化 LineChartView 对象时，传入折线图对象 mLineChart、上下文 messageContext、数据集名称数组 legend、X 轴数据点数量 Xsize 和 Y 轴范围 boundary
        this.context = messageContext;
        this.lineChart = mLineChart;
        this.lineNums = legend.length; // 获取数据集名称数组的长度，即数据集数量
        this.size = Xsize; // 设置 X 轴数据点数量

        // 初始化折线图样式和属性
        initLineChart(Xsize, boundary);

        lineDataSets = new ArrayList<>(); // 创建一个存储 LineDataSet 对象的 ArrayList
        lineData = new LineData(); // 创建 LineData 对象，用于存储所有 LineDataSet 对象

        // 循环遍历数据集名称数组，为每个数据集名称创建一个 LineDataSet 对象，并添加到 lineDataSets 中，然后将 LineDataSet 添加到 LineData 中
        for (int i = 0; i < lineNums; i++) {
            lineDataSets.add(initLineDataSet(legend[i], COLORS[i])); // 初始化 LineDataSet 对象并添加到 lineDataSets
            lineData.addDataSet(lineDataSets.get(i)); // 将 LineDataSet 添加到 LineData
        }

        lineChart.setData(lineData); // 将 LineData 设置给折线图
    }


    // 初始化 LineChart 的各种属性，如图例、坐标轴范围、网格线等
    public void initLineChart(int xlength, float[] Yboundary) {
        // 获取折线图的图例对象
        Legend legend = lineChart.getLegend();
        // 调用 createLegend 方法设置图例样式和位置
        createLegend(legend);

        // 获取左侧 Y 轴对象
        yAxis = lineChart.getAxisLeft();
        // 获取 X 轴对象
        xAxis = lineChart.getXAxis();

        // 设置 X 轴的位置为底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // 设置左侧 Y 轴的最大值和最小值
        yAxis.setAxisMaximum(Yboundary[1]);
        yAxis.setAxisMinimum(Yboundary[0]);

        // 设置左侧 Y 轴的标签数量为10，不精确计算
        yAxis.setLabelCount(10, false);

        // 不绘制 Y 轴的网格线
        yAxis.setDrawGridLines(false);

        // 设置绘制折线图的背景网格线
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(Color.WHITE);

        // 对 X 轴进行设置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.GRAY);
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        // 对左侧 Y 轴进行设置
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(Color.GRAY);
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        // 设置是否绘制图表边框
        lineChart.setDrawBorders(false);

        // 禁用拖拽功能
        lineChart.setDragEnabled(false);

        // 禁用缩放功能
        lineChart.setScaleEnabled(false);

        // 禁用描述信息
        lineChart.getDescription().setEnabled(false);

        // 禁用右侧 Y 轴
        lineChart.getAxisRight().setEnabled(false);

        // 设置可见的 X 范围的最大值和最小值
        lineChart.setVisibleXRangeMaximum(xlength);
        lineChart.setVisibleXRangeMinimum(xlength);
    }


    // 初始化 LineDataSet 对象，设置线条的样式、颜色、标记等
    private LineDataSet initLineDataSet(String name, int color) {
        // 创建一个 LineDataSet 对象，传入数据集为 null，名称为 name
        LineDataSet lineDataSet = new LineDataSet(null, name);
        // 设置线条宽度为1.0f
        lineDataSet.setLineWidth(1.0f);
        // 设置不绘制数据点的圆圈
        lineDataSet.setDrawCircles(false);
        // 设置线条颜色为指定的 color
        lineDataSet.setColor(color);
        // 设置数据点圆圈的颜色为指定的 color
        lineDataSet.setCircleColor(color);
        // 设置高亮颜色为指定的 color
        lineDataSet.setHighLightColor(color);
        // 不绘制数据值
        lineDataSet.setDrawValues(false);
        // 不绘制填充区域
        lineDataSet.setDrawFilled(false);
        // 设置数据集依赖于左侧的 Y 轴
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        // 设置数值文本大小为10f
        lineDataSet.setValueTextSize(10f);
        // 设置模式为线性（Linear）
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        // 创建一个 LineChartMarkView 对象，传入 context 和 X 轴的值格式化器
        LineChartMarkView mv = new LineChartMarkView(context, xAxis.getValueFormatter());
        // 将 LineChartMarkView 与 LineChart 关联
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        // 返回创建的 LineDataSet 对象
        return lineDataSet;
    }


    // 创建图例的样式和位置
    private void createLegend(Legend legend) {
        // 设置图例的形状为线
        legend.setForm(Legend.LegendForm.LINE);
        // 设置图例文本的大小为12f
        legend.setTextSize(12f);
        // 设置图例垂直对齐方式为底部
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        // 设置图例水平对齐方式为左侧
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // 设置图例不绘制在内部
        legend.setDrawInside(false);
        // 启用图例显示
        legend.setEnabled(true);
    }


    // 向折线图中添加数据点，如果数据点数量超过设定的长度，则移除最旧的数据点
    public void addEntry(double[] nums) {
        // 获取第一个 LineDataSet 的数据点数量
        int count = lineDataSets.get(0).getEntryCount();
        // 如果数据点数量超过设定的 size
        if (count > this.size) {
            // 遍历所有 LineDataSet，移除每个数据集的第一个数据点
            for (LineDataSet lineDataSet : lineDataSets) {
                lineDataSet.removeFirst();
            }
        }
        // 遍历输入的 double 数组，将每个数据点添加到 LineData 中
        for (int i = 0; i < nums.length; i++) {
            lineData.addEntry(new Entry(index, (float) nums[i]), i);
        }
        // 增加索引
        index++;
        // 通知 LineData 数据已更改
        lineData.notifyDataChanged();
        // 通知 LineChart 数据已更改
        lineChart.notifyDataSetChanged();
        // 将视图移动到索引位置
        lineChart.moveViewToX(index);
    }


    // 从 JSON 数组中添加数据点到折线图中，处理多个数据集的情况
    public void addEntryJson(JSONArray[] nums) throws JSONException {
        // 遍历第一个 JSON 数组中的元素
        for (int i = 0; i < nums[0].length(); i++) {
            // 遍历所有 JSON 数组
            for (int j = 0; j < nums.length; j++) {
                // 从第 j 个 JSON 数组中获取第 i 个元素作为数据
                double data = nums[j].getDouble(i);
                // 获取第一个 LineDataSet 的数据点数量
                int count = lineDataSets.get(0).getEntryCount();
                // 如果数据点数量超过设定的 size
                if (count > this.size) {
                    // 移除第 j 个 LineDataSet 的第一个数据点
                    lineDataSets.get(j).removeFirst();
                }
                // 向 LineData 中添加数据点，索引为 index，数据为 data，数据集索引为 j
                lineData.addEntry(new Entry(index, (float) data), j);
            }
            // 增加索引
            index++;
        }
        // 通知 LineData 数据已更改
        lineData.notifyDataChanged();
        // 通知 LineChart 数据已更改
        lineChart.notifyDataSetChanged();
        // 将视图移动到索引位置
        lineChart.moveViewToX(index);
    }

}

