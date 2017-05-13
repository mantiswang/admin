package com.linda.control.utils.consts;

/**
 * Created by ywang on 2017/5/2.
 */
public enum ParkingSpaceStatus {

  /**
   * 默认状态
   */
  DEFAULT(1),
  /**
   * 招租中
   */
  LEASING(2),
  /**
   * 被租用
   */
  LEASED(3);



  private final Integer value;

  ParkingSpaceStatus(Integer value) {
    this.value = value;
  }

  public Integer value() {
    return this.value;
  }
}
