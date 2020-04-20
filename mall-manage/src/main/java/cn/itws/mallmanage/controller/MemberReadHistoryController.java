package cn.itws.mallmanage.controller;

import cn.itws.mallmanage.common.api.CommonPage;
import cn.itws.mallmanage.common.api.CommonResult;
import cn.itws.mallmanage.nosql.mongodb.document.MemberReadHistory;
import cn.itws.mallmanage.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员商品浏览记录管理Controller
 * @Author swj
 * @date 2020/4/5 19:22
 */
@RestController
@RequestMapping("/member/readHistory")
@Api(tags = "MemberReadHistoryController",description = "会员商品浏览记录管理")
public class MemberReadHistoryController {

    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @ApiOperation("创建浏览记录")
    @PostMapping("/create")
    public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量删除浏览记录")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("ids") List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        if (count > 0) {
           return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示浏览记录")
    @GetMapping("/list")
    public CommonResult<List<MemberReadHistory>> list(Long memberId) {
        List<MemberReadHistory> readHistoryList = memberReadHistoryService.list(memberId);
        return CommonResult.success(readHistoryList);
    }
}
