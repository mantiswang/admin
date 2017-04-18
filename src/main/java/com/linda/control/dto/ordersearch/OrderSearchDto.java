package com.linda.control.dto.ordersearch;

import com.linda.control.domain.LeaseCustomer;
import com.linda.control.domain.LeaseOrder;
import com.linda.control.domain.LeaseVehicle;
import com.linda.control.utils.DateUtils;
import com.linda.control.utils.state.InstallerStatus;
import lombok.Data;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/4/1.
 * <p>
 * 订单查询实体类副本
 */
@Data
public class OrderSearchDto {

    private static String 有线 = "有线";
    private static String 无线 = "无线";
    private static String 有线无线 = "有线 + 无线";
    private static String 新增订单 = "新增订单";
    private static String 已放款 = "已放款";
    private static String 取消 = "取消";
    private String orderNum;//订单编号

    private Date orderDate;//订单时间

    private String dmlxr;//店面联系人

    private String dmlxdh;//店面电话

    private Date expectDate;// 要求安装时间

    private String vehicleIdentifyNum;//车架号码

    private String remark;//备注

    private String deviceType;//设备型号1：有线；2：无线；3：有线+无线

    private String address;//安装地址

    private String province;//安装地址所在省

    private String city;//安装地址所在市

    private String installer;//订单（安装）归属

    private String status;//订单状态 0：新增订单  1：已放款  2：修改  9：取消

    private String modifyReason;//修改原因

    private String operator;//操作人

    private String cancelReason;//取消原因

    private Date loanDate;// 放款时间

    private String leaseCustomerName;//客户名称

    private String userIdentityNumber;//证件号码

    private String userTel;//客户电话

    private String customerName;//所属客户名称

    private String provinceName;//所属省份名称

    private String sellerName;//所属经销商名称

    private LeaseOrder leaseOrder; // 订单信息

    private LeaseCustomer leaseCustomer; // 租凭用户细信息

    private LeaseVehicle leaseVehicle; // 租凭车辆信息

    public OrderSearchDto(Object[] objects) {
        this.orderNum = objects[0] == null ? "" : objects[0].toString();
        if (objects[1] != null) {
            this.orderDate = DateUtils.getDate(objects[1].toString(), DateUtils.simpleDateFormat);
        }
        this.dmlxr = objects[2] == null ? "" : objects[2].toString();
        this.dmlxdh = objects[3] == null ? "" : objects[3].toString();
        if (objects[4] != null) {
            this.expectDate = DateUtils.getDate(objects[4].toString(), DateUtils.simpleDateFormat);
        }
        this.vehicleIdentifyNum = objects[5] == null ? "" : objects[5].toString();
        this.remark = objects[6] == null ? "" : objects[6].toString();
        this.deviceType = objects[7] == null ? "" : objects[7].toString();
        if (this.deviceType.equals("1")) {
            this.deviceType = 有线;
        } else if (this.deviceType.equals("2")) {
            this.deviceType = 无线;
        } else if (this.deviceType.equals("3")) {
            this.deviceType = 有线无线;
        } else {
            this.deviceType = "";
        }
        this.address = objects[8] == null ? "" : objects[8].toString();
        this.province = objects[9] == null ? "" : objects[9].toString();
        this.city = objects[10] == null ? "" : objects[10].toString();
        this.installer = objects[11] == null ? "" : objects[11].toString();
        if (this.installer.equals(InstallerStatus.LUNUO.value().toString())) {
            this.installer = InstallerStatus.LUNUO.getName();
        } else if (this.installer.equals(InstallerStatus.ZIXING.value().toString())) {
            this.installer = InstallerStatus.ZIXING.getName();
        } else {
            this.installer = "";
        }
        if ((Integer) objects[12] == 0) {
            this.status = 新增订单;
        } else if ((Integer) objects[12] == 1) {
            this.status = 已放款;
        } else if ((Integer) objects[12] == 9) {
            this.status = 取消;
        } else {
            this.status = "";
        }
        this.modifyReason = objects[13] == null ? "" : objects[13].toString();
        this.operator = objects[14] == null ? "" : objects[14].toString();
        this.cancelReason = objects[15] == null ? "" : objects[15].toString();
        if (objects[16] != null) {
            this.loanDate = DateUtils.getDate(objects[16].toString(), DateUtils.simpleDateFormat);
        }
        this.userIdentityNumber = objects[17] == null ? "" : objects[17].toString();
        this.leaseCustomerName = objects[18] == null ? "" : objects[18].toString();
        this.userTel = objects[19] == null ? "" : objects[19].toString();
        this.customerName = objects[20] == null ? "" : objects[20].toString();
        this.provinceName = objects[21] == null ? "" : objects[21].toString();
        this.sellerName = objects[22] == null ? "" : objects[22].toString();

    }

    public OrderSearchDto() {
        this.leaseOrder = new LeaseOrder();
        this.leaseCustomer = new LeaseCustomer();
        this.leaseVehicle = new LeaseVehicle();
    }
}
