package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PengChao on 2016/11/1.
 */
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysResource implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer type = 0 ; // 0:菜单  1：主键
    private String res;
    private Integer sort;
    private String icon;
    private Long parentId;

    @ManyToMany(mappedBy = "sysResources" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SysRole> sysRoles;
    /**
     * 资源与自身父菜单关联
     */
    @OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
    private List<SysResource> children =new LinkedList<SysResource>();

    /**
     * 资源与自身子菜单关联
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parentId",insertable=false,updatable=false)
    @JsonIgnore
    private SysResource parent;



    @CreatedBy
    private String createUser;

    @CreatedDate
    private Date createTime;

    @LastModifiedBy
    private String updateUser;

    @LastModifiedDate
    private Date updateTime;


}
