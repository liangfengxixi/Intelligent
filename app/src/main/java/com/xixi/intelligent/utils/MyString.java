package com.xixi.intelligent.utils;

import java.text.DecimalFormat;

/**
 * @author xixi
 * @date $date$ $time$
 */
public class MyString {

    /**
     * 时长转换
     *
     * @param mss
     * @return
     */
    public static String formatDateTime(long mss) {
        String DateTimes = "";
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (days > 0) {
            DateTimes = days + "天" + hours + "小时" + minutes + "分钟";
//					+ seconds + "秒";
        } else if (hours > 0) {
            DateTimes = hours + "小时" + minutes + "分钟";
//					+ seconds + "秒";
        } else if (minutes > 0) {
            DateTimes = minutes + "分钟";
//					+ seconds + "秒";
        } else {
            DateTimes = "小于1分钟";
//			DateTimes=seconds + "秒";
        }

        return DateTimes;
    }

    public static String formatKM(int d) {
        if (d == 0) {
            return "0米";
        } else if (d < 100) {
            return d + "米";
        } else if ((100 <= d) && (d < 1000)) {
            return d + "米";
        } else if ((1000 <= d) && (d < 10000)) {
            return (d / 10) * 10 / 1000.0D + "千米";
        } else if ((10000 <= d) && (d < 100000)) {
            return (d / 100) * 100 / 1000.0D + "千米";
        }
        return (d / 1000) + "千米";
    }

    public static String postoABC(int pos) {
        if (pos == 0) {
            return "A";
        } else if (pos == 1) {
            return "B";
        } else if (pos == 2) {
            return "C";
        } else if (pos == 3) {
            return "D";
        } else {
            return "";
        }
    }

    public static int ABCtoPos(String abc) {
        int num = 0;
        abc.toUpperCase();
        switch (abc){
            case "A":
                num = 0;
                break;
            case "B":
                num = 1;
                break;
            case "C":
                num = 2;
                break;
            case "D":
                num = 3;
                break;
        }
        return num;
    }

    public static String msgStatus(String msg) {
        String status = "";
        switch (msg){
            case "check":
                status = "点检";
                break;
            case "maintenance":
                status = "保养";
                break;
            case "fault":
                status = "维修";
                break;
            default:
                status = msg;
                break;
        }
        return status;
    }

    public static String trimZero(Double s) {
        DecimalFormat nf = new DecimalFormat("#.##");
        return nf.format(s);
    }

    //是否包含表情
    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }
}
