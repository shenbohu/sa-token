package com.bohu.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName DatabasePreciseShardingAlgorithm
 * @Author shenbohu
 * @Date 2022/1/25:13 PM
 * @Version 1.0
 **/
@Component
public class DatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long curValue = shardingValue.getValue();
        String curBase = "";
        if (curValue > 0 && curValue<=200) {
            curBase = "ds0";
        } else {
            curBase = "ds1";
        }
        return curBase;
    }
}