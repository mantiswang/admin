package com.linda.control.utils.xftm;

import com.linda.control.config.EtonenetParam;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by qiaohao on 2017/4/14.
 * 先锋太盟数据接口请求
 */
public class MessageUtil {

    /**
     * 登录的url
     */
    private static final String loginUrl = "http://27.221.58.41:8000/workspace/default.aspx";
    /**
     * 读取gps_data信息的url
     */
    private static final String gpsDataUrl = "http://27.221.58.41:8000/workspace/Ajax/ajax.ashx";

    @Autowired
    public EtonenetParam etonenetParam;

    /**
     * Hex编码字符组
     */
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

//    /**
//     * 测试代码
//     * @param args
//     */
//    public static void main(String  [] args){
//        MessageUtil m = new MessageUtil();
//        List<String> phoneNums = Lists.newArrayList();
//        phoneNums.add("1064620413192");
//        //phoneNums.add("18621286011");
//        m.etonenetParam =new EtonenetParam();
//        m.etonenetParam.setMtUrl("http://esms100.10690007.net/sms/mt");
//        m.etonenetParam.setCommand("MT_REQUEST");
//        m.etonenetParam.setSpid("9686");
//        m.etonenetParam.setSppassword("UPD7hdzy");
//        m.etonenetParam.setSpsc("00");
//        m.etonenetParam.setSa("10657109053657");
//        m.etonenetParam.setHaltPhoneNum("13916428877");
//        m.sendMessage(phoneNums,"TI");
//    }


    /**
     * 登录先锋太盟接口
     * @return
     */
    public static String login(){

        return "";

    }

    /**
     * 读取gps_data
     * @param url
     * @param cookie
     * @return
     * @throws Exception
     */
    public static String readerGpsData(String url,String cookie) throws Exception{
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("host", "shenghuo.alipay.com");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("accept-Language","zh-CN,zh;q=0.8");
            connection.setRequestProperty("accept-Encoding","gzip, deflate, sdch");
            connection.setRequestProperty("referer","https://shenghuo.alipay.com/send/payment/fill.htm?_pdType=adbhajcaccgejhgdaeih");
            connection.setRequestProperty("cookie",cookie);
            
            connection.setRequestProperty("connection", "keep-alive");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        System.out.println("result + --->" + result);
        return result;
    }

    /**
     * 发送消息
     * @param phoneNums
     * @return
     */
    public void sendMessage(List<String> phoneNums, String content) {
        phoneNums.forEach(phoneNum -> doGetRequest(setSmsParam(phoneNum, content)));
    }

    private String setSmsParam(String phoneNum, String content){
        //目标号码，必填参数
        String da = "86"+phoneNum;
        //下行内容以及编码格式，必填参数
        int dc = 15;
        String sm = encodeHexStr(dc, content);//下行内容进行Hex编码，此处dc设为15，即使用GBK编码格式
        //组成url字符串
        String smsUrl = etonenetParam.getMtUrl() + "?command=" + etonenetParam.getCommand() + "&spid=" + etonenetParam.getSpid()
                + "&sppassword=" + etonenetParam.getSppassword() + "&spsc=" + etonenetParam.getSpsc() + "&sa=" + etonenetParam.getSa()
                + "&da=" + da + "&sm=" + sm + "&dc=" + dc;
        return smsUrl;
    }

    /**
     * 构造发送短信Url
     * @param phoneNum
     * @return
     */
    private String makeSmsUrl(String phoneNum, Integer saveTime){
        //目标号码，必填参数
        String da = "86"+phoneNum;
        //下行内容以及编码格式，必填参数
        int dc = 15;
        String code = "" + (int)(Math.random()*9000+1000); //
        String sm = encodeHexStr(dc, "短信验证码为:" + code +",有效期为一天,请勿删短信!");//下行内容进行Hex编码，此处dc设为15，即使用GBK编码格式
        //组成url字符串
        String smsUrl = etonenetParam.getMtUrl() + "?command=" + etonenetParam.getCommand() + "&spid=" + etonenetParam.getSpid()
                + "&sppassword=" + etonenetParam.getSppassword() + "&spsc=" + etonenetParam.getSpsc() + "&sa=" + etonenetParam.getSa()
                + "&da=" + da + "&sm=" + sm + "&dc=" + dc;
        return smsUrl;
    }

    //短信内容编码
    private String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;

        if (realStr != null) {
            byte[] data = null;
            try {
                if (dataCoding == 15) {
                    data = realStr.getBytes("GBK");
                } else if ((dataCoding & 0x0C) == 0x08) {
                    data = realStr.getBytes("UnicodeBigUnmarked");
                } else {
                    data = realStr.getBytes("ISO8859-1");
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
            }

            if (data != null) {
                int len = data.length;
                char[] out = new char[len << 1];
                // two characters form the hex value.
                for (int i = 0, j = 0; i < len; i++) {
                    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
                    out[j++] = DIGITS[0x0F & data[i]];
                }
                hexStr = new String(out);
            }
        }
        return hexStr;
    }

    /**
     * 发送http GET请求，并返回http响应字符串
     *
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String urlstr) {
        String res = null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "text/html; charset=GB2312");
            System.setProperty("sun.net.client.defaultConnectTimeout", "5000");//jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", "10000"); //jdk1.4换成这个,读操作超时
            //httpConn.setConnectTimeout(5000);//jdk 1.5换成这个,连接超时
            //httpConn.setReadTimeout(10000);//jdk 1.5换成这个,读操作超时
            httpConn.setDoInput(true);
            int rescode = httpConn.getResponseCode();
            if (rescode == 200) {
                BufferedReader bfw = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                res = bfw.readLine();
            } else {
                res = "Http request error code :" + rescode;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(res);
        return res;
    }

}
