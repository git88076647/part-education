package com.czyl.project.mapper;

import java.util.List;

import com.czyl.project.domain.OrderEntity;

public interface ShardingInsertBatchMapper {
	
    int insertBatch(List<OrderEntity> list);
	
}
