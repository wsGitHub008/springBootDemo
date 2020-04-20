package cn.itws.mallmanage.service;

import cn.itws.mallmanage.mbg.model.UmsAdmin;
import cn.itws.mallmanage.mbg.model.UmsPermission;

import java.util.List;

/**
 * @Author swj
 * @date 2020/4/2 15:04
 */
public interface UmsAdminService {

    UmsAdmin getAdminByUsername(String username);

    List<UmsPermission> getPermissionList(Long id);

    UmsAdmin register(UmsAdmin umsAdminParam);

    String login(String username, String password);
}
