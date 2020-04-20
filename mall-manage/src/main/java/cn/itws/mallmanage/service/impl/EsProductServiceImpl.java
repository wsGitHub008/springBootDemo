package cn.itws.mallmanage.service.impl;

import cn.itws.mallmanage.dao.EsProductDao;
import cn.itws.mallmanage.nosql.elasticsearch.document.EsProduct;
import cn.itws.mallmanage.nosql.elasticsearch.repository.EsProductRepository;
import cn.itws.mallmanage.service.EsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author swj
 * @date 2020/4/5 15:37
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    //注入要写入索引库的接口
    @Autowired
    private EsProductDao ProductDao;
    //注入索引库接口
    @Autowired
    private EsProductRepository productRepository;

    //1、从数据库中导入所有商品到ES
    @Override
    public int importAll() {
        List<EsProduct> esProductList = ProductDao.getAllEsProductList(null);
        //添加到ES中
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
        //查看添加了多少条到ES中
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    //2、根据id删除商品
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    //3、根据id创建商品
    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> esProductList = ProductDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            result = productRepository.save(esProduct);
        }
        return result;
    }

    //4、批量删除商品
    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsProduct> esProductList = new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            productRepository.deleteAll(esProductList);
        }
    }

    //5、根据关键字搜索名称或者副标题
    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        //设置当前页和要显示的页数
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
