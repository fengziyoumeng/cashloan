package com.rongdu.cashloan.core.redisson;

import com.rongdu.cashloan.core.common.context.Global;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/***
 * Redis client的辅助工具类
 * 用于连接Redis服务器 创建不同的Redis Server对应的客户端对象
 * @author wangnian
 * 博客地址：http://my.oschina.net/wangnian
 */
public class RedisUtils {

   private static   Logger logger= LoggerFactory.getLogger(RedisUtils.class);

   private static RedisUtils redisUtils;

   private static RedissonClient redissonClient;

   private RedisUtils(){}

   /**
    * 提供单例模式
    * @return
    */
   public static RedisUtils getInstance(){
      if(redisUtils==null) {
         synchronized (RedisUtils.class) {
            if(redisUtils==null){
               redisUtils=new RedisUtils();
            }
         }
      }
      return redisUtils;
   }


   /**
    * 使用config创建Redisson
    * Redisson是用于连接Redis Server的基础类
    * @param config
    * @return
    */
   public RedissonClient getRedisson(Config config){
      if(redissonClient==null){
         synchronized (RedisUtils.class) {
            if(redissonClient==null){
               redissonClient=Redisson.create(config);
            }
         }
      }
      return redissonClient;
   }

   /**
    * 使用ip地址和端口创建Redisson
    * @param ip
    * @param port
    * @return
    */
   public RedissonClient getRedisson(String ip,String port,String passwd){

      if(!ip.startsWith("http://")){
         ip = "http://"+ip;
      }

      logger.info("RedisUtils-getRedisson-传入参数-ip："+ip+"-port:"+port+"-passwd:"+passwd);
      Config config=new Config();

      SingleServerConfig singleServerConfig = config.useSingleServer();

      singleServerConfig.setAddress(ip+":"+port).
              setTimeout(200000).
              setRetryAttempts(10).
              // .setIdleConnectionTimeout(10000)
                      setRetryInterval(2000).
              //.setFailedAttempts(20)
                      setSubscriptionsPerConnection(5).
              setSubscriptionConnectionPoolSize(10).
              setConnectionPoolSize(64).
              setDatabase(0);

      if(StringUtils.isNotBlank(passwd)){
         singleServerConfig.setPassword(passwd);
      }

      if(redissonClient==null){
         synchronized (RedisUtils.class) {
            if(redissonClient==null){
               redissonClient=Redisson.create(config);
            }
         }
      }
      return redissonClient;
   }

   /**
    * 关闭Redisson客户端连接
    * @param redisson
    */
   public void closeRedisson(RedissonClient redisson){
      redisson.shutdown();
   }

   /**
    * 获取字符串对象
    * @param redisson
    * @param objectName
    * @return
    */
   public <T> RBucket<T> getRBucket(RedissonClient redisson, String objectName){
      RBucket<T> bucket=redisson.getBucket(objectName);
      return bucket;
   }

   /**
    * 获取Map对象
    * @param redisson
    * @param objectName
    * @return
    */
   public <K,V> RMap<K, V> getRMap(RedissonClient redisson, String objectName){
      RMap<K, V> map=redisson.getMap(objectName);
      return map;
   }

   /**
    * 获取有序集合
    * @param redisson
    * @param objectName
    * @return
    */
   public <V> RSortedSet<V> getRSortedSet(RedissonClient redisson, String objectName){
      RSortedSet<V> sortedSet=redisson.getSortedSet(objectName);
      return sortedSet;
   }

   /**
    * 获取集合
    * @param redisson
    * @param objectName
    * @return
    */
   public <V> RSet<V> getRSet(RedissonClient redisson, String objectName){
      RSet<V> rSet=redisson.getSet(objectName);
      return rSet;
   }

   /**
    * 获取列表
    * @param redisson
    * @param objectName
    * @return
    */
   public <V> RList<V> getRList(RedissonClient redisson,String objectName){
      RList<V> rList=redisson.getList(objectName);
      return rList;
   }

   /**
    * 获取队列
    * @param redisson
    * @param objectName
    * @return
    */
   public <V> RQueue<V> getRQueue(RedissonClient redisson,String objectName){
      RQueue<V> rQueue=redisson.getQueue(objectName);
      return rQueue;
   }

   /**
    * 获取双端队列
    * @param redisson
    * @param objectName
    * @return
    */
   public <V> RDeque<V> getRDeque(RedissonClient redisson,String objectName){
      RDeque<V> rDeque=redisson.getDeque(objectName);
      return rDeque;
   }

   /**
    * 此方法不可用在Redisson 1.2 中
    * 在1.2.2版本中 可用
    * @param redisson
    * @param objectName
    * @return
    */
   /**
    public <V> RBlockingQueue<V> getRBlockingQueue(RedissonClient redisson,String objectName){
    RBlockingQueue rb=redisson.getBlockingQueue(objectName);
    return rb;
    }*/

   /**
    * 获取锁
    * @param redisson
    * @param objectName
    * @return
    */
   public RLock getRLock(RedissonClient redisson,String objectName){
      RLock rLock=redisson.getLock(objectName);
      return rLock;
   }

   /**
    * 获取原子数
    * @param redisson
    * @param objectName
    * @return
    */
   public RAtomicLong getRAtomicLong(RedissonClient redisson,String objectName){
      RAtomicLong rAtomicLong=redisson.getAtomicLong(objectName);
      return rAtomicLong;
   }

   /**
    * 获取记数锁
    * @param redisson
    * @param objectName
    * @return
    */
   public RCountDownLatch getRCountDownLatch(RedissonClient redisson,String objectName){
      RCountDownLatch rCountDownLatch=redisson.getCountDownLatch(objectName);
      return rCountDownLatch;
   }

   /**
    * 获取消息的Topic
    * @param redisson
    * @param objectName
    * @return
    */
   public <M> RTopic<M> getRTopic(RedissonClient redisson,String objectName){
      RTopic<M> rTopic=redisson.getTopic(objectName);
      return rTopic;
   }


}