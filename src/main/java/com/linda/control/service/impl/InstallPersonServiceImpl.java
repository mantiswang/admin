package com.linda.control.service.impl;

import com.linda.control.config.FileUploadProperties;
import com.linda.wechat.dao.InstallPersonRepository;
import com.linda.wechat.domain.InstallPerson;
import com.linda.control.dto.installperson.InstallPersonDto;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.FileUploadService;
import com.linda.control.utils.Hanyu;
import com.linda.control.utils.SqlUtils;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.linda.control.service.InstallPersonService;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yuanzhenixa on 2017/3/2.
 *
 * 安装师傅信息查询接口实现类
 */
@Service
public class InstallPersonServiceImpl implements InstallPersonService{
    @Autowired
    private InstallPersonRepository installPersonRepository;

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private FileUploadService fileUploadService;

    private JobParameters jobParameters;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importInstallPersonJob;

    /**
     * 分页获取安装师傅信息
     *
     * @param installPerson 画面端查询条件
     * @param page  页数
     * @param size  每页条数
     * @return
     */
    public ResponseEntity<Message> findByInstallPersonPage(InstallPerson installPerson , int page , int size){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page installPersons = installPersonRepository.findAll(Example.of(installPerson, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createDate")));
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, installPersons);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 导入安装师傅信息
     * @param file   导入文件信息
     * @return
     */
    public ResponseEntity<Message> impInstallPersons(MultipartFile file)throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Class installPerson = InstallPersonDto.class;
        String fileName = fileUploadService.excelFileUpload(file);
        if(!validateExcel(fileName)){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "文件格式错误"),HttpStatus.OK);
        }
        //运行一个job导入数据到安装师傅信息表中

        jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("input.file.name", fileUploadProperties.getImportExcelService()+fileName)
                .addString("sql", SqlUtils.installPersonSql2())
                .addString("targetType", installPerson.getName())
                .addLong("skipLines", 1L)
                .toJobParameters();
        jobLauncher.run(importInstallPersonJob,jobParameters);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }


    /**
     * 判断需要导入的excel格式是否正确
     * @param pathToFile  文件名
     * @return
     */
    public Boolean validateExcel(String pathToFile){
        FileInputStream fileInputStream;
        try
        {
            fileInputStream = new FileInputStream(new File(fileUploadProperties.getImpExcelPath()+pathToFile));
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);//.xlsx  xls
            fileInputStream.close();
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<String> headers = new ArrayList<>();
            Hanyu hanyu = new Hanyu();
            Row row = (Row) sheet.iterator().next();
            for (Iterator<Cell> iterator2 = row.iterator(); iterator2.hasNext();) {
                Cell cell = (Cell) iterator2.next();
                headers.add(hanyu.getStringPinYin(cell.getStringCellValue()));
            }
            List<String> templateTitle = Lists.newArrayList("xingming","shoujihao","shenfenzhenghaoma","anzhuangfuwushangquancheng","anzhuangfuwushangshuxing","anzhuangdiqu","qiyongriqi");
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
     * 导出所有安装师傅信息数据
     * @param installPerson 画面端查询信息
     * @return
     */
    public List<InstallPersonDto> getResultAllList(InstallPerson installPerson){
        List<Object> resultList = installPersonRepository.getInstallPersonInfo(installPerson.getName(),installPerson.getName(),installPerson.getPhoneNum(),installPerson.getPhoneNum());
        try {
            return convertDto(resultList);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
    private List<InstallPersonDto> convertDto(List<Object> objectListData) throws Exception {
        List<InstallPersonDto> reportStopList = new ArrayList<>();
        for (Object obj : objectListData) {
            Object[] temp = (Object[]) obj;
            InstallPersonDto dto = new InstallPersonDto(temp);
            reportStopList.add(dto);
        }
        return reportStopList;
    }
}
