package cn.itws.mallmanage.service.impl;

import cn.itws.mallmanage.common.api.CommonResult;
import cn.itws.mallmanage.component.CancelOrderSender;
import cn.itws.mallmanage.dto.OrderParam;
import cn.itws.mallmanage.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author swj
 * @date 2020/4/5 20:07
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    //注入消息发送者对象
    @Autowired
    private CancelOrderSender cancelOrderSender;
    //获取订单超时时间
    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，假设为60分钟(测试为30s)
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
    //1、根据提交信息生成订单
    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //下单伪代码
        //todo 执行一系类下单操作，具体参考mall项目
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null,"下单成功");
    }
    //2、取消单个超时订单
    @Override
    public void cancelOrder(Long orderId) {
        //伪代码
        //todo 执行一系类取消订单操作，具体参考mall项目
        LOGGER.info("process cancelOrder orderId:{}",orderId);
    }
}
