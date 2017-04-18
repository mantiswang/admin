package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by huzongcheng on 2017/3/20.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class SimMsg {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Date createTime;

    private String destterminalIid;

    private String msgContent;//短信内容

    private String msgId;//短信ID

    private Long sequenceId;

    private String terminald;//sim卡号

    private Long type;//类型：收/发

    private String operator;//发送人信息

}
