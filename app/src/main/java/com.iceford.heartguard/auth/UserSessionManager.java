package com.iceford.heartguard.auth;

public class UserSessionManager {
    private static UserSessionManager instance;
    private String phoneNumber;
    // 默认测量时长
    private int measureDuration = 30;
    // 默认放大倍数
    private int magnification = 1;

    private boolean isLoggedIn = false;

    private UserSessionManager() {
        // 私有构造函数，防止外部实例化
    }

    public static synchronized UserSessionManager getInstance() {
        if (instance == null) {
            instance = new UserSessionManager();
        }
        return instance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public int getMeasureDuration() {
        return measureDuration;
    }

    public void setMeasureDuration(int measureDuration) {
        this.measureDuration = measureDuration;
    }

    public int getMagnification() {
        return magnification;
    }

    public void setMagnification(int magnification) {
        this.magnification = magnification;
    }

}

