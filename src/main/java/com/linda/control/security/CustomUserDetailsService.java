package com.linda.control.security;

import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by pengchao on 2016/9/12.
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName){
        SysUser sysUser = sysUserRepository.findByUsername(userName);
        if(sysUser == null){
            throw new UsernameNotFoundException("系统中不存在该用户。");
        }
        return sysUser;
    }
}
