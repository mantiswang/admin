package com.linda.control.service;

import com.linda.control.domain.Village;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by ywang on 2017/4/19.
 */
@Service
public interface VillageService {
  /**
   * 分页返回客户列表
   * @param village
   * @param page
   * @param size
   * @return
   */
  public ResponseEntity<Message> findByVillagePage(Village village, int page, int size);

  /**
   * 创建一个客户 并保存客户的管理员账号以及设置一个默认的一级管理员角色
   * @param village
   * @return
   */
  public ResponseEntity<Message> createVillage(Village village);
  /**
   * 返回一个客户
   * @param villageId
   * @return
   */
  public ResponseEntity<Message> getVillage(Long villageId);
  /**
   * 更新客户
   * @param village
   * @return
   */
  public ResponseEntity<Message> updateVillage(Village village);
  /**
   * 删除客户，双向关联原因 先置空管理员中的客户 再进行删除
   * @param id
   * @return
   */
  public ResponseEntity<Message> deleteVillage(Long id);
}
