package com.example.springapi.send.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SendItemSummaryEntity {

    // 会社コード（CORP_CODE）
    private String corpCode;

    // 処理種別コード（PROCESS_CLASS_CODE）
    private String processClassCode;

    // 帳票番号（REPORT_NO）
    private String reportNo;

    // 明細番号（DETAIL_NO）
    private Integer detailNo;

    // 表示順（DISP_ORDER）
    private Integer displayOrder;

    // 受注番号（ACCEPT_ORDER_NO）
    private String acceptOrderNo;

    // 受注商品明細番号（ACCEPT_ORDER_ITEM_DETAIL_NO）
    private Integer acceptOrderItemDetailNo;

    // 前回出庫番号（PREVIOUS_SEND_NO）
    private String previousSendNo;

    // 前回出庫商品サマリ明細番号（PREVIOUS_SEND_ITEM_SUMMARY_DETAIL_NO）
    private Integer previousSendItemSummaryDetailNo;

    // 商品コード（ITEM_CODE）
    private String itemCode;

    // 商品名（ITEM_NAME）
    private String itemName;

    // 商品タイトル（ITEM_TITLE）
    private String itemTitle;

    // 販売数量（SELL_QUANTITY）
    private BigDecimal sellQuantity;

    // 販売単位コード（SELL_UNIT_CODE）
    private String sellUnitCode;

    // 販売単価（SELL_UNIT_AMOUNT）
    private BigDecimal sellUnitAmount;

    // 客先注文番号（CLIENT_ORDER_CODE）
    private String clientOrderCode;

    // 有償フラグ（PROFIT_FLAG）
    private String profitFlag;

    // 荷印番号（SHIPPING_MARK_NO）
    private String shippingMarkNo;

    // 出庫メモ（ALLOCATION_NOTE）
    private String allocationNote;

    // 販売金額（SELL_AMOUNT）
    private BigDecimal sellAmount;

    // 販売追加金額（SELL_ADD_AMOUNT）
    private BigDecimal sellAddAmount;

    // 基準通貨販売金額（BASE_SELL_AMOUNT）
    private BigDecimal baseSellAmount;

    // 基準通貨追加金額（BASE_SELL_ADD_AMOUNT）
    private BigDecimal baseSellAddAmount;

    // 基準通貨諸掛金額（BASE_COST_CHARGE_AMOUNT）
    private BigDecimal baseCostChargeAmount;
}

