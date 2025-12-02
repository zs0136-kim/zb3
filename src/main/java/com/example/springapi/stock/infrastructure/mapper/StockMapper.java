package com.example.springapi.stock.infrastructure.mapper;

import com.example.springapi.stock.infrastructure.repository.StockEntity;
import com.example.springapi.stock.web.dto.StockSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StockMapper {

    StockEntity selectById(Long id);

    List<StockEntity> selectByCondition(StockSearchCondition condition);

    void insert(StockEntity entity);

    void update(StockEntity entity);

    void delete(Long id);
}
