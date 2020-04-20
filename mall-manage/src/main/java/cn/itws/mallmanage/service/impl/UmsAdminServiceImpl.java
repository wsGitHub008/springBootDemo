package cn.itws.mallmanage.service.impl;

import cn.itws.mallmanage.dao.UmsAdminRoleRelationDao;
import cn.itws.mallmanage.mbg.mapper.UmsAdminMapper;
import cn.itws.mallmanage.mbg.model.UmsAdmin;
import cn.itws.mallmanage.mbg.model.UmsAdminExample;
import cn.itws.mallmanage.mbg.model.UmsPermission;
import cn.itws.mallmanage.service.UmsAdminService;
import cn.itws.mallmanage.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;


/**
 * @Author swj
 * @date 2020/4/2 15:05
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    //定义异常变量
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;
    //注入加密工具(SpringSecurity框架)
    @Autowired
    private PasswordEncoder passwordEncoder;
    //注入用户校验工具(SpringSecurity框架)
    @Autowired
    private UserDetailsService userDetailsService;
    //注入Jwt工具
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //根据用户名获取后台管理员
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList
                = umsAdminMapper.selectByExample(umsAdminExample);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    //获取用户权限列表
    @Override
    public List<UmsPermission> getPermissionList(Long id) {

        return umsAdminRoleRelationDao.getPermissionList(id);
    }

    //注册功能
    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同的用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> adminList = umsAdminMapper.selectByExample(example);
        if (adminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        return umsAdmin;
    }

    //登录功能
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成token
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

}
