package cn.itws.mallmanage.controller;

import cn.itws.mallmanage.common.api.CommonResult;
import cn.itws.mallmanage.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author swj
 * @date 2020/4/1 21:34
 */
@RestController
@RequestMapping("/sso")
@Api(tags = "UmsMemberController",description = "会员注册登录管理")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @PostMapping ("/verifyAuthCode")
    public CommonResult verifyAuthCode(@RequestParam String telephone,@RequestParam String authCode) {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }
}
