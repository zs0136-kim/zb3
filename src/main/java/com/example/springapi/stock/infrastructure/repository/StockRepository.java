package com.example.springapi.stock.infrastructure.repository;

import com.example.springapi.stock.domain.model.Stock;
import com.example.springapi.stock.web.dto.StockSearchCondition;
import java.util.List;
import java.util.Optional;

public interface StockRepository {

    Optional<Stock> findById(Long id);

    List<Stock> findByCondition(StockSearchCondition condition);

    Stock save(Stock stock);

    void delete(Long id);
}
