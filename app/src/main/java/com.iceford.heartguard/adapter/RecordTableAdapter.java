package com.iceford.heartguard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iceford.heartguard.R;
import com.iceford.heartguard.data.TableRecordHeader;

import java.util.List;


// 一个用于RecyclerView的适配器类，用于显示一个表格的数据
public class RecordTableAdapter extends RecyclerView.Adapter<RecordTableAdapter.ViewHolder> {
    private final List<TableRecordHeader> tableDataList;

    // 初始化适配器中的数据集合 tableDataList
    public RecordTableAdapter(List<TableRecordHeader> tableRecordHeaderList) {
        this.tableDataList = tableRecordHeaderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 通过父容器获取上下文对象
        Context context = parent.getContext();
        // 使用上下文对象创建 LayoutInflater 实例，用于从 XML 中创建布局视图对象
        LayoutInflater inflater = LayoutInflater.from(context);

        // 用于存储创建的布局视图
        View itemView;
        // 根据 viewType 的值来判断要创建的 item 类型，
        if (viewType == 0) {
            // 表头
            itemView = inflater.inflate(R.layout.item_table_record_header, parent, false);
        } else {
            // 表行
            itemView = inflater.inflate(R.layout.item_table_record_row, parent, false);
        }

        // 创建一个新的 ViewHolder 实例，将 itemView 作为参数传递给它，并返回该实例
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position == 0) {
            // 表头
            holder.measureTime.setText("测量时间");
            holder.duration.setText("时长(s)");
            holder.minHR.setText("最低心率(bpm)");
            holder.avgHR.setText("平均心率(bpm)");
            holder.maxHR.setText("最高心率(bpm)");
            holder.avgHRV.setText("平均心率变异性(a.u)");
            holder.avgQTC.setText("平均校正QT间期(ms)");
            holder.avgSTRESS.setText("平均压力(a.u)");

        } else {
            // 表行
            TableRecordHeader tableRecordHeader = tableDataList.get(position - 1);

            // 将数据设置到视图
            holder.measureTime.setText(tableRecordHeader.getMeasureTime());
            holder.duration.setText(tableRecordHeader.getDuration());
            holder.minHR.setText(String.valueOf(tableRecordHeader.getMinHR()));
            holder.avgHR.setText(String.valueOf(tableRecordHeader.getAvgHR()));
            holder.maxHR.setText(String.valueOf(tableRecordHeader.getMaxHR()));

            holder.avgHRV.setText(String.valueOf(tableRecordHeader.getAvgHRV()));
            holder.avgQTC.setText(String.valueOf(tableRecordHeader.getAvgQTC()));
            holder.avgSTRESS.setText(String.valueOf(tableRecordHeader.getAvgSTRESS()));
        }
    }

    @Override
    // 回的是列表项的总数
    public int getItemCount() {
        return tableDataList.size() + 1;
    }

    // 根据位置来返回不同的视图类型
    @Override
    public int getItemViewType(int position) {
        // 0 表示头部视图，1 表示表格行视图
        return position == 0 ? 0 : 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView measureTime;
        public TextView duration;
        public TextView minHR;
        public TextView avgHR;
        public TextView maxHR;
        public TextView avgHRV;
        public TextView avgQTC;
        public TextView avgSTRESS;

        public ViewHolder(View itemView) {
            super(itemView);

            // 查找布局中的视图
            measureTime = itemView.findViewById(R.id.measure_time);
            duration = itemView.findViewById(R.id.duration);
            minHR = itemView.findViewById(R.id.min_HR);
            avgHR = itemView.findViewById(R.id.avg_HR);
            maxHR = itemView.findViewById(R.id.max_HR);

            avgHRV = itemView.findViewById(R.id.avg_HRV);
            avgQTC = itemView.findViewById(R.id.avg_QTC);
            avgSTRESS = itemView.findViewById(R.id.avg_STRESS);

        }
    }
}

