package com.linda.control.dto.leasevehicle;

import com.linda.control.utils.CommonUtils;
import lombok.Data;

import java.util.List;

/**
 * Created by qiaohao on 2017/3/3.
 */
@Data
public class LeaseVehicleDto {

    private String vehicleIdentifyNum;
    private String orderNum;
    private String vehicleLicensePlate;
    private String leaseCustomerName;
    private String userTel;
    private List<String> vehicleGroups;


    public void setVal(Object [] objects){
        vehicleIdentifyNum= CommonUtils.getStr(objects[0]);
        orderNum=CommonUtils.getStr(objects[1]);
        vehicleLicensePlate=CommonUtils.getStr(objects[2]);
        leaseCustomerName=CommonUtils.getStr(objects[3]);
        userTel=CommonUtils.getStr(objects[4]);
    }

}
