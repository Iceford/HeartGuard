package com.iceford.heartguard.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatUtil {
    // 四舍五入,不保留小数,保存为int类型数据
    public static int roundToInteger(double number) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        return bd.intValue();
    }

}

