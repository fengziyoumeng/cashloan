package com.rongdu.cashloan.core.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

public class SingleKeyByIdDbAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {

    @Override
    public String doEqualSharding(final Collection<String> collection, ShardingValue<Long> shardingValue) {
        long id = Long.parseLong( shardingValue.getValue()+"")  ;

        long index = id % 2;

        for (String each : collection) {
            if (each.endsWith(index + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doInSharding(final Collection<String> collection, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        Collection<Long> values = shardingValue.getValues();
        for (Long value : values) {
            for (String each : collection) {
                if (each.endsWith(value % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(final Collection<String> collection, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long value = range.lowerEndpoint(); value <= range.upperEndpoint(); value++) {
            for (String each : collection) {
                if (each.endsWith(value % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}