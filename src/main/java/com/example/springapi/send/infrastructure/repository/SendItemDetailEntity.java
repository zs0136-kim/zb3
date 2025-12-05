package com.example.springapi.send.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SendItemDetailEntity {

    // 会社コード（CORP_CODE）
    private String corpCode;

    // 処理種別コード（PROCESS_CLASS_CODE）
    private String processClassCode;

    // 帳票番号（REPORT_NO）
    private String reportNo;

    // 明細番号（DETAIL_NO）
    private Integer detailNo;

    // 在庫番号（STOCK_NO）
    private String stockNo;

    // 在庫履歴番号（STOCK_HISTORY_NO）
    private Integer stockHistoryNo;

    // 最新在庫番号（LATEST_STOCK_NO）
    private String latestStockNo;

    // 最新在庫履歴番号（LATEST_STOCK_HISTORY_NO）
    private Integer latestStockHistoryNo;

    // 販売金額（SELL_AMOUNT）
    private BigDecimal sellAmount;

    // 販売追加金額（SELL_ADD_AMOUNT）
    private BigDecimal sellAddAmount;
}

