package com.example.springapi.stock.web.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
public class StockResponse {

    private String stockNo;
    private String corpCode;
    private String warehouseCode;
    private String itemCode;
    private BigDecimal quantity;
}

