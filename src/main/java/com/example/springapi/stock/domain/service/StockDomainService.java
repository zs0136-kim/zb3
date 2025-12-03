package com.example.springapi.stock.domain.service;

import com.example.springapi.stock.domain.model.Stock;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class StockDomainService {

    public Stock increase(Stock stock, BigDecimal addQty) {
        return Stock.builder()
                .stockNo(stock.getStockNo())
                .corpCode(stock.getCorpCode())
                .warehouseCode(stock.getWarehouseCode())
                .itemCode(stock.getItemCode())
                .quantity(stock.getQuantity().add(addQty))
                .build();
    }
}
