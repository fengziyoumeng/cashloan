package com.rongdu.cashloan.core.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

@Component
public class SingleKeyByIdTbAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {

    @Override
    public String doEqualSharding(final Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
//        long cloumnValue = Long.parseLong( shardingValue.getValue()+"");
//        int tableSize = 3;
//        if(availableTargetNames!=null){
//            tableSize = availableTargetNames.size();
//        }
//        long index = cloumnValue%tableSize;
//        for (String each : availableTargetNames) {
//            if (each.endsWith("_"+index)) {
//                return each;
//            }
//        }

        int tableSize = 3;
        if(availableTargetNames!=null){
            tableSize = availableTargetNames.size();
        }
        for (String each : availableTargetNames) {
            if (each.endsWith("_"+(Long.parseLong( shardingValue.getValue()+"" )   % tableSize))) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doInSharding(final Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {

        int tableSize = 3;
        if(availableTargetNames!=null){
            tableSize = availableTargetNames.size();
        }
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Collection<Long> values = shardingValue.getValues();
        for (Long value : values) {
            for (String each : availableTargetNames) {
                if (each.endsWith("_"+(value % tableSize) )) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        int tableSize = 3;
        if(availableTargetNames!=null){
            tableSize = availableTargetNames.size();
        }
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith("_" + (i % tableSize))) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
