package com.example.springapi.stock.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class StockRequest {

    private String corpCode;
    private String warehouseCode;
    private String itemCode;
    private BigDecimal quantity;
}
