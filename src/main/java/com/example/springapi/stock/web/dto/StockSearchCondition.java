package com.example.springapi.stock.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockSearchCondition {

    private String corpCode;
    private String warehouseCode;
    private String itemCode;
}
