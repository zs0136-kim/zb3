package com.example.springapi.stock.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 在庫の新規登録・更新に使用する入力用DTO。
 */
@Getter
@Setter
@Schema(description = "在庫登録・更新リクエストDTO") // 在庫の新規登録・更新に使用する入力用DTO
public class StockRequest {

    @Schema(description = "会社コード", example = "1000")
    private String corpCode;

    @Schema(description = "倉庫コード", example = "W001")
    private String warehouseCode;

    @Schema(description = "商品コード", example = "ITEM-001")
    private String itemCode;

    @Schema(description = "在庫数量", example = "100.0")
    private BigDecimal quantity;
}
