package com.example.springapi.stock.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@Schema(description = "在庫レスポンスDTO") // 在庫情報をフロントに返却するためのDTO
public class StockResponse {

    @Schema(description = "在庫番号")
    private String stockNo;

    @Schema(description = "会社コード")
    private String corpCode;

    @Schema(description = "倉庫コード")
    private String warehouseCode;

    @Schema(description = "商品コード")
    private String itemCode;

    @Schema(description = "在庫数量")
    private BigDecimal quantity;
}

