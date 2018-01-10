package com.rongdu.cashloan.core.sharding.id;

import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redisson.RedisUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@Component
public class ClUserIdGenerator implements KeyGenerator {

    private Logger logger = LoggerFactory.getLogger(ClUserIdGenerator.class);

    @Override
    public Number generateKey() {

      ResourceBundle resource = ResourceBundle.getBundle("config/redis");

       String redisIp = resource.getString("redis-ip");
       String redisPort = resource.getString("redis-port");
       String redisPasswd = resource.getString("redis-passwd");

        final RedissonClient redisson = RedisUtils.getInstance().getRedisson(redisIp,redisPort,redisPasswd);
        final RLock rLock = redisson.getLock("lock");
        long id = 0;
        try {
            RAtomicLong atomicLong = redisson.getAtomicLong(AppConstant.ID_CLUSER);
            rLock.lock(10, TimeUnit.SECONDS);
            id = atomicLong.incrementAndGet();
            logger.info("生成cl_user主键========>"+id);
        }
        finally {
            rLock.unlock();
        }
        return id;
    }
}
