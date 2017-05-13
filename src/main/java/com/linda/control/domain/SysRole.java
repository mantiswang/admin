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
import java.util.List;

/**
 * Created by ywang on 2016/10/25.
 */

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRole implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String value;


    @CreatedBy
    private String createUser;

    @CreatedDate
    private Date createTime;

    @LastModifiedBy
    private String updateUser;

    @LastModifiedDate
    private Date updateTime;


    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SysUser> users;

    @ManyToMany
    @JoinTable(name = "sys_role_sys_resource", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    private List<SysResource> sysResources;


}
