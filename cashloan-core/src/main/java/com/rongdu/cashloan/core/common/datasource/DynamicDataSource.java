package com.rongdu.cashloan.core.common.datasource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = Logger.getLogger(DynamicDataSource.class);


    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("获得的数据源为========>"+ DatabaseContextHolder.getCustomerType());
        return DatabaseContextHolder.getCustomerType();
    }
}


