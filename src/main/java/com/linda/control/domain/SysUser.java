package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by LEO on 16/8/26.
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class SysUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String username;//登录账号

    private String password;//密码

    private String fullName;//用户姓名

    private Integer userType; //0.超级管理员 1.一级管理员 2.普通用户

    private String phone;//手机号

    private String email;//邮箱

    private Date enableTime;//启用日期

    private Date disableTime;//停用日期

    @JsonIgnore
    @ManyToOne
    private Customer customer;//所属客户

    @Column(columnDefinition = "varchar(1) default '1' ")
    private String userStatus;//用户状态 0.停用 1.启用

    @Column(columnDefinition = "varchar(1) default '1' ")
    private String isModifyPassWord;//是否可以修改密码 0.不可以 1.可以

    @Column(columnDefinition = "varchar(1) default '1' ")
    private String isMoreoverLogin;//是否可以同时登录 0.不可以 1.可以

    @Column(columnDefinition = "varchar(1) default '1' ")
    private String isPhoneLogin;//是否可以在手机上登录 0.不可以 1.可以

    @Column(columnDefinition = "varchar(1) default '1' ")
    private String isWechatLogin;//是否可以微信登录 0.不可以 1.可以

    @CreatedBy
    private String createUser;

    @CreatedDate
    private Date createTime;

    @LastModifiedBy
    private String updateUser;

    @LastModifiedDate
    private Date updateTime;

    @ManyToMany
    @JoinTable(name = "sys_user_sys_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<SysRole> roles;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
