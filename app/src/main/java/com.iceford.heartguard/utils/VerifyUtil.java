package com.iceford.heartguard.utils;

public class VerifyUtil {
    // 手机号码校验
    public static boolean validatePhoneNumber(String phoneNumber) {
        // 去除空格和其他特殊字符
        phoneNumber = phoneNumber.replaceAll("\\s", "");

        // 检查是否为 11 位数字
        // 不符合要求的手机号码
        return phoneNumber.matches("\\d{11}"); // 符合要求的手机号码
    }

    // 密码校验
    public static boolean validatePassword(String password) {
//        // 密码长度至少为 8 位
//        if (password.length() < 8) {
//            return false;
//        }
//
//        // 判断是否包含至少一个小写字母
//        if (!Pattern.matches(".*[a-z].*", password)) {
//            return false;
//        }
//
//        // 判断是否包含至少一个大写字母
//        if (!Pattern.matches(".*[A-Z].*", password)) {
//            return false;
//        }
//
//        // 判断是否包含至少一个数字
//        if (!Pattern.matches(".*\\d.*", password)) {
//            return false;
//        }
//
//        // 判断是否包含至少一个特殊字符
//        if (!Pattern.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", password)) {
//            return false;
//        }

        // 符合要求的密码
        return true;
    }

}

