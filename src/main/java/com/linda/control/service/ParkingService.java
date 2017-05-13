package com.linda.control.service;

import com.linda.control.domain.Parking;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by ywang on 2017/5/2.
 */
@Service
public interface ParkingService {

  /**
   * 分页返回车位列表
   * @param parking
   * @param page
   * @param size
   * @return
   */
  public ResponseEntity<Message> findByParkingPage(Parking parking, int page,int size);

  /**
   * 创建一个车位
   * @param parking
   * @return
   */
  public ResponseEntity<Message> createParking(Parking parking);
  /**
   * 返回一个车位
   * @param parkingId
   * @return
   */
  public ResponseEntity<Message> getParking(Long parkingId);
  /**
   * 更新车位
   * @param parking
   * @return
   */
  public ResponseEntity<Message> updateParking(Parking parking);
  /**
   * 删除车位
   * @param id
   * @return
   */
  public ResponseEntity<Message> deleteParking(Long id);

}
