package com.linda.wechat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 安装人员表
 * Created by pengchao on 2017/2/18.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class InstallPerson implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name; //安装人员姓名

    @Column(unique = true)
    private String phoneNum; //安装人员手机号

    @Column(unique = true)
    private String cardId; //身份证号

    private String providerName; //安装服务商全称

    private String providerProperty; //安装服务商属性

    private String addr; //安装人员所在地址

    private Date effectiveDate; // 启用日期

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public InstallPerson(){

    }

    public InstallPerson(Long id){
        this.id = id;
    }

}
