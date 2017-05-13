package com.linda.control.utils;

/**
 * 用于解析GPS状态
 * <p>
 * Created by ywang on 2017/2/25.
 */
public class GpsStatusUtils {

    private String statusResult = "";
    private String wzylbs = "";

    public GpsStatusUtils(int statusNum) {
        convertGpsStatus(statusNum);
    }

    public GpsStatusUtils(int statusNum, String wzylbs) {
        convertGpsStatus(statusNum);
        this.wzylbs = wzylbs;
    }

    private void convertGpsStatus(int statusNum) {
        // 转为二进制
        String radix_2 = Integer.toBinaryString(statusNum);
        // 补0
        statusResult = addZeroToLeft(radix_2);
    }

    /**
     * 返回车辆震动报警
     *
     * @return
     */
    public String getShockStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 2)) ? "车辆震动报警" : "";
    }

    /**
     * 返回后备箱状态
     *
     * @return
     */
    public String getTrunkStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 3)) ? "后备箱打开" : "后备箱关闭";
    }

    /**
     * 返回车门状态
     *
     * @return
     */
    public String getCarDoorStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 4)) ? "车门打开" : "车门关闭";
    }

    /**
     * 返回ACC状态
     *
     * @return
     */
    public String getACCStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 5)) ? "ACC打开" : "ACC关闭";
    }

    /**
     * 返回设警状态
     *
     * @return
     */
    public String getAlarmStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 6)) ? "车辆设警" : "车辆解警";
    }

    /**
     * 返回紧急求助状态
     *
     * @return
     */
    public String getHelpStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 7)) ? "紧急求助" : "";
    }

    /**
     * 返回断电状态
     *
     * @return
     */
    public String getPowerStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 8)) ? "断电报警" : "";
    }

    /**
     * 返回GPS定位状态
     *
     * @return
     */
    public String getGpsStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 9)) ? "GPS已经定位" : "GPS未定位";
    }

    /**
     * 返回设备被拆卸状态
     *
     * @return
     */
    public String getDeviceStatus() {
        return 1 == Character.getNumericValue(statusResult.charAt(statusResult.length() - 16)) ? "设备被拆卸" : "";
    }

    /**
     * 返回显示的重要状态
     *
     * @return
     */
    public String getAllStatus() {
        String status = getACCStatus();
        if (!"".equals(getShockStatus())) {
            status = status.concat(",").concat(getShockStatus());
        }
        if (!"".equals(getPowerStatus())) {
            status = status.concat(",").concat(getPowerStatus());
        }
        if ("GPS".equals(wzylbs)) {
            status = status.concat(",").concat("GPS已经定位");
        } else if ("GSM".equals(wzylbs)) {
            status = status.concat(",").concat("GSM已经定位");
        } else if ("WIFI".equals(wzylbs)) {
            status = status.concat(",").concat("WIFI已经定位");
        }
        if (!"".equals(getDeviceStatus())) {
            status = status.concat(",").concat(getDeviceStatus());
        }
        return status;
    }

    private static String addZeroToLeft(String obj) {
        while (obj.length() < 16) {
            obj = "0".concat(obj);
        }
        return obj;
    }
}
