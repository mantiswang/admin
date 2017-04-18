package com.linda.control.service.impl;

import com.linda.control.config.FileUploadProperties;
import com.linda.control.dao.SimCardRepository;
import com.linda.control.domain.SimCard;
import com.linda.control.dto.batch.SimCardDto;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.FileUploadService;
import com.linda.control.service.SimCardService;
import com.linda.control.utils.Hanyu;
import com.linda.control.utils.SqlUtils;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qiaohao on 2017/2/16.
 */
@Service
public class SimCardServiceImpl implements SimCardService {


    @Autowired
    private SimCardRepository simCardRepository;

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private FileUploadService fileUploadService;

    private JobParameters jobParameters;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importSimCardJob;


    public ResponseEntity<Message> findBySimCardPage(SimCard simCard , int page ,int size){

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page simCards = simCardRepository.findAll(Example.of(simCard, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, simCards);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 查询所有的sim卡号
     * @param simCard
     * @return
     */
    public List<SimCard> findBySimCard(SimCard simCard){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        List<SimCard> simCards =simCardRepository.findAll(Example.of(simCard, exampleMatcher),new Sort(Sort.Direction.DESC,"createTime"));
        return simCards;
    }


    /**
     * 判断sim卡号是否在白名单中  ---发送网关短信时使用
     * @param simCode
     * @return
     */
    public Boolean isInSimCards(String simCode){
        SimCard simCard = simCardRepository.findTop1BySimCode(simCode);
        if(null != simCard){
            return true;
        }
        return false;
    }

    /**
     * 导入sim卡号
     * @param file
     * @return
     */
    public ResponseEntity<Message> impSimCards(MultipartFile file)throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Class clazz = SimCardDto.class;
        String fileName = fileUploadService.excelFileUpload(file);
        if(!validateExcel(fileName)){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "导入失败,文件格式错误"),HttpStatus.OK);
        }
        //运行一个job导入数据到 sim_card表中
        jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("input.file.name", fileUploadProperties.getImportExcelService()+fileName)
                .addString("sql", SqlUtils.getSimCardSql())
                .addString("targetType", clazz.getName())
                .addLong("skipLines", 1L)
                .toJobParameters();
        if(jobLauncher.run(importSimCardJob,jobParameters).getStatus()  == BatchStatus.FAILED ){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "sim卡导入失败"),HttpStatus.OK);
        }else{
            //运行一个job导入数据到device表中
            jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("input.file.name", fileUploadProperties.getImportExcelService()+fileName)
                    .addString("sql", SqlUtils.getDeviceSql())
                    .addString("targetType", clazz.getName())
                    .addLong("skipLines", 1L)
                    .toJobParameters();
            if(jobLauncher.run(importSimCardJob,jobParameters).getStatus()  == BatchStatus.FAILED ){
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "sim卡导入失败"),HttpStatus.OK);
            } else {
                //运行一个job导入数据到deviceLog表中
                jobParameters = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("input.file.name", fileUploadProperties.getImportExcelService()+fileName)
                        .addString("sql", SqlUtils.getDeviceLogSql())
                        .addString("targetType", clazz.getName())
                        .addLong("skipLines", 1L)
                        .toJobParameters();
                if(jobLauncher.run(importSimCardJob,jobParameters).getStatus()  == BatchStatus.FAILED ){
                    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "sim卡导入失败"),HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 判断需要导入的excel格式是否正确
     * @param pathToFile
     * @return
     */
    public Boolean validateExcel(String pathToFile){
        FileInputStream fileInputStream;
        try
        {
            fileInputStream = new FileInputStream(new File(fileUploadProperties.getImpExcelPath()+pathToFile));
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            //XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);//.xlsx  xls
            fileInputStream.close();
            Sheet sheet = workbook.getSheetAt(0);
            List<String> headers = new ArrayList<>();
            Hanyu hanyu = new Hanyu();
            Row row = (Row) sheet.iterator().next();
            for (Iterator<Cell> iterator2 = row.iterator(); iterator2.hasNext();) {
                Cell cell = (Cell) iterator2.next();
                headers.add(hanyu.getStringPinYin(cell.getStringCellValue()));
            }

            List<String> templateTitle = Lists.newArrayList("SIMhao","chuanhao","suoshukehu","kapianzhuangtai","kapianleixing");
            if(headers.size() != templateTitle.size()){
                return false;
            }
            for(String header : headers){
                if(!templateTitle.contains(header)){
                    return false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 查询sim卡号和客户信息
     * @param smsCardList
     * @param providerName
     * @return
     */
    public List getSimCardInfo(List smsCardList, String providerName){
        return simCardRepository.getSimCardInfo(smsCardList, providerName);
    }

    /**
     * 查询sim卡号和客户信息
     * @param smsCardList
     * @param providerName
     * @return
     */
    public List getSimCardInfoAdmin1(List smsCardList, String providerName,String customer_id){
        return simCardRepository.getSimCardInfoByAdmin1(smsCardList, providerName, customer_id);
    }

    /**
     * 获取去重后的simList
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSimCardList(String providerName, String simCode){
        return simCardRepository.getSimCardList(providerName, simCode);
    }




}
