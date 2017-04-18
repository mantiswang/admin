package com.linda.control.dao;

import com.linda.control.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by PengChao on 16/9/23.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUsername(String username);
    Page<SysUser> findByIdNotInAndUserTypeNotInOrderByCreateTimeDesc(long id,int userType,Pageable pageable);
    Page<SysUser> findByIdNotInAndUsernameLikeAndUserTypeNotInOrderByCreateTimeDesc(long id, String username, int userType, Pageable pageable);
    Page<SysUser> findByIdNotInAndFullNameLikeAndUserTypeNotInOrderByCreateTimeDesc(long id,String fullName, int userType, Pageable pageable);
    Page<SysUser> findByIdNotInAndUsernameLikeAndFullNameLikeAndUserTypeNotInOrderByCreateTimeDesc(long id, String username, String fullName, int userType, Pageable pageable);
/*
    @Query(nativeQuery = true, value = "SELECT lc.lease_customer_name,lv.vehicle_identify_num FROM lease_vehicle lv, lease_customer lc WHERE \n" +
            "lv.vehicle_identify_num LIKE ?1 AND lc.user_identity_number = lv.user_identity_number")
    Page<SysUser> getSysUser(SysUser sysUser, Pageable pageable);*/

    /**
     * 从设备类型表和车机类型表中，查询设备信息
     *
     */
    /*@Query(nativeQuery = true, value = "SELECT dt.type as type," +
            "dt.device_type_name as deviceTypeName,\n" +
            "dt.remark as remark,\n" +
            "vt.name as name,\n" +
            "vt.code as code ,\n" +
            "dt.id as id ,\n" +
            "dt.vehicle_type_id as vehicletypeid, \n" +
            "dt.create_time as dt_create_time,\n"+
            "dt.create_user as dt_create_user,\n"+
            "vt.create_time as vt_create_time,\n"+
            "vt.create_user as vt_create_user \n"+
            "FROM device_type dt, vehicle_type vt WHERE \n" +
            "dt.vehicle_type_id = vt.id and (dt.type = ?3 or ?4 = '') and (vt.name LIKE %?5% or ?6 = '')ORDER BY dt.update_time \n"+ "Limit ?1,?2")
    List<Object> getSysUserOfAdmin(int startIndex, int sized, String type1, String type2, String name1, String name2);*/
}
