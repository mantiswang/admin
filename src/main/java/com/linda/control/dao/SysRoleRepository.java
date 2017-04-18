package com.linda.control.dao;

import com.linda.control.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by qiaohao on 2016/10/25.
 */
public interface SysRoleRepository extends JpaRepository<SysRole,Long> {

    List<SysRole> findByNameLike(String name);

}
