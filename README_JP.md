# 1.キングセンス KSECG-DK の紹介

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KSECG-DKボードは、KS1081シングルチャンネルECGチップとBluetooth 5.0プロセッサ（Nordic nRF528XX）および電源管理システムを統合しています。 ユーザーは、ボード上のプリント金属電極に触れるか、電極にリード線を接続することで、高精度のECG信号（R-Q-R-S-T波）を取得し、Bluetoothデータ伝送とモバイルAPPソフトウェアを介して、リアルタイムで人間の指先の高忠実度のECG波形表示と信号解析を実現する。

<div align=center><img src="assets/KSECG-DKO1E.png"></div>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KSECG-DKは2-in-1のBluetooth ECG評価開発ボードです。 金属電極を指先に接触させて信号を取得するほか、従来のAgCI電極や人体から採取した他の布、金属、導電布電極を使用して、専用のECGリードワイヤーを介して評価用の高精度ECG信号を取得することもできます。 ユーザーは淘宝網のショップでこの製品を購入することができ、製品のリンク： [KS1081指ECG指先ブルートゥースワイヤレスECG開発心拍数データ収集SDKアルゴリズム携帯電話アプリ](https://item.taobao.com/item.htm?id=621624018377&spm=a1z10.1-c.w4004-22915527887.4.23164e45hSs0N4)

<div align=center><img src="assets/KS1081手指心电指尖蓝牙无线ECG开发心率数据采集SDK算法手机app.png"></div>




# 2.モニタリング指標

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心臓の健康状態や生理的状態を評価する上で、心拍数（HR）とその誘導体が重要な役割を果たす。 心拍数、つまり 1 分あたりの心拍数は、心臓活動の強さを直接反映するものです。 心拍変動（HRV）は、生理的変化や環境変化に対する心臓の適応性を示すもので、心拍間隔の変動を検出することによって測定される。 心電図上の QTc 間隔は、心室の脱分極と再分極の時間を補正したもので、異なる心拍数における心機能の比較の重要な指標となります。 LF/HF 比などの HRV パラメータに基づくストレス指標は、自律神経系の活動を評価し、個人のストレス レベルを反映します。 これらの指標を組み合わせることで、心臓の健康状態とストレス状態を総合的にモニタリングすることができます。

(1) [心拍数](https://baike.baidu.com/item/%E5%BF%83%E7%8E%87/9517637) (HR、心拍数)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心拍数とは、心臓の収縮拍動の頻度であり、1分間の拍動数である。 正常な人の心臓の拍動は、落ち着いているとき（安静時心拍数）は1分間に60～100回（60～100bpm（ビーツ・パー・ミニッツ））であるが、運動中は心拍数が速くなり、心肺機能の優れたアスリートは、正常な人よりも心拍数が遅くなる。

(2) [心拍変動](https://baike.baidu.com/item/%E5%BF%83%E7%8E%87%E5%8F%98%E5%BC%82%E6%80%A7/4437748) (HRV、Heart Rate Variability)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;心拍変動は、心拍数の周期ごとの差の変動であり、神経体液性因子による心血管系の調節に関する情報を含んでいるため、心血管系およびその他の疾患の状態および予防への寄与を決定し、心臓突然死や不整脈発生事象を予測するための貴重な指標となりうる。

(3) [QTc間隔](https://baike.baidu.com/item/QTc%E9%97%B4%E6%9C%9F/5874856) (QTc、QTc間隔)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;QT間隔は心電図上のQT帯の始まりからT帯の終わりまでの時間であり、心臓の心室の脱分極と再分極の合計時間を表している。QT間隔は心拍数によって変化するため、異なる心拍数での比較のために補正したQT間隔を表すQTc値を用いるのが一般的である。

(4) [ストレス指数]() (ストレス)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ストレス指数は、心拍変動を分析することにより、個人のストレスレベルを評価する。 これは、低周波/高周波比（LF/HF比）などの心拍変動パラメータに基づいており、自律神経系の活動状態を反映することができる。 自律神経系は、心拍や呼吸といった身体の無意識の活動を制御している。 ストレス指数が高いということは、通常、心理的または生理的ストレスのレベルが高いことを意味する。


# 3.APPページ表示

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;アプリのページデザインは、ユーザーエクスペリエンスと機能認知に直接影響します。 このアプリの主なページには、「ようこそ」、「登録とログイン」、「モニタリング」、「レポート」、「パーソナルホーム」があり、直感的で便利なユーザー体験を提供することを目指しています。

## 3.1 ウェルカムページ、登録ページ、ログインページ
<div align="center" style="display:flex;">
    <img src="assets/欢迎页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/注册页面.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/登录页面.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;初回起動時にウェルカムページが3秒間表示されますが、右上の「スキップ」ボタンをクリックすると、すぐに「モニター」ホームページに移動します。 登録とログインには、携帯電話番号を使用し、ポリシーとプライバシー規約に同意するボックスにチェックを入れてください。


## 3.2 モニタリング・ホームページ
<div align="center" style="display:flex;">
    <img src="assets/监测主页.jpg" style="width:48%;">
    <div style="width:2%;"></div>
    <img src="assets/测量页面.jpg" style="width:48%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;モニター」のトップページには、ユーザーの基本情報と前回の測定結果が表示されます。 測定開始」をクリックすると、心拍数の測定ページにジャンプします。

<div align="center" style="display:flex;">
    <img src="assets/实时监测数据.jpg" style="width:48%;">
    <div style="width:2%;"></div>
    <img src="assets/实时心电图.jpg" style="width:48%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;測定ページでは、まずデバイスを接続し、「測定」ボタンをクリックして心拍数のモニタリングを開始します。 測定中は、ECG、モニタリングインジケータ、カウントダウン時間がリアルタイムで表示されます。 測定が終了すると、自動的に測定レポートが作成され、表示されます。

<div align="center" style="display:flex;">
    <img src="assets/设备蓝牙连接.jpg"  style="width:28%;">
    <div style="width:2%;"></div>
    <img src="assets/温馨提示.jpg" style="width:28%;">
    <div style="width:2%;"></div>
    <img src="assets/测量报告.jpg"  style="width:40%;">
</div>

## 3.3 レポート・ホームページ
<div align="center" style="display:flex;">
    <img src="assets/报告页面.jpg"  style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/心率记录.jpg"  style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/ECG心电图.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;レポート」ホームページには、「心拍記録」、「心電図」、「健康知識」、「疾病予防」の4つのセクションがあります。 心拍記録では、各測定値のレポートが表形式で表示され、心電図では、左右のスライドとズームに対応しており、心電図の動態を簡単に見ることができます。

<div align="center" style="display:flex;">
    <img src="assets/健康知识.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/什么是心率.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/疾病预防.jpg" style="width:32%;">
</div>
<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康知識のページでは心拍数に関連する豊富な情報を提供し、疾病予防のページでは心臓の健康に焦点を当て、的を絞ったさまざまな予防策やアドバイスを提供している。


<div align="center" style="display:flex;">
    <img src="assets/测量设置.jpg" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/关于应用.png" style="width:32%;">
    <div style="width:2%;"></div>
    <img src="assets/版本更新.jpg" style="width:32%;">
</div>
<br>


# 4.データの保存

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;アプリケーションは実行中に、組み込みの SQLite データベースである HeartGuard.db にデータを自動生成して保存します。これらのデータは、整理された四つのテーブルに分散されており、データの秩序と管理のしやすさが確保されています。


<br>（1）**ユーザー情報テーブル**（UserInfo）は、ユーザーの基本情報を保存するために使用されます。
| 列名                | データ型 | 説明                                             |
| ------------------- | -------- | ------------------------------------------------ |
| phone_number        | TEXT     | プライマリキー、電話番号                         |
| avatar_base64       | TEXT     | エンコードされたベース64文字列形式のアバター画像 |
| login_password      | TEXT     | 暗号化されたパスワードフィールド                 |
| user_name           | TEXT     | ユーザー名                                       |
| user_gender         | TEXT     | ユーザーの性別                                   |
| user_birthday       | TEXT     | ユーザーの誕生日                                 |
| user_height         | TEXT     | ユーザーの身長                                   |
| user_weight         | TEXT     | ユーザーの体重                                   |
| user_wear_pacemaker | TEXT     | ペースメーカーを着用しているかどうか             |
| feedback            | TEXT     | ユーザーのフィードバック情報                     |


<br>（2）**接続デバイステーブル**（ConnectedDevices）は、デバイスとアプリケーション間の情報を記録するために設計されています。

| 列名                 | データ型 | 説明                     |
| -------------------- | -------- | ------------------------ |
| phone_number         | TEXT     | プライマリキー、電話番号 |
| device_name          | TEXT     | デバイス名               |
| connect_count        | INTEGER  | 接続回数                 |
| last_connection_time | INTEGER  | 最終接続時間スタンプ     |


<br>（3）**リアルタイムデータテーブル**（RealTimeData）は、心拍数モニタリング中のパラメータをキャプチャして保存するために使用されます。

| 列名                     | データ型 | 説明                                         |
| ------------------------ | -------- | -------------------------------------------- |
| timestamp                | INTEGER  | データの時間点を記録するためのタイムスタンプ |
| hr                       | INTEGER  | 心拍数値                                     |
| hrv                      | INTEGER  | 心拍変動性                                   |
| qtc                      | INTEGER  | 補正QT間隔                                   |
| stress                   | INTEGER  | ストレス値                                   |
| raw_data_0 ~ raw_data_19 | REAL     | 収集された心電図の生データ                   |


<br>（4）**測定統計テーブル**（MeasurementStatistics）は、心拍数モニタリングに関連する統計データを保存するために使用されます。

| 列名             | データ型 | 説明                       |
| ---------------- | -------- | -------------------------- |
| phone_number     | TEXT     | 電話番号                   |
| measurement_time | INTEGER  | 測定時間（タイムスタンプ） |
| duration         | TEXT     | 測定時間（秒単位）         |
| min_heart_rate   | INTEGER  | 最低心拍数                 |
| avg_heart_rate   | INTEGER  | 平均心拍数                 |
| max_heart_rate   | INTEGER  | 最高心拍数                 |
| avg_hrv          | DOUBLE   | 平均心拍変動性             |
| avg_qtc          | DOUBLE   | 平均補正QT間隔             |
| avg_stress       | DOUBLE   | 平均ストレス値             |


# 5. インストールパッケージのダウンロード
リンク: http://47.121.198.148:1024/share/LnQCMXTbYsfWyMiZymcm <br>
抽出コード: GNfA7