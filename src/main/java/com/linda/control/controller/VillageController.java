package com.linda.control.controller;


import com.linda.control.domain.Village;
import com.linda.control.dto.message.Message;
import com.linda.control.service.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业
 * Created by ywang on 2017/4/19.
 */
@RestController
@RequestMapping("village")
public class VillageController {

  @Autowired
  private VillageService villageService;

  /**
   * 分页返回villages
   * @param village
   * @param page
   * @param size
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Message> getVillageList(Village village,int page,int size){
    return villageService.findByVillagePage(village, page, size);
  }

  /**
   * 新建village
   * @param village
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Message> createVillage(@RequestBody Village village){
    return villageService.createVillage(village);
  }

  /**
   * 返回一个village
   * @param villageId
   * @return
   */
  @RequestMapping(value = "{villageId}",method = RequestMethod.GET)
  public ResponseEntity<Message> getVillage(@PathVariable Long villageId){
    return villageService.getVillage(villageId);
  }

  /**
   * 修改小区信息
   * @param village
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}" , method = RequestMethod.PUT)
  public ResponseEntity<Message> updateVillage(@RequestBody Village village,@PathVariable Long id){
    return villageService.updateVillage(village);
  }

  /**
   * 删除小区
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Message> deleteVillage(@PathVariable  Long id){
    return villageService.deleteVillage(id);
  }
}
