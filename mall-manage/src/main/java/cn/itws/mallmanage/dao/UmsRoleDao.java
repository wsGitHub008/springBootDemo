package cn.itws.mallmanage.dao;

import cn.itws.mallmanage.mbg.model.UmsMenu;
import cn.itws.mallmanage.mbg.model.UmsResource;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户角色自定义Dao
 * Created by macro on 2020/2/2.
 */
public interface UmsRoleDao {
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);

    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
