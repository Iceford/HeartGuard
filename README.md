# 1.芯森微 KSECG-DK 介绍

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KSECG-DK 评估开发板是深圳芯森微电子有限公司推出的基于高精度心电传感芯片 KS1081 指尖蓝牙心电ECG信号采集评估板。KSECG-DK 板集成了 KS1081 单通道心电芯片和蓝牙5.0处理器(Nordic nRF528XX)以及电源管理系统。用户通过手指触碰评估板上的印制金属电极，或者导联线接入电极实现高精度的心电图 ECG 信号(R-Q-R-S-T波)采集，并通过蓝牙数据传输和手机端APP软件实现实时人体指尖高保真心电ECG波形显示和信号分析。

![KSECG-DKO1E](assets/KSECG-DKO1E.png)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KSECG-DK 是一款二合一的蓝牙心电评估开发板。支持指尖接触金属电极获取信号，以及支持通过专用心电 ECG 导联线使用传统 AgCI 电极或其他织物、金属、导电布电极从人体获取高精度心电 ECG 信号进行评测。用户可以在淘宝店铺购买该产品，商品链接：[KS1081手指心电指尖蓝牙无线ECG开发心率数据采集SDK算法手机app](https://item.taobao.com/item.htm?id=621624018377&spm=a1z10.1-c.w4004-22915527887.4.23164e45hSs0N4)

![KSECG-DKO1E](assets/KS1081手指心电指尖蓝牙无线ECG开发心率数据采集SDK算法手机app.png)




# 2.监测指标

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在评估心脏健康和生理状态时，心率（HR）及其衍生指标起着关键作用。心率，即心跳次数每分钟，是心脏活动强度的直接反映。心率变异性（HRV）显示心脏对生理和环境变化的适应性，通过检测心跳间隔的波动来衡量。心电图上的QTc间期，校正后的心室去极化和复极化时间，是不同心率下心脏功能比较的重要指标。压力指数，基于HRV参数，如 LF/HF 比率，评估自主神经系统活动，反映个体的压力水平。这些指标综合提供了心脏健康和压力状态的全面监测。

(1) [心率](https://baike.baidu.com/item/%E5%BF%83%E7%8E%87/9517637)（HR，Heart rate）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心率是指心脏收缩跳动的频率和每分钟跳动的次数，正常人平静时（静息心率）每分钟60到100次（60~100 bpm(次/分钟)），运动时心跳会加速，心肺功能较好的运动员会比正常人的心跳要慢。

(2) [心率变异性](https://baike.baidu.com/item/%E5%BF%83%E7%8E%87%E5%8F%98%E5%BC%82%E6%80%A7/4437748)（HRV，Heart Rate Variability）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心率变异性是指逐次心跳周期差异的变化情况，它含有神经体液因素对心血管系统调节的信息，从而判断其对心血管等疾病的病情及预防，可能是预测心脏性猝死和心律失常性事件的一个有价值的指标。

(3) [QTc间期](https://baike.baidu.com/item/QTc%E9%97%B4%E6%9C%9F/5874856)（QTc，QTc interval）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;QT 间期是心电图上从QT 波段开始到 T 波段结束的时间，它代表心脏心室去极化和复极化的总时间。QT 间期会随心率的变化而变化，因此通常使用 QTc 值来表示校正后的 QT 间期，以便在不同心率下进行比较。QTc 的异常延长或缩短都可能是心脏疾病的风险因素。

(4) [压力指数]()（Stress）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;压力指数是通过分析心率变异性来评估个体的压力水平。它基于心率变异性参数，如低频/高频比率(LF/HF 比率)等，这些参数可以反映自主神经系统的活动状态。自主神经系统控制着身体的无意识活动，如心跳和呼吸。压力指数较高通常意味着较高的心理或生理压力水平。



# 3.APP页面展示

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;APP页面设计直接影响用户体验和功能认知。本应用的主要页面包括欢迎、注册登录、监测、报告和个人主页，旨在提供直观和便捷的使用体验。

## 3.1 欢迎、注册和登录页面
<div align="center" style="display:flex;">
    <img src="assets/欢迎页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/注册页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/登录页面.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首次启动应用时，您将看到欢迎页面，持续3秒或点击右上角"跳过"按钮，即可快速进入"监测"主页。注册和登录请使用手机号码，并勾选同意相关政策和隐私协议。

## 3.2 监测主页
<div align="center" style="display:flex;">
    <img src="assets/监测主页.jpg" style="width:48%;">
    <div style="width:2%;"></div>
    <img src="assets/测量页面.jpg" style="width:48%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"监测"主页直观显示用户基本信息和上次测量结果。点击"开始测量"，即可跳转至心率测量页面。

<div align="center" style="display:flex;">
    <img src="assets/实时监测数据.jpg" style="width:48%;">
    <div style="width:2%;"></div>
    <img src="assets/实时心电图.jpg" style="width:48%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在测量页面，先连接设备，然后点击"测量"按钮，即可开始心率监测。测量时，心电图、监测指标和倒计时将实时展示。完成后，系统将自动生成并展示测量报告。

<div align="center" style="display:flex;">
    <img src="assets/设备蓝牙连接.jpg"  style="width:28%;">
    <div style="width:2%;"></div>
    <img src="assets/温馨提示.jpg" style="width:28%;">
    <div style="width:2%;"></div>
    <img src="assets/测量报告.jpg"  style="width:40%;">
</div>

## 3.3 报告主页
<div align="center" style="display:flex;">
    <img src="assets/报告页面.jpg"  style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/心率记录.jpg"  style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/ECG心电图.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"报告"主页包含心率记录、ECG 心电图、健康知识及疾病预防四个部分。心率记录以表格形式展示每次测量的报告。ECG 心电图支持左右滑动和缩放，方便查看心电图动态。

<div align="center" style="display:flex;">
    <img src="assets/健康知识.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/什么是心率.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/疾病预防.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康知识页面提供与心率相关的丰富资讯，而疾病预防部分则聚焦于心脏健康，提供一系列针对性的预防策略和建议。

## 3.4 我的主页

<div align="center" style="display:flex;">
    <img src="assets/我的页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/更换头像.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/基础信息.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"我的"主页为用户提供了全面控制权，包括个人资料、应用设置和账号管理。用户可以轻松更换头像、更新信息、调整测量设置、升级应用、更改密码、管理账号、清理缓存、提交问题反馈以及安全退出登录。


<div align="center" style="display:flex;">
    <img src="assets/测量设置.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/关于应用.png" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/版本更新.jpg" style="width:32%;">
</div>
<br>


# 3.数据存储

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本应用在运行过程中，将自动生成并存储数据至内置的 SQLite 数据库，即 HeartGuard.db。这些数据分布在四张结构化的表中，确保了数据的有序性和易于管理。


<br>（1）**用户信息表**（UserInfo）用于存储用户的基本信息。
| 列名                | 数据类型 | 说明                         |
| ------------------- | -------- | ---------------------------- |
| phone_number        | TEXT     | 主键，电话号码               |
| avatar_base64       | TEXT     | 头像图片编码后的Base64字符串 |
| login_password      | TEXT     | 加密后的密码字段             |
| user_name           | TEXT     | 用户姓名                     |
| user_gender         | TEXT     | 用户性别                     |
| user_birthday       | TEXT     | 用户生日                     |
| user_height         | TEXT     | 用户身高                     |
| user_weight         | TEXT     | 用户体重                     |
| user_wear_pacemaker | TEXT     | 用户是否佩戴起搏器           |
| feedback            | TEXT     | 用户的反馈信息               |


<br>（2）**连接设备表**（ConnectedDevices）旨在记录设备与应用程序之间的信息。

| 列名                 | 数据类型 | 说明                 |
| -------------------- | -------- | -------------------- |
| phone_number         | TEXT     | 主键，电话号码       |
| device_name          | TEXT     | 设备名称             |
| connect_count        | INTEGER  | 连接次数             |
| last_connection_time | INTEGER  | 最后一次连接的时间戳 |


<br>（3）**实时监测数据表**（RealTimeData）用于捕获和存储心率监测中的监测参数。

| 列名                     | 数据类型 | 说明                         |
| ------------------------ | -------- | ---------------------------- |
| timestamp                | INTEGER  | 时间戳，用于记录数据的时间点 |
| hr                       | INTEGER  | 心率值                       |
| hrv                      | INTEGER  | 心率变异性                   |
| qtc                      | INTEGER  | 校正QT间期                   |
| stress                   | INTEGER  | 压力                         |
| raw_data_0 ~ raw_data_19 | REAL     | 采集的心电图的原始数据       |


<br>（4）**测量统计表**（MeasurementStatistics）用于存储心率监测的相关统计数据。

| 列名             | 数据类型 | 说明                   |
| ---------------- | -------- | ---------------------- |
| phone_number     | TEXT     | 电话号码               |
| measurement_time | INTEGER  | 测量的时间点(时间戳)   |
| duration         | TEXT     | 测量时长（以秒为单位） |
| min_heart_rate   | INTEGER  | 最低心率               |
| avg_heart_rate   | INTEGER  | 平均心率               |
| max_heart_rate   | INTEGER  | 最高心率               |
| avg_hrv          | DOUBLE   | 平均心率变异性         |
| avg_qtc          | DOUBLE   | 平均校正QT间期         |
| avg_stress       | DOUBLE   | 平均压力               |

# 4.安装包下载
链接: http://47.121.198.148:1024/share/LnQCMXTbYsfWyMiZymcm <br>
提取码: GNfA7