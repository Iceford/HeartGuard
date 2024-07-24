package com.iceford.heartguard.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.iceford.heartguard.auth.AuthenticationResult;
import kotlin.Triple;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBHelper extends SQLiteOpenHelper {
    // 数据库名
    private static final String DATABASE_NAME = "HeartGuard.db";
    // 通用列
    private static final String COL_LOG = "log";
    private static final String COL_SPARE = "spare";


    // 用户数据表(主键：phone_number)
    private static final String TABLE_ACCOUNT = "UserInfo";
    private static final String COL_PHONE_NUMBER = "phone_number";
    private static final String COL_AVATAR_BASE64 = "avatar_base64";
    private static final String COL_LOGIN_PASSWORD = "login_password";
    private static final String COL_USER_NAME = "user_name";
    private static final String COL_USER_GENDER = "user_gender";
    private static final String COL_USER_BIRTHDAY = "user_birthday";
    private static final String COL_USER_HEIGHT = "user_height";
    private static final String COL_USER_WEIGHT = "user_weight";
    private static final String COL_USER_WEAR_PACEMAKER = "user_wear_pacemaker";
    private static final String COL_FEEDBACK = "feedback";


    // 已连接设备的数据表(主键：phone_number)
    private static final String TABLE_CONNECTED_DEVICES = "ConnectedDevices";
    private static final String COL_DEVICE_NAME = "device_name";
    private static final String COL_CONNECTION_COUNT = "connection_count";
    private static final String COL_LAST_CONNECTION_TIME = "last_connection_time";


    // 测量统计表(主键：phone_number)
    private static final String TABLE_MEASUREMENT_STATISTICS = "MeasurementStatistics";
    private static final String COL_MEASURE_TIME = "measure_time";
    private static final String COL_DURATION = "duration";
    private static final String COL_AVG_HR = "avg_hr";
    private static final String COL_MAX_HR = "max_hr";
    private static final String COL_MIN_HR = "min_hr";
    private static final String COL_AVG_HRV = "avg_hrv";
    private static final String COL_AVG_QTC = "avg_qtc";
    private static final String COL_AVG_STRESS = "avg_stress";


    // 实时数据表
    private static final String TABLE_REAL_TIME_DATA = "RealTimeData";
    private static final String COL_TIMESTAMP = "timestamp";
    private static final String COL_HR = "hr";
    private static final String COL_HRV = "hrv";
    private static final String COL_QTC = "qtc";
    private static final String COL_STRESS = "stress";
    private static final String COL_RAW_DATA_0 = "raw_data_0";
    private static final String COL_RAW_DATA_1 = "raw_data_1";
    private static final String COL_RAW_DATA_2 = "raw_data_2";
    private static final String COL_RAW_DATA_3 = "raw_data_3";
    private static final String COL_RAW_DATA_4 = "raw_data_4";
    private static final String COL_RAW_DATA_5 = "raw_data_5";
    private static final String COL_RAW_DATA_6 = "raw_data_6";
    private static final String COL_RAW_DATA_7 = "raw_data_7";
    private static final String COL_RAW_DATA_8 = "raw_data_8";
    private static final String COL_RAW_DATA_9 = "raw_data_9";
    private static final String COL_RAW_DATA_10 = "raw_data_10";
    private static final String COL_RAW_DATA_11 = "raw_data_11";
    private static final String COL_RAW_DATA_12 = "raw_data_12";
    private static final String COL_RAW_DATA_13 = "raw_data_13";
    private static final String COL_RAW_DATA_14 = "raw_data_14";
    private static final String COL_RAW_DATA_15 = "raw_data_15";
    private static final String COL_RAW_DATA_16 = "raw_data_16";
    private static final String COL_RAW_DATA_17 = "raw_data_17";
    private static final String COL_RAW_DATA_18 = "raw_data_18";
    private static final String COL_RAW_DATA_19 = "raw_data_19";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        db.execSQL("CREATE TABLE " + TABLE_ACCOUNT + "(" + COL_PHONE_NUMBER + " TEXT PRIMARY KEY, " + COL_AVATAR_BASE64 + " TEXT, " + COL_USER_NAME + " TEXT, " + COL_LOGIN_PASSWORD + " TEXT ," + COL_USER_GENDER + " TEXT, " + COL_USER_BIRTHDAY + " TEXT, " + COL_USER_HEIGHT + " TEXT, " + COL_USER_WEIGHT + " TEXT, " + COL_USER_WEAR_PACEMAKER + " TEXT," + COL_FEEDBACK + " TEXT," + COL_LOG + " TEXT," + COL_SPARE + " TEXT)");

        // 创建连接设备表
        db.execSQL("CREATE TABLE " + TABLE_CONNECTED_DEVICES + " (" + COL_PHONE_NUMBER + " TEXT PRIMARY KEY, " + COL_DEVICE_NAME + " TEXT , " + COL_CONNECTION_COUNT + " INTEGER, " + COL_LAST_CONNECTION_TIME + " INTEGER, " + COL_LOG + " TEXT," + COL_SPARE + " TEXT)");

        // 创建测量统计表
        db.execSQL("CREATE TABLE " + TABLE_MEASUREMENT_STATISTICS + " (" + COL_PHONE_NUMBER + " TEXT, " + COL_MEASURE_TIME + " INTEGER," + COL_DURATION + " TEXT," + COL_MIN_HR + " INTEGER," + COL_AVG_HR + " INTEGER, " + COL_MAX_HR + " INTEGER, " + COL_AVG_HRV + " DOUBLE," + COL_AVG_QTC + " DOUBLE," + COL_AVG_STRESS + " DOUBLE," + COL_LOG + " TEXT," + COL_SPARE + " TEXT)");

        // 创建实时数据表
        db.execSQL("CREATE TABLE " + TABLE_REAL_TIME_DATA + " (" + COL_TIMESTAMP + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_HR + " INTEGER, " + COL_HRV + " INTEGER, " + COL_QTC + " INTEGER, " + COL_STRESS + " INTEGER, " + COL_RAW_DATA_0 + " REAL, " + COL_RAW_DATA_1 + " REAL, " + COL_RAW_DATA_2 + " REAL, " + COL_RAW_DATA_3 + " REAL, " + COL_RAW_DATA_4 + " REAL, " + COL_RAW_DATA_5 + " REAL, " + COL_RAW_DATA_6 + " REAL, " + COL_RAW_DATA_7 + " REAL, " + COL_RAW_DATA_8 + " REAL, " + COL_RAW_DATA_9 + " REAL, " + COL_RAW_DATA_10 + " REAL, " + COL_RAW_DATA_11 + " REAL, " + COL_RAW_DATA_12 + " REAL, " + COL_RAW_DATA_13 + " REAL, " + COL_RAW_DATA_14 + " REAL, " + COL_RAW_DATA_15 + " REAL, " + COL_RAW_DATA_16 + " REAL, " + COL_RAW_DATA_17 + " REAL, " + COL_RAW_DATA_18 + " REAL, " + COL_RAW_DATA_19 + " REAL," + COL_LOG + " TEXT," + COL_SPARE + " TEXT)");
    }


    // 在数据库需要升级时，先删除旧版本的表格，然后重新创建一个新版本的数据库结构
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTED_DEVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REAL_TIME_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENT_STATISTICS);
        onCreate(db);
    }


    // 向用户表插入用户数据
    public Boolean insertRegisteredUser(String phone_number, String login_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        // 创建一个 ContentValues 对象，用于存储要插入的数据
        ContentValues values = new ContentValues();
        // 使用 put() 方法将用户名称、电话号码、登录密码值存储到 ContentValues 对象中
        values.put(COL_PHONE_NUMBER, phone_number);
        values.put(COL_LOGIN_PASSWORD, login_password);
        // 使用 insert() 方法将存储在 ContentValues 对象中的数据插入到名为 TABLE_ACCOUNT 的表中
        long result = db.insert(TABLE_ACCOUNT, null, values);
        // 检查插入操作的结果
        return result != -1;
    }


    // 检查手机号码是否存在用户表中
    @SuppressLint("Recycle")
    public Boolean checkPhoneNumber(String phone_number) {
        SQLiteDatabase db = this.getReadableDatabase();

        // 通过调用 getCount() 方法获取 cursor 对象中的行数，即查询结果的行数
        String[] columns = {"*"};
        String selection = COL_PHONE_NUMBER + "=?";
        String[] selectionArgs = {phone_number};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        return cursor.getCount() > 0;
    }


    // 用于验证用户登录
    @SuppressLint("Range")
    public AuthenticationResult authenticateUser(String phoneNumber, String loginPassword) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + COL_PHONE_NUMBER + "=?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return AuthenticationResult.USER_NOT_FOUND;
        }

        cursor.moveToFirst();
        String storedPassword = cursor.getString(cursor.getColumnIndex(COL_LOGIN_PASSWORD));

        cursor.close();
        db.close();

        if (storedPassword.equals(loginPassword)) {
            return AuthenticationResult.SUCCESS;
        } else {
            return AuthenticationResult.INVALID_PASSWORD;
        }
    }


    // 更新用户头像
    public boolean updateUserAvatarByPhoneNumber(String phoneNumber, String avatarBase64) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_AVATAR_BASE64, avatarBase64);

        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        int rowsAffected = database.update(TABLE_ACCOUNT, values, selection, selectionArgs);

        return rowsAffected > 0;
    }


    // 根据手机号码查询 avatarBase64
    public String getAvatarBase64ByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        } else {
            SQLiteDatabase database = getReadableDatabase();

            String[] projection = {COL_AVATAR_BASE64};
            String selection = COL_PHONE_NUMBER + " = ?";
            String[] selectionArgs = {phoneNumber};

            Cursor cursor = database.query(TABLE_ACCOUNT, projection, selection, selectionArgs, null, null, null);

            String avatarBase64 = null;
            if (cursor.moveToFirst()) {
                avatarBase64 = cursor.getString(cursor.getColumnIndexOrThrow(COL_AVATAR_BASE64));
            }

            cursor.close();
            return avatarBase64;
        }

    }


    // 根据传入的手机号码获取用户姓名
    @SuppressLint("Range")
    public String getUserNameByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = null;

        if (phoneNumber == null) {
            return null;
        }

        String[] columns = {COL_USER_NAME};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return userName;
    }


    // 根据传入的手机号码获取用户性别
    @SuppressLint("Range")
    public String getUserGenderByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userGender = null;

        String[] columns = {COL_USER_GENDER};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            userGender = cursor.getString(cursor.getColumnIndex(COL_USER_GENDER));
        }

        cursor.close();
        db.close();
        return userGender;
    }


    // 根据传入的手机号码获取用户生日
    @SuppressLint("Range")
    public String getUserBirthdayByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String UserBirthday = null;

        String[] columns = {COL_USER_BIRTHDAY};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            UserBirthday = cursor.getString(cursor.getColumnIndex(COL_USER_BIRTHDAY));
        }

        cursor.close();
        db.close();
        return UserBirthday;
    }


    // 根据传入的手机号码获取用户身高
    @SuppressLint("Range")
    public String getUserHeightByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userHeight = null;

        String[] columns = {COL_USER_HEIGHT};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            userHeight = cursor.getString(cursor.getColumnIndex(COL_USER_HEIGHT));
        }

        cursor.close();
        db.close();
        return userHeight;
    }


    // 根据传入的手机号码获取用户体重
    @SuppressLint("Range")
    public String getUserWeightByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userWeight = null;

        String[] columns = {COL_USER_WEIGHT};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            userWeight = cursor.getString(cursor.getColumnIndex(COL_USER_WEIGHT));
        }

        cursor.close();
        db.close();
        return userWeight;
    }


    // 根据传入的手机号码获取用户是否佩戴心脏起搏器
    @SuppressLint("Range")
    public String getUserWearPacemakerByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String user_wear_pacemaker = null;

        String[] columns = {COL_USER_WEAR_PACEMAKER};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            user_wear_pacemaker = cursor.getString(cursor.getColumnIndex(COL_USER_WEAR_PACEMAKER));
        }

        cursor.close();
        db.close();
        return user_wear_pacemaker;
    }


    // 根据传入的手机号码获取计算用户年龄
    public int calculateUserAgeByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {COL_USER_BIRTHDAY};
        String selection = COL_PHONE_NUMBER + "=?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String userBirthday = cursor.getString(cursor.getColumnIndex(COL_USER_BIRTHDAY));
            if (userBirthday != null) {
                // 计算年龄
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date birthDate = dateFormat.parse(userBirthday);
                    Calendar birthCalendar = Calendar.getInstance();
                    birthCalendar.setTime(birthDate);

                    Calendar currentCalendar = Calendar.getInstance();
                    int currentYear = currentCalendar.get(Calendar.YEAR);
                    int currentMonth = currentCalendar.get(Calendar.MONTH);
                    int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

                    int birthYear = birthCalendar.get(Calendar.YEAR);
                    int birthMonth = birthCalendar.get(Calendar.MONTH);
                    int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);

                    int age = currentYear - birthYear;

                    // 如果当前月份小于出生月份，年龄减1
                    if (currentMonth < birthMonth) {
                        age--;
                    }
                    // 如果当前月份等于出生月份，且当前日期小于出生日期，年龄减1
                    else if (currentMonth == birthMonth && currentDay < birthDay) {
                        age--;
                    }

                    cursor.close();
                    return age;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 处理userBirthday为空的情况
            return -1;
        }
        cursor.close();
        return -1; // 返回-1表示无法计算年龄或发生错误
    }


    // 根据传入的手机号码更新用户信息
    public void updateUserInformationByPhoneNumber(String phoneNumber, String userName, String userGender, String userBirthday, String userHeight, String userWeight, String userWearPacemaker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_USER_NAME, userName);
        values.put(COL_USER_GENDER, userGender);
        values.put(COL_USER_BIRTHDAY, userBirthday);
        values.put(COL_USER_HEIGHT, userHeight);
        values.put(COL_USER_WEIGHT, userWeight);
        values.put(COL_USER_WEAR_PACEMAKER, userWearPacemaker);

        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        db.update(TABLE_ACCOUNT, values, selection, selectionArgs);

        db.close();
    }

    // 根据传入的手机号码注销账号
    public void deleteAccountByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        db.delete(TABLE_ACCOUNT, selection, selectionArgs);

        db.close();
    }

    // 根据手机号码获取用户登录密码
    public String getLoginPasswordByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String password = null;

        String[] projection = {COL_LOGIN_PASSWORD};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_ACCOUNT, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOGIN_PASSWORD));
        }

        cursor.close();
        db.close();

        return password;
    }

    // 更新登录密码
    public void updateLoginPasswordByPhoneNumber(String phoneNumber, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LOGIN_PASSWORD, newPassword);

        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        db.update(TABLE_ACCOUNT, values, selection, selectionArgs);

        db.close();
    }

    // 根据用户手机号码插入反馈内容
    public Boolean updateFeedbackByPhoneNumber(String phoneNumber, String feedback) {
        SQLiteDatabase db = getWritableDatabase(); // 假设已经获取到有效的数据库连接
        ContentValues values = new ContentValues();

        values.put(COL_FEEDBACK, feedback);

        String whereClause = COL_PHONE_NUMBER + " = ?";
        String[] whereArgs = {phoneNumber};

        int rowsAffected = db.update(TABLE_ACCOUNT, values, whereClause, whereArgs);
        db.close(); // 关闭数据库连接

        return rowsAffected > 0;
    }


    // 检查该手机号码下是否成功连接过设备
    public boolean checkDeviceConnectionByPhoneNumber(String phone_number, String deviceName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_CONNECTED_DEVICES + " WHERE " + COL_PHONE_NUMBER + "=? AND " + COL_DEVICE_NAME + "=?";
        String[] selectionArgs = {phone_number, deviceName};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean connectedBefore = cursor.getCount() > 0;
        cursor.close();
        return connectedBefore;
    }

    // 更新手机号码下设备的连接次数
    public void UpdateConnectionRecord(String phone_number, String deviceName, long connection_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_PHONE_NUMBER, phone_number);
        values.put(COL_DEVICE_NAME, deviceName);

        if (checkDeviceConnectionByPhoneNumber(phone_number, deviceName)) {
            // 之前连接的设备，更新连接计数和连接时间
            db.execSQL("UPDATE " + TABLE_CONNECTED_DEVICES + " SET " + COL_CONNECTION_COUNT + " = " + COL_CONNECTION_COUNT + "+1, " + COL_LAST_CONNECTION_TIME + " = ?" + " WHERE " + COL_PHONE_NUMBER + "=? AND " + COL_DEVICE_NAME + "=?", new String[]{String.valueOf(connection_time), phone_number, deviceName});
        } else {
            // 设备之前未连接，插入新行
            values.put(COL_CONNECTION_COUNT, 1);
            values.put(COL_LAST_CONNECTION_TIME, connection_time);
            db.insert(TABLE_CONNECTED_DEVICES, null, values);
        }
    }


    // 获取手机号码下所有连接过的设备名称
    public List<String> getConnectedDevicesByPhoneNumber(String phone_number) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> connectedDevicesList = new ArrayList<>();

        String[] columns = {COL_DEVICE_NAME};
        String selection = COL_PHONE_NUMBER + "=?";
        String[] selectionArgs = {phone_number};

        Cursor cursor = db.query(TABLE_CONNECTED_DEVICES, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String deviceName = cursor.getString(cursor.getColumnIndex(COL_DEVICE_NAME));
                connectedDevicesList.add(deviceName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return connectedDevicesList;
    }


    // 根据传入的手机号码删除连接过的设备记录
    public void deleteConnectedDeviceByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        db.delete(TABLE_CONNECTED_DEVICES, selection, selectionArgs);

        db.close();
    }


    // 插入实时数据
    public void insertRealTimeDataByPhoneNumber(long timestamp, int hr, int hrv, int qtc, int stress, double[] rawDataDoubleArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TIMESTAMP, timestamp);
        values.put(COL_HR, hr);
        values.put(COL_HRV, hrv);
        values.put(COL_QTC, qtc);
        values.put(COL_STRESS, stress);
        values.put(COL_RAW_DATA_0, rawDataDoubleArray[0]);
        values.put(COL_RAW_DATA_1, rawDataDoubleArray[1]);
        values.put(COL_RAW_DATA_2, rawDataDoubleArray[2]);
        values.put(COL_RAW_DATA_3, rawDataDoubleArray[3]);
        values.put(COL_RAW_DATA_4, rawDataDoubleArray[4]);
        values.put(COL_RAW_DATA_5, rawDataDoubleArray[5]);
        values.put(COL_RAW_DATA_6, rawDataDoubleArray[6]);
        values.put(COL_RAW_DATA_7, rawDataDoubleArray[7]);
        values.put(COL_RAW_DATA_8, rawDataDoubleArray[8]);
        values.put(COL_RAW_DATA_9, rawDataDoubleArray[9]);
        values.put(COL_RAW_DATA_10, rawDataDoubleArray[10]);
        values.put(COL_RAW_DATA_11, rawDataDoubleArray[11]);
        values.put(COL_RAW_DATA_12, rawDataDoubleArray[12]);
        values.put(COL_RAW_DATA_13, rawDataDoubleArray[13]);
        values.put(COL_RAW_DATA_14, rawDataDoubleArray[14]);
        values.put(COL_RAW_DATA_15, rawDataDoubleArray[15]);
        values.put(COL_RAW_DATA_16, rawDataDoubleArray[16]);
        values.put(COL_RAW_DATA_17, rawDataDoubleArray[17]);
        values.put(COL_RAW_DATA_18, rawDataDoubleArray[18]);
        values.put(COL_RAW_DATA_19, rawDataDoubleArray[19]);

        db.insert(TABLE_REAL_TIME_DATA, null, values);
        db.close();
    }


    // 记录测量状态和统计心率数据
    public void calculateHRStats(String phoneNumber, long startTime, long endTime, int duration) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        String[] columns = {COL_HR, COL_HRV, COL_QTC, COL_STRESS};
        String selection = COL_TIMESTAMP + " > ? AND " + COL_TIMESTAMP + " < ? AND " + COL_STRESS + " > 0";
        String[] selectionArgs = {String.valueOf(startTime), String.valueOf(endTime)};

        Cursor cursor = db.query(TABLE_REAL_TIME_DATA, columns, selection, selectionArgs, null, null, null);

        int sumHR = 0;
        int sumHRV = 0;
        int sumQTC = 0;
        int sumStress = 0;
        int count = 0;
        int maxHR = Integer.MIN_VALUE;
        int minHR = Integer.MAX_VALUE;

        if (cursor != null && cursor.moveToFirst()) {
            int hrColumnIndex = cursor.getColumnIndex(COL_HR);
            int hrvColumnIndex = cursor.getColumnIndex(COL_HRV);
            int qtcColumnIndex = cursor.getColumnIndex(COL_QTC);
            int stressColumnIndex = cursor.getColumnIndex(COL_STRESS);

            if (hrColumnIndex != -1) {
                do {
                    int hrValue = cursor.getInt(hrColumnIndex);
                    int hrvValue = cursor.getInt(hrvColumnIndex);
                    int qtcValue = cursor.getInt(qtcColumnIndex);
                    int stressValue = cursor.getInt(stressColumnIndex);

                    sumHR += hrValue;
                    sumHRV += hrvValue;
                    sumQTC += qtcValue;
                    sumStress += stressValue;

                    count++;
                    if (hrValue > maxHR) {
                        maxHR = hrValue;
                    }
                    if (hrValue < minHR) {
                        minHR = hrValue;
                    }
                } while (cursor.moveToNext());
            } else {
                values.put(COL_LOG, "在光标中找不到所需的列");
            }
        }
        assert cursor != null;
        cursor.close();

        if (count > 0) {
            double avgHR = (double) sumHR / count;
            double avgHRV = (double) sumHRV / count;
            double avgQTC = (double) sumQTC / count;
            double avgStress = (double) sumStress / count;

            values.put(COL_PHONE_NUMBER, phoneNumber);
            values.put(COL_MEASURE_TIME, startTime);
            values.put(COL_DURATION, duration);
            values.put(COL_AVG_HR, avgHR);
            values.put(COL_MAX_HR, maxHR);
            values.put(COL_MIN_HR, minHR);
            values.put(COL_AVG_HRV, avgHRV);
            values.put(COL_AVG_QTC, avgQTC);
            values.put(COL_AVG_STRESS, avgStress);


            long newRowId = db.insert(TABLE_MEASUREMENT_STATISTICS, null, values);
            if (newRowId == -1) {
                values.put(COL_LOG, "无法将数据插入到 MeasurementStatistics 表中");
            } else {
                values.put(COL_LOG, "数据已成功插入到 MeasurementStatistics 表中");
            }
        } else {
            values.put(COL_LOG, "在指定的时间范围内未找到监测数据");
        }
        db.close();
    }


    // 获取最近一次测量的数据
    @SuppressLint("Range")
    public LatestMeasurementData getLatestMeasurementDataByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        long measureTime = 0;
        int duration = 0;
        int minHR = 0;
        double avgHR = 0;
        int maxHR = 0;
        double avgHRV = 0.0;
        double avgQTC = 0.0;
        double avgStress = 0.0;

        String[] columns = {COL_MEASURE_TIME, COL_DURATION, COL_MIN_HR, COL_AVG_HR, COL_MAX_HR, COL_AVG_HRV, COL_AVG_QTC, COL_AVG_STRESS};
        String selection = COL_PHONE_NUMBER + "=?";
        String[] selectionArgs = {phoneNumber};
        String orderBy = COL_MEASURE_TIME + " DESC";
        String limit = "1";

        Cursor cursor = db.query(TABLE_MEASUREMENT_STATISTICS, columns, selection, selectionArgs, null, null, orderBy, limit);

        if (cursor.moveToFirst()) {
            measureTime = cursor.getLong(cursor.getColumnIndex(COL_MEASURE_TIME));
            duration = cursor.getInt(cursor.getColumnIndex(COL_DURATION));

            minHR = cursor.getInt(cursor.getColumnIndex(COL_MIN_HR));
            avgHR = cursor.getDouble(cursor.getColumnIndex(COL_AVG_HR));
            maxHR = cursor.getInt(cursor.getColumnIndex(COL_MAX_HR));

            avgHRV = cursor.getDouble(cursor.getColumnIndex(COL_AVG_HRV));
            avgQTC = cursor.getDouble(cursor.getColumnIndex(COL_AVG_QTC));
            avgStress = cursor.getDouble(cursor.getColumnIndex(COL_AVG_STRESS));
        }

        cursor.close();
        db.close();

        return new LatestMeasurementData(measureTime, duration, minHR, avgHR, maxHR, avgHRV, avgQTC, avgStress);
    }


    // 获取所有的历史测量数据
    @SuppressLint("Range")
    public List<TableRecordRow> getAllMeasurementDataByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TableRecordRow> tableRecordRowList = new ArrayList<>();

        String[] columns = {COL_MEASURE_TIME, COL_DURATION, COL_MIN_HR, COL_AVG_HR, COL_MAX_HR, COL_AVG_HRV, COL_AVG_QTC, COL_AVG_STRESS};
        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};
        Cursor cursor = db.query(TABLE_MEASUREMENT_STATISTICS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String measureTime = cursor.getString(cursor.getColumnIndex(COL_MEASURE_TIME));
                String duration = cursor.getString(cursor.getColumnIndex(COL_DURATION));
                int minHR = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_MIN_HR)));
                double avgHR = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_AVG_HR)));
                int maxHR = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_MAX_HR)));
                double avgHRV = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_AVG_HRV)));
                double avgQTC = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_AVG_QTC)));
                double avgSTRESS = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_AVG_STRESS)));

                TableRecordRow tableRecordRow = new TableRecordRow(measureTime, duration, minHR, avgHR, maxHR, avgHRV, avgQTC, avgSTRESS);
                tableRecordRowList.add(tableRecordRow);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return tableRecordRowList;
    }


    // 获取指定范围的原始数据
    @SuppressLint("Range")
    public List<Double> getRawDataInRange(long startTime, int durationSeconds) {
        SQLiteDatabase db = this.getReadableDatabase();

        long endTime = startTime + (durationSeconds * 1000L);
        List<Double> rawDataList = new ArrayList<>();

        String[] columns = {
                COL_RAW_DATA_0, COL_RAW_DATA_1, COL_RAW_DATA_2, COL_RAW_DATA_3, COL_RAW_DATA_4,
                COL_RAW_DATA_5, COL_RAW_DATA_6, COL_RAW_DATA_7, COL_RAW_DATA_8, COL_RAW_DATA_9,
                COL_RAW_DATA_10, COL_RAW_DATA_11, COL_RAW_DATA_12, COL_RAW_DATA_13, COL_RAW_DATA_14,
                COL_RAW_DATA_15, COL_RAW_DATA_16, COL_RAW_DATA_17, COL_RAW_DATA_18, COL_RAW_DATA_19
        };

        String selection = COL_TIMESTAMP + " >= ? AND " + COL_TIMESTAMP + " < ?";
        String[] selectionArgs = {String.valueOf(startTime), String.valueOf(endTime)};

        Cursor cursor = db.query(TABLE_REAL_TIME_DATA, columns, selection, selectionArgs, null, null, COL_TIMESTAMP);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                double rawData0 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_0));
                double rawData1 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_1));
                double rawData2 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_2));
                double rawData3 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_3));
                double rawData4 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_4));
                double rawData5 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_5));
                double rawData6 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_6));
                double rawData7 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_7));
                double rawData8 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_8));
                double rawData9 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_9));
                double rawData10 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_10));
                double rawData11 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_11));
                double rawData12 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_12));
                double rawData13 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_13));
                double rawData14 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_14));
                double rawData15 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_15));
                double rawData16 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_16));
                double rawData17 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_17));
                double rawData18 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_18));
                double rawData19 = cursor.getDouble(cursor.getColumnIndex(COL_RAW_DATA_19));

                rawDataList.add(rawData0);
                rawDataList.add(rawData1);
                rawDataList.add(rawData2);
                rawDataList.add(rawData3);
                rawDataList.add(rawData4);
                rawDataList.add(rawData5);
                rawDataList.add(rawData6);
                rawDataList.add(rawData7);
                rawDataList.add(rawData8);
                rawDataList.add(rawData9);
                rawDataList.add(rawData10);
                rawDataList.add(rawData11);
                rawDataList.add(rawData12);
                rawDataList.add(rawData13);
                rawDataList.add(rawData14);
                rawDataList.add(rawData15);
                rawDataList.add(rawData16);
                rawDataList.add(rawData17);
                rawDataList.add(rawData18);
                rawDataList.add(rawData19);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return rawDataList;
    }


    // 查询ECG图像的对应信息
    @SuppressLint("Range")
    public List<Triple<Long, Integer, Integer>> getECGInfoByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Triple<Long, Integer, Integer>> result = new ArrayList<>();

        String[] columns = {
                COL_MEASURE_TIME, COL_DURATION, COL_AVG_HR
        };

        String selection = COL_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_MEASUREMENT_STATISTICS, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            long measureTime = cursor.getLong(cursor.getColumnIndex(COL_MEASURE_TIME));
            int duration = cursor.getInt(cursor.getColumnIndex(COL_DURATION));
            int avgHeartRate = cursor.getInt(cursor.getColumnIndex(COL_AVG_HR));
            result.add(new Triple<>(measureTime, duration, avgHeartRate));
        }

        cursor.close();

        return result;
    }

}

