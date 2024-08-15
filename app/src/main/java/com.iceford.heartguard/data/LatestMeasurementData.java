package com.iceford.heartguard.data;

import static com.iceford.heartguard.utils.FormatUtil.roundToInteger;

import java.util.HashMap;

public class LatestMeasurementData {
    private long measurementTime;
    private int duration;
    private int minHR;
    private double avgHR;
    private int maxHR;
    private double avgHRV;
    private double avgQTC;
    private double avgStress;

    public LatestMeasurementData(long measurementTime, int duration, int minHR, double avgHR, int maxHR, double avgHRV, double avgQTC, double avgStress) {
        this.measurementTime = measurementTime;
        this.duration = duration;
        this.minHR = minHR;
        this.avgHR = avgHR;
        this.maxHR = maxHR;
        this.avgHRV = avgHRV;
        this.avgQTC = avgQTC;
        this.avgStress = avgStress;
    }

    public LatestMeasurementData(int minHeartRate, int avgHeartRate, int maxHeartRate, double avgHRV, double avgQTC, double avgStress) {
    }

    public long getMeasurementTime() {
        return measurementTime;
    }

    public int getDuration() {
        return duration;
    }

    public int getMinHR() {
        return minHR;
    }

    public int getAvgHR() {
        return roundToInteger(avgHR);
    }

    public int getMaxHR() {
        return maxHR;
    }

    public int getAvgHRV() {
        return roundToInteger(avgHRV);
    }

    public int getAvgQTC() {
        return roundToInteger(avgQTC);
    }

    public int getAvgStress() {
        return roundToInteger(avgStress);
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("measurementTime", measurementTime);
        data.put("duration", duration);
        data.put("minHR", minHR);
        data.put("avgHR", avgHR);
        data.put("maxHR", maxHR);
        data.put("avgHRV", avgHRV);
        data.put("avgQTC", avgQTC);
        data.put("avgStress", avgStress);
        return data;
    }
}