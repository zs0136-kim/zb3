package com.example.springapi.stock.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class StockEntity {

    private Long id;
    private String corpCode;
    private String warehouseCode;
    private String itemCode;
    private BigDecimal quantity;
}
