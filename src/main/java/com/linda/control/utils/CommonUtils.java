package com.linda.control.utils;

/**
 * Created by PengChao on 16/9/22.
 */
public class CommonUtils {

    public static String SysCreateUser = "leaduadmin";
    public static String SysUpdateUser = "leaduadmin";
    public static String chinaName = "中国";
    public static String MAP_TUBA = "tuba";
    public static String MAP_BAIDU = "baidu";
    public static String UNIT_SPEED = "km/h";
    public static String UNIT_DISTANCE= "km";

    /**
     * sql语句like查询值构造
     * @param param
     * @return
     */
    public static String likePartten(String param){
        return "%" + param + "%";
    }

    /**
     * 获取文件后缀名(包含.)
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String inPartten(Object [] param){
        StringBuffer stringBuffer = new StringBuffer();
        for(Object object : param){
            stringBuffer.append("'"+object+"',");
        }
        if(stringBuffer.length() > 0){
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        return stringBuffer.toString();
    }

    /**
     * 转string字符串
     * @param param
     * @return
     */
    public static String getStr(Object param){
        try{
            if(param != null)
                return param.toString();
            else
                return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 转long
     * @param param
     * @return
     */
    public static Long getLong(Object param){
        try{
            if(param != null)
                return Long.parseLong(param.toString());
            else
                return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 转double
     * @param param
     * @return
     */
    public static Double getDouble(Object param){
        try{
            if(param != null)
                return Double.parseDouble(param.toString());
            else
                return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 转int
     * @param param
     * @return
     */
    public static  Integer getInt(Object param){
        try{
            if(param != null)
                return Integer.parseInt(param.toString());
            else
                return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 返回是否为空
     * @param param
     * @return
     */
    public static boolean isNull(Object param){
        if(param == null)
            return true;
        else if(param.toString().trim().equals(""))
            return true;
        else
            return false;
    }

}
