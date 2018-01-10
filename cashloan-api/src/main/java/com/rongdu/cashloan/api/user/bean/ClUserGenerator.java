package com.rongdu.cashloan.api.user.bean;

import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import com.rongdu.cashloan.core.constant.IdConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ClUserGenerator implements KeyGenerator,ApplicationContextAware {


    @Resource
    private ShardedJedisClient redisClient;

    private ApplicationContext applicationContext;


    @Override
    public Number generateKey() {
        ShardedJedisClient redisClient2=  (ShardedJedisClient)applicationContext.getBean("redisClient");
        return redisClient.incr(IdConstant.ID_CL_USER);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
