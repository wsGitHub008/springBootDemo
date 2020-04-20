package cn.itws.mallmanage.controller;

import cn.itws.mallmanage.common.api.CommonPage;
import cn.itws.mallmanage.common.api.CommonResult;
import cn.itws.mallmanage.mbg.model.PmsBrand;
import cn.itws.mallmanage.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理
 *
 * @Author swj
 * @date 2020/4/1 16:11
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "PmsBrandController",description = "商品品牌管理")
public class PmsBrandController {
    @Autowired
    private PmsBrandService pmsBrandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);
    /**
     * 1、显示所有的品排
     */
    @ApiOperation("获取所有品牌列表")
    @GetMapping("/listAll")
    //- 给查询接口添加`pms:brand:read`权限
    //- 给修改接口添加`pms:brand:update`权限
    //- 给删除接口添加`pms:brand:delete`权限
    //- 给添加接口添加`pms:brand:create`权限
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    /**
     * 2、添加品牌
     */
    @ApiOperation("添加品牌")
    @PostMapping("/create")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    /**
     * 3、更新
     */
    @ApiOperation("更新品牌")
    @PostMapping("/update/{id}")
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result) {
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("createBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    /**
     * 4、删除
     */
    @ApiOperation("删除品牌")
    @GetMapping("/delete/{id}")
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        CommonResult commonResult;
        int count = pmsBrandService.deleteBrand(id);
        if (count == 1) {
            commonResult = CommonResult.success(null);
            LOGGER.debug("createBrand success:id={}", id);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:id={}", id);
        }
        return commonResult;
    }

    /**
     * 5、品牌分页
     */
    @ApiOperation("品牌分页")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsBrand>> listBrand(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize
    ) {
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(pmsBrands));
    }

    /**
     * 6、根据id查询品牌
     */
    @ApiOperation("查询品牌")
    @GetMapping("/{id}")
    public CommonResult<PmsBrand> findBrandById(@PathVariable("id") Long id) {
        return CommonResult.success(pmsBrandService.getBrand(id));
    }
}
