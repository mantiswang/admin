package com.linda.control.service.impl;

import com.linda.control.dao.LeaseCustomerRepository;
import com.linda.control.dao.LeaseOrderRepository;
import com.linda.control.dao.LeaseVehicleRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.LeaseCustomer;
import com.linda.control.domain.LeaseOrder;
import com.linda.control.domain.LeaseVehicle;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.dto.ordersearch.OrderSearchDto;
import com.linda.control.service.OrderSearchService;
import com.linda.control.utils.CommonUtils;
import com.linda.control.utils.state.UserType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Created by yuanzhenxia on 2017/4/1.
 * <p>
 * 订单查询实现类
 */
@Service
public class OrderSearchServiceImpl implements OrderSearchService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private LeaseOrderRepository leaseOrderRepository;

    @Autowired
    private LeaseCustomerRepository leaseCustomerRepository;

    @Autowired
    private LeaseVehicleRepository leaseVehicleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private SysUser loginUser;

    /**
     * 分页返回订单信息
     *
     * @param page           页数
     * @param size           每页数据条数
     * @param orderSearchDto 查询条件
     * @param loginUser      用户信息
     * @return
     */
    public ResponseEntity<Message> getOrderPage(int page, int size, OrderSearchDto orderSearchDto, SysUser loginUser) {
        this.loginUser = sysUserRepository.findOne(loginUser.getId());
        int startIndex = (page - 1) * size;
        List<Object> resultList = getResultList(startIndex, size, orderSearchDto);
        List<OrderSearchDto> listData;
        try {
            listData = buildData(resultList);
        } catch (Exception ex) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "查询失败"), HttpStatus.OK);
        }
        int totalSize = getCount(orderSearchDto);
        Page pages = new PageImpl(listData, new PageRequest(page - 1, size), totalSize);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pages), HttpStatus.OK);
    }


    /**
     * 获得分页数据
     *
     * @param page           页数
     * @param size           每页条数
     * @param orderSearchDto 画面参数
     * @return
     */
    private List<Object> getResultList(int page, int size, OrderSearchDto orderSearchDto) {
        Query query = entityManager.createNativeQuery(getWhereSql(0,orderSearchDto));
        query.setMaxResults(size);
        query.setFirstResult(page);
        setWhereParam(orderSearchDto, query);
        return query.getResultList();
    }

    private int getCount(OrderSearchDto orderSearchDto) {
        Query query = entityManager.createNativeQuery(getWhereSql(1,orderSearchDto));
        setWhereParam(orderSearchDto, query);
        List list = query.getResultList();
        return Integer.parseInt(list.get(0).toString());
    }

    private List<OrderSearchDto> buildData(List<Object> resultList) {
        List<OrderSearchDto> orderSearchDtos = new ArrayList<OrderSearchDto>();
        for (Object object : resultList) {
            OrderSearchDto orderSearchDto = new OrderSearchDto((Object[]) object);
            orderSearchDtos.add(orderSearchDto);
        }
        return orderSearchDtos;
    }

    private String getWhereSql(Integer flag,OrderSearchDto orderSearchDto) {
        StringBuffer querySql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer();
        querySql.append(" select ");
        if (flag == 0) {
            querySql.append(" distinct lo.order_num ,lo.order_date,lo.dmlxr,lo.dmlxdh,lo.expect_date,lo.vehicle_identify_num");
            querySql.append(",lo.remark,lo.device_type,lo.address,lo.province, lo.city ,lo.installer, lo.status, lo.modify_reason, lo.operator");
            querySql.append(",lo.cancel_reason,lo.loan_date,lc.user_identity_number,lc.lease_customer_name,lc.user_tel,cus.name, pro.province_name,sel.seller_name");
        } else {
            querySql.append(" ifnull(count(distinct lo.order_num),0) ");
        }
        querySql.append(" from  lease_order lo  ");
        querySql.append(" left join lease_vehicle lvh on lvh.order_id = lo.id ");
        querySql.append(" left join lease_customer lc on lc.order_id = lo.id ");
        querySql.append(" left join customer cus on cus.id = lvh.customer_id ");
        querySql.append(" left join vehicle_province pro on pro.id = lvh.vehicle_province_id ");
        querySql.append(" left join vehicle_seller sel on sel.id = lvh.vehicle_seller_id ");
        whereSql.append(" where lo.vehicle_identify_num like ?2 ");
        whereSql.append(" and lo.order_num like ?1 ");
        if(orderSearchDto.getStatus()!=null&&!"88".equals(orderSearchDto.getStatus())){
            whereSql.append(" and lo.status = ?5 ");
        }
        whereSql.append(" and lc.user_identity_number like ?6 ");

        if (loginUser.getCustomer() != null && (loginUser.getUserType() == UserType.ORDINARY_USER.value() || loginUser.getUserType() == UserType.FIRST_ADMIN.value())) {
            //若不是客户的一级管理员 则判断当前登录账号的所属分组 只能看见其所属分组中的车架号 如果没有分组可以看见全部
            if (loginUser.getUserType() == UserType.FIRST_ADMIN.value() ) {
                whereSql.append(" and lvh.customer_id = ?3 ");
            } else {
                querySql.append(" left join vehicle_group vg on vg.customer_id = lvh.customer_id and vg.id = lvh.vehicle_group_id ");
                querySql.append(" left join vehicle_group_sys_user vgsu on vgsu.vehicle_group_id = vg.id ");
                whereSql.append(" and lvh.customer_id = ?3 ");
                whereSql.append(" and lc.customer_id = ?3 ");
                whereSql.append(" and vgsu.sys_user_id = ?4  ");
            }
        }
        whereSql.append(" ORDER BY lo.order_num DESC ");
        return querySql.toString() + whereSql.toString();
    }

    private void setWhereParam(OrderSearchDto orderSearchDto, Query query) {
        query.setParameter(1, CommonUtils.likePartten(orderSearchDto.getOrderNum()));
        query.setParameter(2, CommonUtils.likePartten(orderSearchDto.getVehicleIdentifyNum()));
        if (loginUser.getCustomer() != null && (loginUser.getUserType() == UserType.ORDINARY_USER.value() || loginUser.getUserType() == UserType.FIRST_ADMIN.value())) {
            //若不是客户的一级管理员 则判断当前登录账号的所属分组 只能看见其所属分组中的车架号 如果没有分组可以看见全部
            if (loginUser.getUserType() == UserType.FIRST_ADMIN.value() ) {
                query.setParameter(3, loginUser.getCustomer().getId());
            } else {
                query.setParameter(3, loginUser.getCustomer().getId());
                query.setParameter(4, loginUser.getId());
            }
        }
        if(orderSearchDto.getStatus()!=null&&!"88".equals(orderSearchDto.getStatus())){
            query.setParameter(5, Integer.parseInt(orderSearchDto.getStatus()));
        }
        query.setParameter(6, CommonUtils.likePartten(orderSearchDto.getUserIdentityNumber()));

    }
    /**
     * 根据订单编号取得订单明细
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Message> findOrderDetail(String applyNum ) {
        OrderSearchDto data = new OrderSearchDto();
        // 取得订单信息
        LeaseOrder leaseOrder = leaseOrderRepository.findTop1ByOrderNum(applyNum);
        // 取得客户信息
        LeaseCustomer leaseCustomer = leaseCustomerRepository.findByOrderId(leaseOrder.getId());
        // 取得车辆信息
        LeaseVehicle leaseVehicle = leaseVehicleRepository.findTop1ByOrderId(leaseOrder.getId());
        data.setLeaseOrder(leaseOrder);
        data.setLeaseCustomer(leaseCustomer);
        data.setLeaseVehicle(leaseVehicle);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS,data), HttpStatus.OK);
    }
}
