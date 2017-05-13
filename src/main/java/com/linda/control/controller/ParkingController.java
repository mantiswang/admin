package com.linda.control.controller;

import com.linda.control.domain.Parking;
import com.linda.control.dto.message.Message;
import com.linda.control.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ywang on 2017/5/2.
 */
@RestController
@RequestMapping("parking")
public class ParkingController {

  @Autowired
  private ParkingService parkingService;

  /**
   * 分页返回车位列表
   * @param parking
   * @param page
   * @param size
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Message> getParkingList(Parking parking,int page,int size){
    return parkingService.findByParkingPage(parking, page, size);
  }

  /**
   * 新建车位
   * @param parking
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Message> createParking(@RequestBody Parking parking){
    return parkingService.createParking(parking);
  }

  /**
   * 返回一个客户
   * @param parkingId
   * @return
   */
  @RequestMapping(value = "{parkingId}",method = RequestMethod.GET)
  public ResponseEntity<Message> getParking(@PathVariable Long parkingId){
    return parkingService.getParking(parkingId);
  }

  /**
   * 修改车位
   * @param parking
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}" , method = RequestMethod.PUT)
  public ResponseEntity<Message> updateParking(@RequestBody Parking parking,@PathVariable Long id){
    return parkingService.updateParking(parking);
  }

  /**
   * 删除客户
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Message> deleteParking(@PathVariable  Long id){
    return parkingService.deleteParking(id);
  }
}
