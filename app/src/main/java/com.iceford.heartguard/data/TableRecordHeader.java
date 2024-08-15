package com.iceford.heartguard.data;

import static com.iceford.heartguard.utils.FormatUtil.roundToInteger;

public class TableRecordHeader {
    private final String measureTime;
    private final String duration;
    private final int minHR;
    private final double avgHR;
    private final int maxHR;

    private final double avgHRV;
    private final double avgQTC;
    private final double avgSTRESS;

    public TableRecordHeader(String measureTime, String duration, int minHR, double avgHR, int maxHR, double avgHRV, double avgQTC, double avgSTRESS) {
        this.measureTime = measureTime;
        this.duration = duration;
        this.minHR = minHR;
        this.avgHR = avgHR;
        this.maxHR = maxHR;
        this.avgHRV = avgHRV;
        this.avgQTC = avgQTC;
        this.avgSTRESS = avgSTRESS;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public String getDuration() {
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

    public int getAvgSTRESS() {
        return roundToInteger(avgSTRESS);
    }
}