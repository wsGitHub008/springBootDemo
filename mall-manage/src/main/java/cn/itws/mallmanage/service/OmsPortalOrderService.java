package cn.itws.mallmanage.service;

import cn.itws.mallmanage.common.api.CommonResult;
import cn.itws.mallmanage.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author swj
 * @date 2020/4/5 20:06
 */
public interface OmsPortalOrderService {
    /**
     * 根据提交信息生成订单
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);
}
