package cn.itws.mallmanage.nosql.elasticsearch.repository;

import cn.itws.mallmanage.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author swj
 * @date 2020/4/5 15:28
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct,Long> {

    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
