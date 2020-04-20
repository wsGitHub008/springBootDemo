package cn.itws.mallmanage.controller;

import cn.itws.mallmanage.common.api.CommonResult;
import cn.itws.mallmanage.dto.OssCallbackResult;
import cn.itws.mallmanage.dto.OssPolicyResult;
import cn.itws.mallmanage.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author swj
 * @date 2020/4/6 14:30
 */
@RestController
@RequestMapping("/aliyun/oss")
@Api(tags = "OssController",description = "Oss管理")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "oss上传签名生成")
    @GetMapping("/policy")
    public CommonResult<OssPolicyResult>policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }


    @ApiOperation(value = "oss上传成功回调")
    @PostMapping("/callback")
    public CommonResult<OssCallbackResult>callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }
}
