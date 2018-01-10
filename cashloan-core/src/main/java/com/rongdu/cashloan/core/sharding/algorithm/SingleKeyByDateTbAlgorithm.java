package com.rongdu.cashloan.core.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import com.rongdu.cashloan.core.sharding.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;


public class SingleKeyByDateTbAlgorithm implements SingleKeyTableShardingAlgorithm<Date> {

    private Logger logger = LoggerFactory.getLogger(SingleKeyByDateTbAlgorithm.class);


    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
       Date createDate = shardingValue.getValue();
       //logger.info("日期分表获取到的日期为=========>"+createDate.toString());
       String formatDate = DateUtil.getDateMonthString(createDate);
       //logger.info("日期分表格式化后的结果为========>"+formatDate);

        String dbFalg = "0";
        //测试用
        if("201707".equals(formatDate)||"201708".equals(formatDate)||
                "201709".equals(formatDate)||"201710".equals(formatDate)){
            dbFalg = "0";
        }else{
            dbFalg = "1";
        }
        for (String each : collection) {
            if (each.endsWith(dbFalg)) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
        return null;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>();
        Range range = shardingValue.getValueRange();
        String beginDateStr =  "" + range.lowerEndpoint();
        String endDateStr = "" + range.upperEndpoint();
        getAllDate(result,beginDateStr,endDateStr);
        return result;
    }

    public void getAllDate(Collection<String> result,String beginDateStr,String endDateStr){
        Date beginDate = DateUtil.stringToDate(beginDateStr,"YYYYMMdd");
        Date endDate = DateUtil.stringToDate(endDateStr,"YYYYMMdd");
        //调用daysBetweenDates函数计算两个日期之间的天数时，第一个参数是较大的日期
        int days = DateUtil.daysBetweenDates(endDate,beginDate);
        for (int i = 0 ; i <= days ; i++){
            String itemDateStr = DateUtil.dateToString(DateUtil.getDateBetween(beginDate,i),"YYYYMMdd");
            result.add("user_action_log_" + itemDateStr);
        }
    }
}