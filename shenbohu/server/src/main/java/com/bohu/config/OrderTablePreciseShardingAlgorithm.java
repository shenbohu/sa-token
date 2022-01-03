package com.bohu.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName OrderTablePreciseShardingAlgorithm
 * @Author shenbohu
 * @Date 2022/1/25:22 PM
 * @Version 1.0
 **/
@Component
public class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long curValue = shardingValue.getValue();
        String curTable = "";
        if (curValue > 0 && curValue<=100) {
            curTable = "t_user0";
        } else if (curValue > 100 && curValue<=200) {
            curTable = "t_user1";
        } else if (curValue > 200 && curValue<=300) {
            curTable = "t_user2";
        } else {
            curTable = "t_user3";
        }
        return curTable;
    }
}