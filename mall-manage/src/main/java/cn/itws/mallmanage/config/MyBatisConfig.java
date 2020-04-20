package cn.itws.mallmanage.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan({"cn.itws.mallmanage.mbg.mapper","cn.itws.mallmanage.dao"})
public class MyBatisConfig {

}