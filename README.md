<!--
 * @Author: WhimsyQuester rongquanhuang01@gmail.com
 * @Date: 2024-05-15 03:37:03
 * @LastEditors: WhimsyQuester rongquanhuang01@gmail.com
 * @LastEditTime: 2024-05-28 11:09:00
 * @FilePath: \HeartGuard\README.md
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
# 1.芯森微 KSECG-DK 介绍

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KSECG-DK评估开发板是深圳芯森微电子有限公司推出的基于高精度心电传感芯片KS1081指尖蓝牙心电ECG信号采集评估板。KSECG-DK板集成了KS1081单通道心电芯片和蓝牙5.0处理器(Nordic nRF528XX)以及电源管理系统。用户通过手指触碰评估板上的印制金属电极，或者导联线接入电极实现高精度的心电图ECG信号(R-Q-R-S-T波)采集，并通过蓝牙数据传输和手机端APP软件实现实时人体指尖高保真心电ECG波形显示和信号分析。

![KSECG-DKO1E](assets\KSECG-DKO1E.png)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KSECG-DK是一款二合一的蓝牙心电评估开发板。支持指尖接触金属电极获取信号，以及支持通过专用心电ECG导联线使用传统AgCI电极或其他织物、金属、导电布电极从人体获取高精度心电ECG信号进行评测。用户可以在官方淘宝店铺购买该产品，购买链接：[KS1081手指心电指尖蓝牙无线ECG开发心率数据采集SDK算法手机app]([淘宝店铺购买链接地址](https://item.taobao.com/item.htm?id=621624018377&spm=a1z10.1-c.w4004-22915527887.4.23164e45hSs0N4))。

![KSECG-DKO1E](assets\KS1081手指心电指尖蓝牙无线ECG开发心率数据采集SDK算法手机app.png)




# 2.监测指标介绍

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心率监测中的"HR"、"Rhythm"、"QTc"和"Stress"是心电图分析的一部分，它们代表了心脏功能的不同方面：

(1) [心率](https://baike.baidu.com/item/%E5%BF%83%E7%8E%87/9517637)（HR，Heart rate）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心率是指每分钟心跳的次数，用来衡量心脏活动的强度。正常成年人安静状态下的心率一般在60到100次/分钟之间。运动、情绪变化、健康状况等都可以影响心率。

(2) [心率变异性](https://baike.baidu.com/item/%E5%BF%83%E7%8E%87%E5%8F%98%E5%BC%82%E6%80%A7/4437748)（HRV，Heart Rate Variability）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心率变异性是指心跳间隔时间的变化，它是心脏适应生理和环境变化能力的一个指标。高的HRV通常被认为是身体健康、恢复能力强的标志，而低的HRV则可能指示压力、疲劳或其他健康问题。HRV是通过测量连续心跳之间的时间间隔（R-R间期）的变异来计算的。

(3) [QTc间期](https://baike.baidu.com/item/QTc%E9%97%B4%E6%9C%9F/5874856)（QTc，QTc interval）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;QT间期是心电图上从QT波段开始到T波段结束的时间，它代表心脏心室去极化和复极化的总时间。QT间期会随心率的变化而变化，因此通常使用QTc值来表示校正后的QT间期，以便在不同心率下进行比较。QTc的异常延长或缩短都可能是心脏疾病的风险因素。

(4) [压力指数]()（Stress）

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;压力指数是通过分析心率变异性来评估个体的压力水平。它基于心率变异性参数，如低频/高频比率(LF/HF比率)等，这些参数可以反映自主神经系统的活动状态。自主神经系统控制着身体的无意识活动，如心跳和呼吸。压力指数较高通常意味着较高的心理或生理压力水平。



# 3.APP页面展

## 3.1 欢迎、注册和登录页面
<div style="display:flex;">
    <img src="assets\欢迎页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\注册页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\登录页面.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首次启动应用时，您将看到欢迎页面，持续3秒或点击右上角"跳过"按钮，即可快速进入监测主页。注册时请使用手机号码，并勾选同意相关政策和隐私协议。

## 3.2 监测页面
<div style="display:flex;">
    <img src="assets\监测主页.jpg" style="width:48%;">
    <div style="width:2%;"></div>
    <img src="assets\测量页面.jpg" style="width:48%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测主页直观显示用户基本信息和上次测量结果。点击"开始测量"，即可跳转至心率测量页面。

<div style="display:flex;">
    <img src="assets\实时监测数据.jpg" style="width:48%;">
    <div style="width:2%;"></div>
    <img src="assets\实时心电图.jpg" style="width:48%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在测量页面，先连接设备，然后点击"测量"按钮，即可开始心率监测。测量时，心电图、监测指标和倒计时将实时展示。完成后，系统将自动生成并展示测量报告。

<div style="display:flex;">
    <img src="assets\设备蓝牙连接.jpg"  style="width:28%;">
    <div style="width:2%;"></div>
    <img src="assets\温馨提示.jpg" style="width:28%;">
    <div style="width:2%;"></div>
    <img src="assets\测量报告.jpg"  style="width:40%;">
</div>

## 3.3 报告页面
<div style="display:flex;">
    <img src="assets\报告页面.jpg"  style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\心率记录.jpg"  style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\ECG心电图.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报告页面包含心率记录、ECG心电图、健康知识及疾病预防四个部分。心率记录以表格形式展示每次测量的报告。ECG心电图支持左右滑动和缩放，方便查看心电图动态。

<div style="display:flex;">
    <img src="assets\健康知识.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\什么是心率.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\疾病预防.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康知识页面提供与心率相关的丰富资讯，而疾病预防部分则聚焦于心脏健康，提供一系列针对性的预防策略和建议。

## 3.4 我的页面

<div style="display:flex;">
    <img src="assets\我的页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\更换头像.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\基础信息.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"我的"页面为用户提供了全面控制权，包括个人资料、应用设置和账号管理。用户可以轻松更换头像、更新信息、调整测量设置、升级应用、更改密码、管理账号、清理缓存、提交问题反馈以及安全退出登录。


<div style="display:flex;">
    <img src="assets\测量设置.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\关于应用.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets\版本更新.jpg" style="width:32%;">
</div>
<br>


# 3.数据存储

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本应用在运行过程中，将自动生成并存储数据至内置的SQLite数据库，即HeartGuard.db。这些数据经过精心组织，分布在四张结构化的表中，确保了数据的有序性和易于管理。


<br>用户信息表（UserInfo）用于存储用户的基本信息。
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


<br>连接设备表（ConnectedDevices）旨在记录设备与应用程序之间的信息。

| 列名                 | 数据类型 | 说明                 |
| -------------------- | -------- | -------------------- |
| phone_number         | TEXT     | 主键，电话号码       |
| device_name          | TEXT     | 设备名称             |
| connect_count        | INTEGER  | 连接次数             |
| last_connection_time | INTEGER  | 最后一次连接的时间戳 |


<br>实时监测数据表（RealTimeData）用于捕获和存储心率监测中的监测参数。

| 列名                     | 数据类型 | 说明                         |
| ------------------------ | -------- | ---------------------------- |
| timestamp                | INTEGER  | 时间戳，用于记录数据的时间点 |
| hr                       | INTEGER  | 心率值                       |
| hrv                      | INTEGER  | 心率变异性                   |
| qtc                      | INTEGER  | 校正QT间期                   |
| stress                   | INTEGER  | 压力                         |
| raw_data_0 ~ raw_data_19 | REAL     | 采集的心电图的原始数据       |


<br>测量统计表（MeasurementStatistics）用于存储心率监测的相关统计数据。

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