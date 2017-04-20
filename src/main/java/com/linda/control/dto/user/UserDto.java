package com.linda.control.dto.user;

import com.linda.control.domain.Village;
import com.linda.control.domain.SysResource;
import com.linda.control.domain.SysRole;
import com.linda.control.domain.SysUser;
import java.util.List;
import lombok.Data;

/**
 * Created by PengChao on 2016/11/1.
 */
@Data
public class UserDto {
    private Long id;

    private String username;

    private List<SysRole> roles;

    private List<SysResource> sysResources;

    private Village village;

    private String isModifyPassWord;

    public UserDto(){}

    public UserDto(SysUser sysUser, List<SysResource> sysResources){
        this.id = sysUser.getId();
        this.username = sysUser.getUsername();
        this.sysResources = sysResources;
        this.village = sysUser.getVillage();
        this.isModifyPassWord = sysUser.getIsModifyPassWord();
    }

    public UserDto(SysUser sysUser){
        this.id = sysUser.getId();
        this.username = sysUser.getUsername();
        this.roles = sysUser.getRoles();
        this.village = sysUser.getVillage();
        this.isModifyPassWord = sysUser.getIsModifyPassWord();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SysResource> getSysResources() {
        return sysResources;
    }

    public void setSysResources(List<SysResource> sysResources) {
        this.sysResources = sysResources;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Village getVillage(){
        return this.village;
    }
}
