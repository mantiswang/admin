package com.linda.control.dao;

import com.linda.control.domain.SysResource;
import com.linda.control.domain.SysRole;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ywang on 2016/12/9.
 */
public interface SysResourceRepository extends JpaRepository<SysResource,Long> {
    List<SysResource> findDistinctBySysRolesInAndTypeOrderBySortAsc(List<SysRole> sysRoles, Integer integer);
    List<SysResource> findByOrderByCreateTimeDesc();
    List<SysResource> findBySysRolesId(Long id);
    List<SysResource> findByParentIdIsNull();

}
