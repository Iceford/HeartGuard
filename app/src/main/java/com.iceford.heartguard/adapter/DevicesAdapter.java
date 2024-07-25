package com.iceford.heartguard.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.RequiresPermission;
import com.iceford.heartguard.R;

import java.util.ArrayList;
import java.util.List;

//用于显示蓝牙设备列表的自定义适配器，用于在Android应用程序的ListView或GridView中展示蓝牙设备的列表
public class DevicesAdapter extends BaseAdapter {
    // 上下文对象
    private final Context context;
    // 一个存储设备蓝牙的列表
    private final List<BluetoothDevice> bluetoothDeviceList;

    public DevicesAdapter(Context context) {
        this.context = context;
        bluetoothDeviceList = new ArrayList<>();
    }

    @Override
    // 返回列表中的项数
    public int getCount() {
        return bluetoothDeviceList.size();
    }

    @Override
    // 返回指定位置的项
    public Object getItem(int i) {
        return bluetoothDeviceList.get(i);
    }

    @Override
    // 返回指定位置的项的ID
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    // 调用这个方法需要蓝牙连接的权限,这意味着这段代码可能用于处理蓝牙设备的UI展示
    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    // 用于创建或重用列表项的视图，并将数据绑定到视图上
    public View getView(int i, View view, ViewGroup viewGroup) {
        DeviceViewHolder viewHolder;
        if (view == null) {
            // 检查用来判断传入的view是否为空。如果为空，这意味着没有可复用的视图，因此需要创建一个新的视图，并且初始化viewHolder
            view = LayoutInflater.from(context).inflate(R.layout.item_device_bluetooth, null);
            viewHolder = new DeviceViewHolder();
            viewHolder.tvDeviceName = view.findViewById(R.id.device_name);
            viewHolder.tvDeviceAddress = view.findViewById(R.id.device_address);
            view.setTag(viewHolder);
        } else {
            // 如果 view 不为空，说明当前视图是可重用的，我们可以直接从 view.getTag() 方法获取之前绑定的 viewHolder 对象
            viewHolder = (DeviceViewHolder) view.getTag();
        }

        // 检查列表中位置为i的设备名称是否为空。如果为空，则使用默认文本"NULL"
        if (bluetoothDeviceList.get(i).getName() == null) {
            viewHolder.tvDeviceName.setText("NULL");
        } else {
            // 置设备名称和地址到对应的TextView中
            viewHolder.tvDeviceName.setText("  " + bluetoothDeviceList.get(i).getName());

        }
        viewHolder.tvDeviceAddress.setText("  " + bluetoothDeviceList.get(i).getAddress());
//        Log.e("BluetoothDevice", bluetoothDeviceList.get(i).toString());
//        Log.e("BluetoothDeviceList", "Device Name: " + bluetoothDeviceList.get(i).getName() + ", Device Address: " + bluetoothDeviceList.get(i).getAddress());
        // 返回更新后的视图
        return view;
    }

    // 用于向列表中添加设备
    public void addDevice(BluetoothDevice bluetoothDevice) {
        if (!bluetoothDeviceList.contains(bluetoothDevice)) {
            bluetoothDeviceList.add(bluetoothDevice);
        }
        notifyDataSetChanged(); //刷新
    }

    // 用于清空列表
    public void clear() {
        bluetoothDeviceList.clear();
        notifyDataSetChanged(); //刷新
    }

    // 获取所有搜索到的设备蓝牙名称
    public List<String> getSearchDevices() {
        List<String> devicesNameList = new ArrayList<>();
        for (BluetoothDevice device : bluetoothDeviceList) {
            @SuppressLint("MissingPermission")
            String deviceName = device.getName() != null ? device.getName() : "NULL";
            devicesNameList.add(deviceName);
        }
        return devicesNameList;
    }

    // 获取名称匹配相同的设备蓝牙的列表位置
    @SuppressLint("MissingPermission")
    public int findDevicePositionByName(String deviceName) {
        for (int i = 0; i < bluetoothDeviceList.size(); i++) {
            BluetoothDevice device = bluetoothDeviceList.get(i);
            if (device.getName() != null && device.getName().equals(deviceName)) {
                return i; // 返回匹配设备名称的位置
            }
        }
        return -1; // 如果未找到匹配的设备名称，返回-1
    }

    // 静态内部类
    static class DeviceViewHolder {
        // 设备名称
        TextView tvDeviceName;
        // 设备地址
        TextView tvDeviceAddress;
    }
}

