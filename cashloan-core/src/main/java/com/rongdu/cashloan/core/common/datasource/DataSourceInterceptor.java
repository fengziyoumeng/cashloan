package com.rongdu.cashloan.core.common.datasource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInterceptor {

    private static final Logger logger = Logger.getLogger(DataSourceInterceptor.class);

    public void setdataSourceOne(JoinPoint jp) {
        if( !"dataSourceRDB".equals(  DatabaseContextHolder.getCustomerType() ))
        {
            //if(logger.isDebugEnabled()){
                logger.info("==========>设置为dataSourceRDB数据源,"+jp.getSignature().toString());
                logger.info("========>设置为dataSourceRDB数据源,原先数据源名称为："+ DatabaseContextHolder.getCustomerType());
           // }
            DatabaseContextHolder.setCustomerType("dataSourceRDB");
        }

    }

    public void setdataSourceTwo(JoinPoint jp) {
        if( !"dataSourceNoSharding".equals(  DatabaseContextHolder.getCustomerType() )){
           // if(logger.isDebugEnabled()){
            logger.info("==========>设置为dataSourceNoSharding数据源,"+jp.getSignature().toString());
                logger.info("========>设置为dataSourceNoSharding数据源,原先数据源名称为："+ DatabaseContextHolder.getCustomerType());
            //}
            DatabaseContextHolder.setCustomerType("dataSourceNoSharding");
        }
    }
}
