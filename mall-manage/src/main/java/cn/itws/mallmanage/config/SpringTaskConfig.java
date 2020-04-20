package cn.itws.mallmanage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务配置
 * 一、应用场景
 *   系统设置了60分钟用户不付款就会取消订单；
 * 二、配置springTask
 *  1、由于SpringTask已经存在于Spring框架中，所以无需添加依赖。
 *  2、只需要在配置类中添加一个@EnableScheduling注解即可开启SpringTask的定时任务能力。
 * 三、书写组件
 *   cn.itws.mallmanage.config.SpringTaskConfig
 * Created by macro on 2019/4/8.
 */
@Configuration
@EnableScheduling
public class SpringTaskConfig {

}