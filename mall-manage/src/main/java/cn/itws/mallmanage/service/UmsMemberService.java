package cn.itws.mallmanage.service;

import cn.itws.mallmanage.common.api.CommonResult;

/**
 * @Author swj
 * @date 2020/4/1 21:37
 */
public interface UmsMemberService {
    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);
}
