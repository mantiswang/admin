package com.linda.control.service;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by ywang on 2016/10/25.
 */
@Service
public interface SysUserService {
    /**
     * 分页返回用户列表
     * @param sysUser
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getSysUserList(SysUser sysUser,int page, int size,SysUser loginUser);
    /**
     * 更新用户
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> updateSysUser(SysUser sysUser);
    /**
     * 更新用户密码
     * @param id
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> updateSysUserPassword(Long id, SysUser sysUser);
    /**
     * 创建用户
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> createSysUser(SysUser sysUser, SysUser sysLoginUser);

    /**
     * 创建物业人员
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> createStaff(SysUser sysUser, SysUser sysLoginUser);
    /**
     * 删除用户
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteSysUser(Long id);
    /**
     * 根据id返回用户
     * @param id
     * @return
     */
    public ResponseEntity<Message> getSysUserById(Long id);
    public ResponseEntity<Message> changePassword(Long id, String oldPassword, String newPassword);

    /**
     * 分页返回物业人员列表
     * @param sysUser
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getStaffList(SysUser sysUser,int page, int size,SysUser loginUser);

}
