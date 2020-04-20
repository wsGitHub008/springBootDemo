package cn.itws.mallmanage;


import cn.itws.mallmanage.mbg.model.PmsBrand;
import cn.itws.mallmanage.service.PmsBrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallManageApplicationTests {
    @Autowired
    private PmsBrandService pmsBrandService;

    @Test
    public void  getBrandList() {
        List<PmsBrand> pmsBrands = pmsBrandService.listAllBrand();

        for (PmsBrand pmsBrand : pmsBrands) {
            System.out.println(pmsBrand);
        }
    }

}
