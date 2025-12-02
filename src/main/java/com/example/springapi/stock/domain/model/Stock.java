package com.example.springapi.stock.domain.model;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
public class Stock {

    private final Long id;
    private final String corpCode;
    private final String warehouseCode;
    private final String itemCode;
    private final BigDecimal quantity;
}
