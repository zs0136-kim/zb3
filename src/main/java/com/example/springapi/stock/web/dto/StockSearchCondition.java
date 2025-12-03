package com.example.springapi.stock.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "在庫検索条件DTO") // 在庫一覧検索に使用する条件DTO
public class StockSearchCondition {

    @Schema(description = "会社コード", example = "1000")
    private String corpCode;

    @Schema(description = "倉庫コード", example = "W001")
    private String warehouseCode;

    @Schema(description = "商品コード", example = "ITEM-001")
    private String itemCode;
}
