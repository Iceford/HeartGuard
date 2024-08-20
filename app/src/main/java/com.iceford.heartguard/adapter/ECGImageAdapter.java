package com.iceford.heartguard.adapter;

import static com.iceford.heartguard.utils.TimeUtil.timestampToBeijingTime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.iceford.heartguard.R;
import com.iceford.heartguard.data.DBHelper;

import java.util.ArrayList;
import java.util.List;

import kotlin.Triple;

public class ECGImageAdapter extends RecyclerView.Adapter<ECGImageAdapter.ViewHolder> {

    private static final int SAMPLING_RATE = 500; // ECG数据的采样率
    private static final int MAX_POINTS_TO_DISPLAY = 2600; // 最多展示的点数
    private final DBHelper dbHelper;
    private final List<Triple<Long, Integer, Integer>> ECGInfo;

    public ECGImageAdapter(Context context, List<Triple<Long, Integer, Integer>> ECGInfo, DBHelper dbHelper) {
        this.ECGInfo = ECGInfo;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_ecg, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Triple<Long, Integer, Integer> ecgData = ECGInfo.get(position);
        long timestamp = ecgData.getFirst();
        int duration = ecgData.getSecond();
        int averageHeartRate = ecgData.getThird();
        // 设置文本值到 TextView
        holder.MeasureTime.setText(timestampToBeijingTime(timestamp));
        holder.Duration.setText("时长:" + duration + "s");
        holder.AVG_HR.setText("平均心率:" + averageHeartRate + "bpm");
        setupLineChart(holder.ECGlineChart, timestamp, duration, averageHeartRate);
    }

    @Override
    public int getItemCount() {
        return ECGInfo.size();
    }

    private void setupLineChart(LineChart lineChart, long timestamp, int duration, int averageHeartRate) {
        lineChart.setDrawBorders(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        List<Double> rawDataList = dbHelper.getRawDataInRange(timestamp, duration);
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;
        for (double value : rawDataList) {
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < rawDataList.size(); i++) {
            float second = i / (float) SAMPLING_RATE; // 根据采样率计算时间
            entries.add(new Entry(second, rawDataList.get(i).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "ecg");
        dataSet.setColor(Color.parseColor("#D4237A"));
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(Color.WHITE);
        lineChart.getLegend().setEnabled(false); // 设置不显示图例

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(0.05f); // 设置最小间隔，防止当放大时出现重复标签
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.GRAY);
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum((float) minValue);
        yAxis.setAxisMaximum((float) maxValue);
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(Color.GRAY);
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        // 调整视图窗口大小以适应最多5000个数据点，如果需要
        float maxSecondsToShow = MAX_POINTS_TO_DISPLAY / (float) SAMPLING_RATE;
        lineChart.setVisibleXRangeMaximum(maxSecondsToShow);

        lineChart.invalidate(); // 刷新图表
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LineChart ECGlineChart;
        public TextView MeasureTime;
        public TextView Duration;
        public TextView AVG_HR;

        public ViewHolder(View v) {
            super(v);
            ECGlineChart = v.findViewById(R.id.ECG_lineChart);
            MeasureTime = v.findViewById(R.id.measure_time);
            Duration = v.findViewById(R.id.duration);
            AVG_HR = v.findViewById(R.id.avg_hr);
        }
    }

}

