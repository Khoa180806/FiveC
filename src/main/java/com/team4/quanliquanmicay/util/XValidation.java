package com.team4.quanliquanmicay.util;

public class XValidation {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isEmail(String value) {
        return value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isPhone(String value) {
        return value.matches("^\\d{10}$");
    }

    public static boolean isPassword(String value) {
        return value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public static boolean isUsername(String value) {
        return value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isNumber(String value) {
        return value.matches("^-?\\d+(\\.\\d+)?$");
    }

    public static boolean isDate(String value) {
        return value.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static boolean isTime(String value) {
        return value.matches("^\\d{2}:\\d{2}$");
    }

    public static boolean isDateTime(String value) {
        return value.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$");
    }
}

