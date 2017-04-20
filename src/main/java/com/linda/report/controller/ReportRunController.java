package com.linda.report.controller;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.report.dto.report.ReportRunDto;
import com.linda.report.service.ReportRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 行驶报表的Controller
 * <p>
 * Created by tianshuai on 2017/3/2
 */
@RestController
@RequestMapping("runReport")
public class ReportRunController {

    @Autowired
    private ReportRunService reportRunService;

    /**
     * 取位置历史信息
     *
     * @param reportRunDto
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "getHistory", method = RequestMethod.GET)
    public ResponseEntity<Message> getStopPositionHistoryList(ReportRunDto reportRunDto,
                                                              int page,
                                                              int size,
                                                              @AuthenticationPrincipal SysUser sysUser) {
        return reportRunService.getReportRunHistoryList(reportRunDto, page, size, sysUser);
    }
}
