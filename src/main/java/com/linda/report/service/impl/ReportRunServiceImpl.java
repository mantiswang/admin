package com.linda.report.service.impl;


import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.TubaMapInterface;
import com.linda.control.utils.DateUtils;
import com.linda.control.utils.TubaAnalyzeUtils;
import com.linda.control.utils.consts.TubaMap;
import com.linda.control.utils.state.UserType;
import com.linda.report.dto.report.ReportRunDto;
import com.linda.report.service.ReportRunService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 取行驶信息的实现类
 * <p>
 * Created by tianshuai on 2017/3/2.
 */
@Service
public class ReportRunServiceImpl implements ReportRunService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TubaMapInterface tubaMapInterface;

    @Autowired
    private SysUserRepository sysUserRepository;

    /**
     * 根据参数查询停止位置历史信息
     *
     * @param reportRunDto
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getReportRunHistoryList(ReportRunDto reportRunDto,
                                                           int page,
                                                           int size,
                                                           SysUser loginUser) {
        SysUser sysUser = sysUserRepository.findOne(loginUser.getId());
        List<Object> resultList = getResultList(page, size, reportRunDto, sysUser);
        List<ReportRunDto> listData;
        try {
            listData = convertDto(resultList);
        } catch (Exception ex) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "查询失败"), HttpStatus.OK);
        }
        int totalSize = getCount(reportRunDto, sysUser);
        Page pages = new PageImpl(listData, new PageRequest(page - 1, size), totalSize);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pages), HttpStatus.OK);
    }

    /**
     * 根据参数查询行驶历史信息,报表导出用
     *
     * @param reportRunDto
     * @param loginUser
     * @return
     */
    public List<ReportRunDto> getExportList(ReportRunDto reportRunDto, SysUser loginUser) {
        SysUser sysUser = sysUserRepository.findOne(loginUser.getId());
        List<Object> resultList = getReportResultList(reportRunDto, sysUser);
        List<ReportRunDto> listData = new ArrayList<>();
        try {
            listData = convertDto(resultList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listData;
    }

    /**
     * 获得分页数据
     *
     * @param page
     * @param size
     * @param reportRunDto
     * @return
     */
    private List<Object> getResultList(int page, int size, ReportRunDto reportRunDto, SysUser sysUser) {
        Query query = entityManager.createNativeQuery(getWhereSql(reportRunDto, 0, sysUser));
        query.setMaxResults(size);
        int limitFrom = (page - 1) * size;
        query.setFirstResult(limitFrom);
        setWherePara(reportRunDto, query, sysUser);
        return query.getResultList();
    }

    /**
     * 获得报表导出数据
     *
     * @param reportRunDto
     * @return
     */
    private List<Object> getReportResultList(ReportRunDto reportRunDto, SysUser sysUser) {
        Query query = entityManager.createNativeQuery(getWhereSql(reportRunDto, 0, sysUser));
        setWherePara(reportRunDto, query, sysUser);
        return query.getResultList();
    }

    /**
     * 获得总记录
     *
     * @param reportRunDto
     * @return
     */
    private int getCount(ReportRunDto reportRunDto, SysUser sysUser) {
        Query query = entityManager.createNativeQuery(getWhereSql(reportRunDto, 1, sysUser));
        setWherePara(reportRunDto, query, sysUser);
        List list = query.getResultList();
        return Integer.parseInt(list.get(0).toString());
    }

    /**
     * 构建查询语句
     *
     * @param reportRunDto
     * @param flag
     * @return
     */
    private String getWhereSql(ReportRunDto reportRunDto, Integer flag, SysUser loginUser) {
        StringBuffer querySql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();

        querySql.append(" SELECT ");
        if (flag == 0) {
            querySql.append(" rrh.id,rrh.sim_code,rrh.vehicle_identify_num,rrh.apply_num,rrh.vehicle_license_plate,  ");
            querySql.append(" rrh.start_lon,rrh.start_lat,rrh.end_lon,rrh.end_lat,rrh.run_start_time,rrh.run_stop_time, ");
            querySql.append(" rrh.run_duration,rrh.start_distance,rrh.end_distance,rrh.run_distance ");
        } else {
            querySql.append(" count(rrh.id) ");
        }
        querySql.append(" FROM report_run_history rrh ");
        whereSql.append(" WHERE rrh.run_duration <> 0 ");
        if (StringUtils.isNotEmpty(reportRunDto.getSimCode())) {
            whereSql.append(" AND rrh.sim_code = ?1 ");
        }
        if (StringUtils.isNotEmpty(reportRunDto.getVehicleIdentifyNum())) {
            whereSql.append(" AND rrh.vehicle_identify_num = ?2 ");
        }
        if (StringUtils.isNotEmpty(reportRunDto.getApplyNum())) {
            whereSql.append(" AND rrh.apply_num = ?3 ");
        }
        if (reportRunDto.getBeginTime() != null) {
            whereSql.append(" AND ?4 <= rrh.run_start_time ");
        }
        if (reportRunDto.getEndTime() != null) {
            whereSql.append(" AND rrh.run_stop_time <= ?5 ");
        }
        if (reportRunDto.getBeginTimeNm() != null) {
            whereSql.append(" AND ?6 <= DATE_FORMAT(rrh.run_start_time,'%Y-%m-%d') ");
        }
        if (reportRunDto.getEndTimeNm() != null) {
            whereSql.append(" AND DATE_FORMAT(rrh.run_stop_time,'%Y-%m-%d') <= ?7 ");
        }
        if (StringUtils.isNotEmpty(reportRunDto.getBeginTimeMs())) {
            whereSql.append(" AND ?8 <= CAST(DATE_FORMAT(rrh.run_start_time,'%k%i') as signed) ");
        }
        if (StringUtils.isNotEmpty(reportRunDto.getEndTimeMs())) {
            whereSql.append(" AND CAST(DATE_FORMAT(rrh.run_stop_time,'%k%i') as signed) <= ?9 ");
        }
        if (loginUser.getCustomer() != null) {//判断当前的用户是否是客户 若是客户则只显示和客户相关联的车架号

            querySql.append(" left join device dv on dv.vehicle_identify_num = rrh.vehicle_identify_num ");
            //如果是客户的一级管理员 则可以看见客户的所有车架号信息
            //若不是客户的一级管理员 则判断当前登录账号的所属分组 只能看见其所属分组中的车架号 如果没有分组可以看见全部
            if (loginUser.getUserType() == UserType.FIRST_ADMIN.value() ) {
                whereSql.append(" and dv.customer_id = ?10 ");
            } else {
                querySql.append(" left join vehicle_group_device vgd on vgd.device_id = dv.id  ");
                querySql.append(" left join vehicle_group vg on vg.customer_id = dv.customer_id and vg.id = vgd.vehicle_group_id ");
                querySql.append(" left join vehicle_group_sys_user vgsu on vgsu.vehicle_group_id = vg.id ");
                whereSql.append(" and dv.customer_id = ?10 ");
                whereSql.append(" and vgsu.sys_user_id = ?11 ");
            }
        }
        whereSql.append(" ORDER BY rrh.sim_code ASC,rrh.run_stop_time DESC ");
        return querySql.toString() + whereSql.toString();
    }

    /**
     * 构建查询参数
     *
     * @param reportRunDto
     * @param query
     */
    private void setWherePara(ReportRunDto reportRunDto, Query query, SysUser loginUser) {
        if (StringUtils.isNotEmpty(reportRunDto.getSimCode())) {
            query.setParameter(1, reportRunDto.getSimCode());
        }
        if (StringUtils.isNotEmpty(reportRunDto.getVehicleIdentifyNum())) {
            query.setParameter(2, reportRunDto.getVehicleIdentifyNum());
        }
        if (StringUtils.isNotEmpty(reportRunDto.getApplyNum())) {
            query.setParameter(3, reportRunDto.getApplyNum());
        }
        if (reportRunDto.getBeginTime() != null) {
            query.setParameter(4, reportRunDto.getBeginTime());
        }
        if (reportRunDto.getEndTime() != null) {
            query.setParameter(5, reportRunDto.getEndTime());
        }
        if (reportRunDto.getBeginTimeNm() != null) {
            query.setParameter(6, reportRunDto.getBeginTimeNm());
        }
        if (reportRunDto.getEndTimeNm() != null) {
            query.setParameter(7, reportRunDto.getEndTimeNm());
        }
        if (StringUtils.isNotEmpty(reportRunDto.getBeginTimeMs())) {
            query.setParameter(8, DateUtils.convertHHmmssToInt(reportRunDto.getBeginTimeMs()));
        }
        if (StringUtils.isNotEmpty(reportRunDto.getEndTimeMs())) {
            query.setParameter(9, DateUtils.convertHHmmssToInt(reportRunDto.getEndTimeMs()));
        }
        if (loginUser.getCustomer() != null) {//判断当前的用户是否是客户 若是客户则只显示和客户相关联的车架号

            //如果是客户的一级管理员 则可以看见客户的所有车架号信息
            //若不是客户的一级管理员 则判断当前登录账号的所属分组 只能看见其所属分组中的车架号 如果没有分组可以看见全部
            if (loginUser.getUserType() == UserType.FIRST_ADMIN.value()) {
                query.setParameter(10, loginUser.getCustomer().getId());
            } else {
                query.setParameter(10, loginUser.getCustomer().getId());
                query.setParameter(11, loginUser.getId());
            }
        }
    }

    private List<ReportRunDto> convertDto(List<Object> objectListData) throws Exception {
        List<ReportRunDto> reportRunList = new ArrayList<>();
        for (Object obj : objectListData) {
            Object[] temp = (Object[]) obj;
            ReportRunDto dto = new ReportRunDto(temp);
            // 取位置信息
            dto.setStartAddress(getAddressByLonLat(dto.getStartLon() + "," + dto.getStartLat()));
            dto.setEndAddress(getAddressByLonLat(dto.getEndLon() + "," + dto.getEndLat()));
            // 设置时长
            dto.setTimeSize(DateUtils.convertDisplayNameByTime(dto.getRunStartTime(), dto.getRunStopTime()));
            dto.setRunDistanceSize(String.valueOf(dto.getEndDistance() - dto.getStartDistance()) + "公里");
            reportRunList.add(dto);
        }
        return reportRunList;
    }

    private String getAddressByLonLat(String lonLat) throws Exception {
        // 经纬度转换
        String latLon_02_json = tubaMapInterface.get02Latlon(lonLat);
        Map<String, Double> resultMap = TubaAnalyzeUtils.getLonLatFromJson(latLon_02_json);
        // 取中文地址
        String address_json = tubaMapInterface.getInverseGeocoding(
                TubaMap.DEFAULT_LEV.value(), TubaMap.ZOOM.value(),
                resultMap.get(TubaAnalyzeUtils.KEY_LON) + "," + resultMap.get(TubaAnalyzeUtils.KEY_LAT),
                TubaMap.ROAD.value(),
                TubaMap.G_02.value(),
                TubaMap.G_02.value());
        return TubaAnalyzeUtils.getAddressFromJson(address_json);
    }
}
