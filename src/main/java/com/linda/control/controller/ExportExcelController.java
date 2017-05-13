package com.linda.control.controller;

/**
 * 导出报表入口
 * Created by ywang on 2017/3/8.
 */

import com.linda.control.service.ExcelService;
import com.linda.control.utils.DateUtils;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "excel")
public class ExportExcelController {

    @Autowired
    private ExcelService excelService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtils.simpleDateFormat, true));
    }
//
//    /**
//     * 安装师傅信息导出
//     * @param installPerson 画面端查询信息
//     * @param response 页面请求
//     */
//    @RequestMapping(value = "installPersonInfo",method = RequestMethod.GET)
//    public void installPersonInfo(InstallPerson installPerson, HttpServletResponse response){
//        List<InstallPersonDto> installPersonDtos = installPersonService.getResultAllList(installPerson);
//        try {
//            OutputStream out=response.getOutputStream();
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.addHeader("Content-Disposition", "attachment; filename="+ new String(new String("安装师傅信息".getBytes("gb2312"),"iso8859-1")+ ".xls"));
//            excelService.exportList("安装师傅信息", installPersonDtos,InstallPersonDto.class, response.getOutputStream());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 短信列表导出
//     *
//     * @param providerName
//     * @param simCode
//     * @param response
//     */
//    @RequestMapping(value = "/sms", method = RequestMethod.GET)
//    public void sms(String providerName, String simCode, HttpServletResponse response, @AuthenticationPrincipal SysUser loginUser) {
//        List<SmsDto> smsDtos = new ArrayList<SmsDto>();
//        // 如果超级管理员登入
//        if (loginUser.getUsername().equals("leaduadmin")) {
//            // 从cmpp中查询输入SIM卡条件下的所有SIM卡信息
//            List smsList = smsInterface.getSmsMessageList(CommonUtils.likePartten(simCode));
//            if (smsList != null && !smsList.isEmpty()) {
//                // 转换为SIM卡集合
//                List smsCardList = new ArrayList();
//                for (Object object : smsList) {
//                    ArrayList tempList = (ArrayList) object;
//                    Object[] temp = tempList.toArray();
//                    smsCardList.add(temp[0].toString());
//                }
//                // 将从cmpp中查到的SIM卡集合和输入的供应商作为检索条件查询SIM卡对应的供应商信息
//                List infoList = simCardService.getSimCardInfo(smsCardList, CommonUtils.likePartten(providerName));
//                if (smsList != null && !smsList.isEmpty()) {
//                    // 将查到的信息转换为Map（key为SIM卡号，value为供应商）
//                    Map infoMap = new HashMap();
//                    for (Object object : infoList) {
//                        Object[] temp = (Object[]) object;
//                        infoMap.put(temp[0].toString(), temp[1].toString());
//                    }
//                    for (Object object : smsList) {
//                        ArrayList tempList = (ArrayList) object;
//                        Object[] temp = tempList.toArray();
//                        // 通过SIM卡号关联两个结果集，整合数据
//                        Object cutomerName = infoMap.get(temp[0].toString());
//                        if (cutomerName != null) {
//                            smsDtos.add(new SmsDto(temp, cutomerName));
//                        }
//                    }
//                }
//            }
//        } else {
//            // 超级管理员以外的用户登入
//            // 从cmpp中查询输入SIM卡条件下的所有SIM卡信息
//            List smsList = smsInterface.getSmsMessageList(CommonUtils.likePartten(simCode), loginUser.getUsername());
//            if (smsList != null && !smsList.isEmpty()) {
//            }
//            List smsCardList = new ArrayList();
//            for (Object object : smsList) {
//                ArrayList tempList = (ArrayList) object;
//                Object[] temp = tempList.toArray();
//                smsCardList.add(temp[0].toString());
//            }
//            // 将从cmpp中查到的SIM卡集合和输入的供应商作为检索条件查询SIM卡对应的供应商信息
//            List infoList = simCardService.getSimCardInfo(smsCardList, CommonUtils.likePartten(providerName));
//            if (smsList != null && !smsList.isEmpty()) {
//                // 将查到的信息转换为Map（key为SIM卡号，value为供应商）
//                Map infoMap = new HashMap();
//                for (Object object : infoList) {
//                    Object[] temp = (Object[]) object;
//                    infoMap.put(temp[0].toString(), temp[1].toString());
//                }
//                for (Object object : smsList) {
//                    ArrayList tempList = (ArrayList) object;
//                    Object[] temp = tempList.toArray();
//                    // 通过SIM卡号关联两个结果集，整合数据
//                    Object cutomerName = infoMap.get(temp[0].toString());
//                    if (cutomerName != null) {
//                        smsDtos.add(new SmsDto(temp, cutomerName));
//                    }
//                }
//            }
//        }
//        try {
//            OutputStream out = response.getOutputStream();
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.addHeader("Content-Disposition", "attachment; filename=" + new String(new String("短信列表".getBytes("gb2312"), "iso8859-1") + ".xls"));
//            excelService.exportList("短信列表", smsDtos, SmsDto.class, response.getOutputStream());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

