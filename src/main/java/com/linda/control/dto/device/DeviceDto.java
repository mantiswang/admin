package com.linda.control.dto.device;

import com.linda.control.domain.Device;
import com.linda.control.domain.LeaseOrder;
import lombok.Data;

/**
 * Created by qiaohao on 2017/3/17.
 */
@Data
public class DeviceDto {

    private Device device;

    private LeaseOrder leaseOrder;

}
