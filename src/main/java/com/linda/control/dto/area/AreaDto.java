package com.linda.control.dto.area;

import com.linda.control.domain.Area;
import com.linda.control.domain.AreaCar;
import com.linda.control.domain.AreaDetail;
import com.linda.control.dto.leasevehicle.LeaseVehicleDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by qiaohao on 2017/3/3.
 */
@Data
public class AreaDto {
    private Area area;
    private Integer flag;
    private List<String> cars;
    private List<String> lonlatstrArr;
    private Map<String,LeaseVehicleDto> leaseVehicleDtoMaps;
}
