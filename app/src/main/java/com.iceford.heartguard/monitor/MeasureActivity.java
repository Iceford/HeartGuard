package com.iceford.heartguard.monitor;

import static com.iceford.heartguard.utils.ImageUtil.base64ToImage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kingsense.sdk.BleListener;
import com.kingsense.sdk.ecgsdk;
import com.iceford.heartguard.R;
import com.iceford.heartguard.adapter.DevicesAdapter;
import com.iceford.heartguard.auth.UserSessionManager;
import com.iceford.heartguard.data.DBHelper;
import com.iceford.heartguard.data.LatestMeasurementData;
import com.iceford.heartguard.utils.ImageUtil;
import com.iceford.heartguard.view.HeartRateDashboard;
import com.iceford.heartguard.view.LineChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MeasureActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MeasureActivity";
    private static final int REQUEST_ENABLE_BT = 1;
    String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
    // 获取测量时长
    int measureDuration = UserSessionManager.getInstance().getMeasureDuration();
    // 倒计时时长=测量时长+预备时间(5s)
    private final long countdownDuration = (measureDuration + 5) * 1000L;
    // 获取放大倍数
    int magnification = UserSessionManager.getInstance().getMagnification();
    // 数据库操作对象
    DBHelper dbHelper = new DBHelper(this);
    // 设备的默认蓝牙适配器实例
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ImageView deviceConnectionStatus;
    private ImageView devicePowerStatus;
    private TextView connectStatus;
    private TextView deviceName;
    private TextView deviceAddress;
    private HeartRateDashboard realTimeHeartRateDashboard;
    private ImageView heartbeatStatus;
    private TextView HRV_Value;
    private TextView QTC_Value;
    private TextView STRESS_Value;
    // 实时心电图
    private ConstraintLayout dataReceivePortrait;
    private LineChart lineChartPortrait;
    private LineChartView lineChartViewPortrait;
    private ImageView hourglass;
    private TextView countdown;
    private TextView min_HR;
    private TextView avg_HR;
    private TextView max_HR;
    private TextView avg_HRV;
    private TextView avg_QTC;
    private TextView avg_STRESS;
    // 设备蓝牙列表
    private ConstraintLayout availableDeviceBluetooth;
    private ImageView icAvailableDevices;
    private TextView tvAvailableDevices;
    private LinearLayout deviceBluetoothList;
    private ListView deviceBluetooth;
    // 设备适配器
    private DevicesAdapter devicesAdapter;
    // 选中连接的设备蓝牙名称
    private String currentSelectedDeviceName;
    // 选中连接的设备蓝牙地址
    private BluetoothDevice currentSelectedDeviceAddress;
    // 当前的连接状态
    private boolean currentConnectionStatus = false;
    // 设备剩余电量
    private int remainingPower;
    // SDK对象
    private ecgsdk ECGSDK;
    private Context messageContext;
    // 是否正在测量中
    private Boolean whetherMeasuring = false;
    private final BleListener bleListener = new BleListener() {
        // 搜索到设备回调
        @Override
        public void onDeviceFound(BluetoothDevice bluetoothDevice) {
            // 必须实现BleListener接口中onDeviceFound方法，这里接受搜索到的蓝牙设备
            devicesAdapter.addDevice(bluetoothDevice);
        }

        // 扫描超时回调
        @SuppressLint({"MissingPermission", "SetTextI18n"})
        @Override
        public void onDiscoveryOutTime() {
            if (devicesAdapter.getCount() > 0) {
                tvAvailableDevices.setText("搜索结束，请选择设备连接");
                deviceConnectionStatus.setImageResource(R.drawable.iv_bluetooth_on);
            } else if (devicesAdapter.getCount() == 0) {
                tvAvailableDevices.setText("未搜索到设备蓝牙信号");
                deviceConnectionStatus.setImageResource(R.drawable.iv_bluetooth_on);
            }
            // 检查ecgsdk对象是否为非空
            if (ECGSDK != null) {
                // 如果非空，则调用ecgsdk.scanStop()方法停止扫描
                ECGSDK.scanStop();
                // 为了在连接新设备之前停止当前的扫描操作，确保不会同时进行多个扫描
            }

            // 获取成功连接过的设备蓝牙列表
            List<String> connectedDevicesList = dbHelper.getConnectedDevicesByPhoneNumber(currentLoginNumber);
            // 获取搜索到的设备蓝牙列表
            List<String> searchDevicesList = devicesAdapter.getSearchDevices();

            boolean whetherFound = false;
            int position = -1;
            for (String searchDevice : searchDevicesList) {
                if (whetherFound) {
                    break; // 退出外层循环
                }
                for (String connectedDevice : connectedDevicesList) {
                    if (searchDevice.equals(connectedDevice)) {
                        position = devicesAdapter.findDevicePositionByName(searchDevice);
                        // 在这里可以对找到的位置进行处理
                        whetherFound = true;
                        break; // 退出内层循环
                    }
                }
            }
            if (position != -1) {
                // 通过devicesAdapter.getItem(i)获取点击项的数据对象，并将其转换为BluetoothDevice类型的bluetoothDevice对象
                BluetoothDevice bluetoothDevice = (BluetoothDevice) devicesAdapter.getItem(position);
                // 为了在后续的代码中使用该变量来表示当前选中的蓝牙设备
                currentSelectedDeviceAddress = bluetoothDevice;
                ECGSDK.connectDevice(currentSelectedDeviceAddress);
                tvAvailableDevices.setText("已自动连接至:" + searchDevicesList.get(position));
                deviceConnectionStatus.setImageResource(R.drawable.state_bluetooth_device_connect_success);
                batteryStatusDisplay();
                // 将bluetoothDevice对象的名称和地址设置到文本视图元素中，以显示当前选中的蓝牙设备的信息
                deviceName.setText(currentSelectedDeviceAddress.getName());
                deviceAddress.setText(currentSelectedDeviceAddress.getAddress());

                String device_name = deviceName.getText().toString();
                // 检查设备是否成功连接过
                dbHelper.checkDeviceConnectionByPhoneNumber(currentLoginNumber, device_name);
                // 更新设备连接次数
                long connectionTime = System.currentTimeMillis();
                dbHelper.UpdateConnectionRecord(currentLoginNumber, device_name, connectionTime);
            }
            // Log.e(TAG, "所有成功连接过的设备蓝牙列表:"+dbHelper.getConnectedDevices()+"\n");
            // Log.e(TAG, "搜索到的设备蓝牙列表:"+devicesAdapter.getSearchDevices()+"\n");
        }

        // 发现服务成功回调
        @Override
        public void onServiceDiscoverySucceed(BluetoothGatt bluetoothGatt, int i) {
            currentConnectionStatus = true;
            connectStatus.setText("连接成功");
        }

        // 连接失败回调
        @Override
        public void onConnectFailure() {
            currentConnectionStatus = false;
            connectStatus.setText("连接失败");
        }

        // 断开连接成功回调
        @Override
        public void onDisConnectSuccess() {
            currentConnectionStatus = false;
            connectStatus.setText("断开成功");
        }

        // 收到蓝牙数据回调
        @Override
        public void onReceiveMessage(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, JSONObject jsonObject) {
            try {
                JSONArray rawData = jsonObject.getJSONArray("rawData");
                remainingPower = jsonObject.getInt("power");
                Log.e(TAG, "rawData：" + rawData);
                // Log.e(TAG, "设备剩余电量："+ remainingPower);
                int hr = jsonObject.getInt("hr");
                int hrv = jsonObject.getInt("hrv");
                int qtc = jsonObject.getInt("qtc");
                int stress = jsonObject.getInt("stress");
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        realTimeHeartRateDashboard.setCurrentNumAnim(hr);
                        // digitalPower.setText("电量:" + remainingPower + "%");
                        HRV_Value.setText(hrv + "");
                        QTC_Value.setText(qtc + "");
                        STRESS_Value.setText(stress + "");

                        // 获取当前时间戳
                        long currentTimeMillis = System.currentTimeMillis();
                        double[] rawDataDoubleArray = convertJSONArrayToDoubleArray(rawData);
                        dbHelper.insertRealTimeDataByPhoneNumber(currentTimeMillis, hr, hrv, qtc, stress, rawDataDoubleArray);
                        // Log.d(TAG, standardTime+" "+currentTimeMillis+" "+hr+" "+hrv+" "+qtc+" "+stress);

                        try {
                            if (whetherMeasuring) {
                                lineChartViewPortrait.addEntryJson(new JSONArray[]{rawData});
                            }
                            // Log.e( "原始数据：", Arrays.toString(new JSONArray[]{rawData}));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };
    // 是否在测量时与设备断开链接
    private Boolean disconnectedDuringMeasurement = false;

    // onCreate 方法: 在活动创建时调用，用于初始化视图和蓝牙模块
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏活动导航栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 设置活动的布局文件
        setContentView(R.layout.activity_measure);
        // 显示用户头像
        updateDisplayAvatar();
        // 将messageContext变量赋值为当前活动的上下文对象
        messageContext = MeasureActivity.this;
        // 用于初始化视图
        initView();
        // 初始化蓝牙模块
        initBle();
        // Log.e(TAG, "测量时长："+measureDuration+"  "+"放大倍数:"+magnification);
    }


    private void initView() {
        // 顶部状态栏: 连接设备名称和地址、连接状态和设备电量
        ConstraintLayout statusBar = findViewById(R.id.status_bar);

        ImageButton returnPrevious = findViewById(R.id.return_previous);
        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView userName = findViewById(R.id.user_name);
        deviceName = findViewById(R.id.device_name);
        deviceAddress = findViewById(R.id.device_address);
        connectStatus = findViewById(R.id.connect_status);
        deviceConnectionStatus = findViewById(R.id.device_bluetooth_state);

        TextView digitalPower = findViewById(R.id.digital_power);
        digitalPower.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MeasureActivity.this, "当前设备剩余电量:" + remainingPower + "%", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        devicePowerStatus = findViewById(R.id.device_battery_status);
        hourglass = findViewById(R.id.hourglass);
        countdown = findViewById(R.id.countdown);
        setTextViewHeightBasedFontSize(countdown, 0.80f);

        // 实时监测数据:心率、心律、QT间期、心理压力
        ConstraintLayout dataStatus = findViewById(R.id.data_status);
        realTimeHeartRateDashboard = findViewById(R.id.real_time_heart_rate_dashboard);
        heartbeatStatus = findViewById(R.id.heartbeat_status);

        TextView HRV = findViewById(R.id.hrv);
        setTextViewHeightBasedFontSize(HRV, 0.60f);
        HRV_Value = findViewById(R.id.hrv_value);
        setTextViewHeightBasedFontSize(HRV_Value, 0.8f);
        TextView HRV_Unit = findViewById(R.id.hrv_unit);
        setTextViewHeightBasedFontSize(HRV_Unit, 0.8f);

        TextView QTC = findViewById(R.id.qtc);
        setTextViewHeightBasedFontSize(QTC, 0.60f);
        QTC_Value = findViewById(R.id.qtc_value);
        setTextViewHeightBasedFontSize(QTC_Value, 0.8f);
        TextView QTC_Unit = findViewById(R.id.qtc_unit);
        setTextViewHeightBasedFontSize(QTC_Unit, 0.8f);

        TextView STRESS = findViewById(R.id.stress);
        setTextViewHeightBasedFontSize(STRESS, 0.60f);
        STRESS_Value = findViewById(R.id.stress_value);
        setTextViewHeightBasedFontSize(STRESS_Value, 0.8f);
        TextView STRESS_Unit = findViewById(R.id.stress_unit);
        setTextViewHeightBasedFontSize(STRESS_Unit, 0.8f);

        String[] legend = {"ECG"};
        dataReceivePortrait = findViewById(R.id.data_receive_portrait);
        lineChartPortrait = findViewById(R.id.lineChart_portrait);
        lineChartViewPortrait = new LineChartView(lineChartPortrait, this, legend, 2000, new float[]{0, 1.8f});

        // 诊断结果
        ConstraintLayout diagnosticResult = findViewById(R.id.diagnostic_result);
        TextView measurementRecords = findViewById(R.id.measurement_records);
        min_HR = findViewById(R.id.tv_min_hr);
        avg_HR = findViewById(R.id.tv_avg_hr);
        max_HR = findViewById(R.id.tv_max_hr);
        avg_HRV = findViewById(R.id.tv_avg_hrv);
        avg_QTC = findViewById(R.id.tv_avg_qtc);
        avg_STRESS = findViewById(R.id.tv_avg_stress);

        availableDeviceBluetooth = findViewById(R.id.available_device_bluetooth);
        icAvailableDevices = findViewById(R.id.ic_available_devices);
        tvAvailableDevices = findViewById(R.id.tv_available_devices);
        setTextViewHeightBasedFontSize(tvAvailableDevices, 0.60f);
        deviceBluetoothList = findViewById(R.id.device_bluetooth_list);
        deviceBluetooth = findViewById(R.id.device_bluetooth);
        devicesAdapter = new DevicesAdapter(messageContext);
        deviceBluetooth.setAdapter(devicesAdapter);

        // 设置当前用户姓名
        String currentLoginNumber = UserSessionManager.getInstance().getPhoneNumber();
        userName.setText(dbHelper.getUserNameByPhoneNumber(currentLoginNumber));

        deviceBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @SuppressLint({"MissingPermission", "SetTextI18n"})
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 通过devicesAdapter.getItem(i)获取点击项的数据对象，并将其转换为BluetoothDevice类型的bluetoothDevice对象
                BluetoothDevice bluetoothDevice = (BluetoothDevice) devicesAdapter.getItem(i);
                // 检查ecgsdk对象是否为非空
                if (ECGSDK != null) {
                    // 如果非空，则调用ecgsdk.scanStop()方法停止扫描
                    ECGSDK.scanStop();
                    // 为了在连接新设备之前停止当前的扫描操作，确保不会同时进行多个扫描
                }
                // 将bluetoothDevice对象的名称和地址设置到文本视图元素中，以显示当前选中的蓝牙设备的信息
                deviceName.setText(bluetoothDevice.getName());
                deviceAddress.setText(bluetoothDevice.getAddress());

                currentSelectedDeviceName = deviceName.getText().toString();
                // 检查设备是否成功连接过
                dbHelper.checkDeviceConnectionByPhoneNumber(currentLoginNumber, currentSelectedDeviceName);
                // 更新设备连接次数
                long connectionTime = System.currentTimeMillis();
                dbHelper.UpdateConnectionRecord(currentLoginNumber, currentSelectedDeviceName, connectionTime);
                // 为了在后续的代码中使用该变量来表示当前选中的蓝牙设备
                currentSelectedDeviceAddress = bluetoothDevice;

            }
        });

        // 底部操作按钮
        ConstraintLayout actionButton = findViewById(R.id.action_button);
        Button searchDevice = findViewById(R.id.search_device);
        setTextViewHeightBasedFontSize(searchDevice, 0.35f);

        Button connectDevice = findViewById(R.id.connect_device);
        setTextViewHeightBasedFontSize(connectDevice, 0.35f);

        Button startMeasure = findViewById(R.id.start_measure);
        setTextViewHeightBasedFontSize(startMeasure, 0.35f);

        Button disconnectDevice = findViewById(R.id.disconnect_device);
        setTextViewHeightBasedFontSize(disconnectDevice, 0.35f);


        searchDevice.setOnClickListener(this);
        connectDevice.setOnClickListener(this);
        startMeasure.setOnClickListener(this);
        disconnectDevice.setOnClickListener(this);
    }


    @SuppressLint({"MissingPermission", "NonConstantResourceId", "SetTextI18n"})
    @Override
    // 处理按钮的点击事件
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_device:
                availableDeviceBluetooth.setVisibility(View.VISIBLE);
                icAvailableDevices.setVisibility(View.VISIBLE);
                tvAvailableDevices.setVisibility(View.VISIBLE);
                tvAvailableDevices.setText("可用设备列表");
                deviceBluetoothList.setVisibility(View.VISIBLE);
                deviceBluetooth.setVisibility(View.VISIBLE);

                dataReceivePortrait.setVisibility(View.GONE);
                hourglass.setVisibility(View.GONE);
                countdown.setVisibility(View.GONE);
                lineChartPortrait.setVisibility(View.GONE);


                searchBtDevice();
                whetherEnableBluetooth();
                deviceConnectionStatus.setImageResource(R.drawable.state_bluetooth_device_search);
                Toast.makeText(this, "正在搜索设备", Toast.LENGTH_SHORT).show();
                break;

            case R.id.connect_device:
                tvAvailableDevices.setText("已连接至设备:" + currentSelectedDeviceName);
                deviceConnectionStatus.setImageResource(R.drawable.state_bluetooth_device_connect_success);
                whetherEnableBluetooth();
                batteryStatusDisplay();
                // 当前未连接蓝牙设备
                if (!currentConnectionStatus) {
                    // 放大倍数
                    ECGSDK.setMultiple(magnification);
                    if (!ECGSDK.connectDevice(currentSelectedDeviceAddress)) {
                        // 如果连接失败，将设备连接状态图标设置为连接失败，提示消息“连接失败”
                        Toast.makeText(this, "请先搜索设备后再连接", Toast.LENGTH_SHORT).show();
                        deviceConnectionStatus.setImageResource(R.drawable.state_bluetooth_device_connect_failed);
                        whetherEnableBluetooth();
                    }
                } else {
                    // 如果当前已连接到蓝牙设备，提示消息“当前设备已连接”
                    Toast.makeText(this, "当前设备已连接", Toast.LENGTH_SHORT).show();
                    deviceConnectionStatus.setImageResource(R.drawable.state_bluetooth_device_connect_success);
                    whetherEnableBluetooth();
                }
                break;

            case R.id.start_measure:
                if (whetherMeasuring) {
                    Toast.makeText(this, "请等待本次测量结束再开始", Toast.LENGTH_SHORT).show();
                } else {
                    availableDeviceBluetooth.setVisibility(View.GONE);
                    icAvailableDevices.setVisibility(View.GONE);
                    tvAvailableDevices.setVisibility(View.GONE);
                    deviceBluetoothList.setVisibility(View.GONE);
                    deviceBluetooth.setVisibility(View.GONE);

                    dataReceivePortrait.setVisibility(View.VISIBLE);
                    lineChartPortrait.setVisibility(View.VISIBLE);
                    if (!currentConnectionStatus) {
                        Toast.makeText(this, "测量需要连接设备", Toast.LENGTH_SHORT).show();
                    } else {
                        hourglass.setVisibility(View.VISIBLE);
                        countdown.setVisibility(View.VISIBLE);
                        showInstructionsBottomSheetDialog();
                    }
                }
                break;

            case R.id.disconnect_device:
                if (currentConnectionStatus) {
                    ECGSDK.disconnectDevice();
                    if (whetherMeasuring) {
                        disconnectedDuringMeasurement = true;
                        Toast.makeText(this, "设备连接中断，测量提前结束", Toast.LENGTH_SHORT).show();
                    }
                    deviceConnectionStatus.setImageResource(R.drawable.state_bluetooth_device_disconnect);
                    whetherEnableBluetooth();
                } else {
                    deviceConnectionStatus.setImageResource(R.drawable.iv_bluetooth_on);
                    Toast.makeText(this, "当前未与设备连接", Toast.LENGTH_SHORT).show();
                    whetherEnableBluetooth();
                }
                break;
        }
    }


    @SuppressLint("MissingPermission")
    // 开始搜索设备蓝牙，更新UI以显示搜索状态
    private void searchBtDevice() {
        if (!bluetoothAdapter.isEnabled()) {
            // 蓝牙未打开，弹出请求打开蓝牙的系统对话框
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // 若当前蓝牙是否处于正在扫描状态
            if (ECGSDK.isDiscovery()) {
                // 停止蓝牙扫描
                ECGSDK.scanStop();
            }
            // 清空设备列表
            if (devicesAdapter != null) {
                devicesAdapter.clear();
            }
            // 开始搜索
            connectStatus.setText("开始搜索");
            ECGSDK.scanDevice();
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    // 当活动结束时，断开与蓝牙设备的连接
    public void finish() {
        super.finish();
        ECGSDK.disconnectDevice();
    }


    // 初始化蓝牙模块，设置蓝牙权限，并检查设备是否支持蓝牙和蓝牙是否已启用
    @SuppressLint("MissingPermission")
    public void initBle() {
        // 初始化蓝牙,bleListener回调接口，按需求对其进行实现
        ECGSDK = new ecgsdk();
        ECGSDK.init(messageContext, bleListener);
        // 打开相关权限
        ECGSDK.openPermission();

        // 设备不支持低功耗蓝牙
        if (!ECGSDK.checkBLE()) {
            deviceConnectionStatus.setImageResource(R.drawable.state_not_support_bluetooth);
            Toast.makeText(messageContext, "该设备不支持低功耗蓝牙", Toast.LENGTH_LONG).show();
        } else {    // 设备支持低功耗蓝牙，获取蓝牙开关状态
            if (ECGSDK.isEnable()) {   // 蓝牙已经打开
                deviceConnectionStatus.setImageResource(R.drawable.iv_bluetooth_on);

            } else if (!ECGSDK.isEnable()) {    // 蓝牙未打开
                Toast.makeText(messageContext, "请打开设备蓝牙开关", Toast.LENGTH_LONG).show();
                deviceConnectionStatus.setImageResource(R.drawable.iv_bluetooth_off);
                // 提示用户打开蓝牙
                ECGSDK.openBluetooth(false);
            }
        }
    }


    // 启动倒计时
    private void startCountdown(final long durationInMillis) {
        CountDownTimer countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                updateCountdownText(0);
            }
        }.start();
    }

    // 更新倒计时文本
    @SuppressLint("SetTextI18n")
    private void updateCountdownText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        if (seconds > measureDuration && seconds <= (millisUntilFinished / 1000)) {
            countdown.setText(seconds - measureDuration + "");
        } else if (seconds == measureDuration) {
            countdown.setText("开始");
        } else if (seconds > 0 && seconds < measureDuration) {
            countdown.setText(seconds + "");
        } else if (seconds == 0) {
            countdown.setText("结束");
        }
    }

    // 根据设备的剩余电量范围，设置相应的电池状态图标
    void batteryStatusDisplay() {
        if (remainingPower > 0 && remainingPower <= 25) {
            devicePowerStatus.setImageResource(R.drawable.state_battery_low);
            if (remainingPower > 0 && remainingPower <= 5) {
                Toast.makeText(this, "设备电量低于5%，请及时更换电池", Toast.LENGTH_SHORT).show();
            }
        } else if (remainingPower > 25 && remainingPower <= 50) {
            devicePowerStatus.setImageResource(R.drawable.state_battery_half);
        } else if (remainingPower > 50 && remainingPower <= 75) {
            devicePowerStatus.setImageResource(R.drawable.state_battery_high);
        } else if (remainingPower > 75 && remainingPower <= 100) {
            devicePowerStatus.setImageResource(R.drawable.state_battery_full);
        } else {
            devicePowerStatus.setImageResource(R.drawable.iv_device_power);
        }
    }


    // 传入的 JSON 数组转换为双精度浮点数数组
    public double[] convertJSONArrayToDoubleArray(JSONArray jsonArray) {
        double[] convertedData = new double[jsonArray.length()];
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                double value = jsonArray.getDouble(i);
                convertedData[i] = value;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertedData;
    }


    // 停止 GIF 动画并切换到静态图像
    private void cancelGifView(int gifDrawableResId, ImageView imageView) {
        if (imageView != null) {
            Glide.with(this).clear(imageView);
            if (gifDrawableResId != 0) {
                imageView.setImageResource(gifDrawableResId);
            }
        }
    }


    // 测量前提示
    @SuppressLint("InflateParams")
    private void showInstructionsBottomSheetDialog() {
        // 创建 BottomSheetDialog 对象
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        // 设置布局
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_instructions, null);
        ImageView imageView = bottomSheetView.findViewById(R.id.usage_diagram);
        Button closePrompt = bottomSheetView.findViewById(R.id.close_prompt);

        imageView.setImageResource(R.drawable.hint_usage_diagram);

        // 将布局设置给 BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetView);
        // 显示 BottomSheetDialog
        bottomSheetDialog.show();


        // 设置确定按钮的点击事件
        closePrompt.setOnClickListener(v -> {
            // 关闭 BottomSheetDialog
            bottomSheetDialog.dismiss();
            // 获取本次测量开始的时间戳
            long startTime = System.currentTimeMillis();
            // 开始测量，更新状态
            whetherMeasuring = true;
            ImageUtil.loadGifIntoImageView(MeasureActivity.this, R.drawable.gif_heartbeat, heartbeatStatus);
            ImageUtil.loadGifIntoImageView(MeasureActivity.this, R.drawable.gif_hourglass, hourglass);
            // 开始倒计时
            startCountdown(countdownDuration);

            // ECGSDK.store();

            // 使用 Handler 延迟执行
            new Handler().postDelayed(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    // ECGSDK.stopStore();
                    // 测量时中断连接则不统计本次测量结果
                    if (disconnectedDuringMeasurement) {
                        avg_HR.setText("平均心率:-- bpm");
                        min_HR.setText("最低心率:-- bpm");
                        max_HR.setText("最高心率:-- bpm");
                    } else {
                        // 获取本次测量结束的时间戳
                        long endTime = System.currentTimeMillis();
                        cancelGifView(R.drawable.gif_heartbeat, heartbeatStatus);
                        cancelGifView(R.drawable.gif_hourglass, hourglass);
                        dbHelper.calculateHRStats(currentLoginNumber, startTime + 5000, endTime, measureDuration);
                        // 测量结束，更新状态
                        whetherMeasuring = false;
                        LatestMeasurementData latestMeasurementData = dbHelper.getLatestMeasurementDataByPhoneNumber(currentLoginNumber);
                        min_HR.setText("最低心率:" + latestMeasurementData.getMinHR() + " bpm");
                        avg_HR.setText("平均心率:" + latestMeasurementData.getAvgHR() + " bpm");
                        max_HR.setText("最高心率:" + latestMeasurementData.getMaxHR() + " bpm");

                        avg_HRV.setText("平均节律:" + latestMeasurementData.getAvgHRV() + " a.u");
                        avg_QTC.setText("平均QT间期:" + latestMeasurementData.getAvgQTC() + " m.s");
                        avg_STRESS.setText("平均节律:" + latestMeasurementData.getAvgStress() + " a.u");

                    }
                }
            }, countdownDuration); // 倒计时时长=测量时长+预备时间
        });
    }


    // 更新显示用户头像
    public void updateDisplayAvatar() {
        String base64Avatar = dbHelper.getAvatarBase64ByPhoneNumber(currentLoginNumber);
        ImageView userAvatar = findViewById(R.id.user_avatar);
        if (base64Avatar == null) {
            // 设置默认头像
            userAvatar.setImageResource(R.drawable.iv_man_on_a_trail);
        } else {
            userAvatar.setImageBitmap(base64ToImage(base64Avatar));
        }
    }


    // 判断手机蓝牙是否被关闭
    private void whetherEnableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(messageContext, "蓝牙已关闭", Toast.LENGTH_LONG).show();
            deviceConnectionStatus.setImageResource(R.drawable.iv_bluetooth_off);
        }
    }


    // 重写onActivityResult来处理用户响应
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // 用户同意打开蓝牙
                Toast.makeText(this, "蓝牙已被打开", Toast.LENGTH_SHORT).show();
            } else {
                // 用户拒绝打开蓝牙或操作失败
                Toast.makeText(this, "需要蓝牙权限以继续", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // 根据 TextView 的高度动态调整字体的大小，以适应不同的布局需求
    private void setTextViewHeightBasedFontSize(final TextView textView, final float scale) {
        textView.post(() -> {
            float height = textView.getHeight();
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, height * scale);
        });
    }

}