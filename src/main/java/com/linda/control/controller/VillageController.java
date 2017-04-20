package com.linda.control.controller;


import com.linda.control.domain.Company;
import com.linda.control.dto.message.Message;
import com.linda.control.service.CompanyService;
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
  private CompanyService companyService;

  /**
   * 分页返回客户列表
   * @param company
   * @param page
   * @param size
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Message> getCompanyList(Company company,int page,int size){
    return companyService.findByCompanyPage(company, page, size);
  }

  /**
   * 新建客户
   * @param company
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Message> createCompany(@RequestBody Company company){
    return companyService.createCompany(company);
  }

  /**
   * 返回一个客户
   * @param companyId
   * @return
   */
  @RequestMapping(value = "{companyId}",method = RequestMethod.GET)
  public ResponseEntity<Message> getCompany(@PathVariable Long companyId){
    return companyService.getCompany(companyId);
  }

  /**
   * 修改客户
   * @param company
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}" , method = RequestMethod.PUT)
  public ResponseEntity<Message> updateCompany(@RequestBody Company company,@PathVariable Long id){
    return companyService.updateCompany(company);
  }

  /**
   * 删除客户
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Message> deleteCompany(@PathVariable  Long id){
    return companyService.deleteCompany(id);
  }
}
