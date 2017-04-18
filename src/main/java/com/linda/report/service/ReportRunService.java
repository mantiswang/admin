//package com.linda.report.service;
//
//import com.linda.control.domain.SysUser;
//import com.linda.control.dto.message.Message;
//import com.linda.report.dto.report.ReportRunDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by tianshuai on 2017/3/2.
// */
//@Service
//public interface ReportRunService {
//
//    /**
//     * 根据参数查询行驶历史信息
//     *
//     * @param reportRunDto
//     * @param page
//     * @param size
//     * @param sysUser
//     * @return
//     */
//    ResponseEntity<Message> getReportRunHistoryList(ReportRunDto reportRunDto,
//                                                    int page,
//                                                    int size,
//                                                    SysUser sysUser);
//
//    /**
//     * 根据参数查询行驶历史信息,报表导出用
//     *
//     * @param reportRunDto
//     * @param sysUser
//     * @return
//     */
//    List<ReportRunDto> getExportList(ReportRunDto reportRunDto, SysUser sysUser);
//}
