package com.example.springapi.stock.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StockEntity {
    // 会社コード（CORP_CODE）
    private String corpCode;

    // 在庫番号（STOCK_NO）
    private String stockNo;

    // 在庫履歴番号（STOCK_HISTORY_NO）
    private Integer stockHistoryNo;

    // 最新フラグ（LATEST_FLAG）
    private String latestFlag;

    // 表示フラグ（DISPLAY_FLAG）
    private String displayFlag;

    // 在庫処理種別コード（STOCK_PROCESS_CLASS_CODE）
    private String stockProcessClassCode;

    // 在庫処理帳票番号（STOCK_PROCESS_REPORT_NO）
    private String stockProcessReportNo;

    // 在庫処理日時（STOCK_PROCESS_DATETIME）
    private LocalDateTime stockProcessDatetime;

    // 在庫処理者コード（STOCK_PROCESS_USER_CODE）
    private String stockProcessUserCode;

    // 在庫ステータスコード（STOCK_STATUS_CODE）
    private String stockStatusCode;

    // 在庫状態コード（STOCK_CONDITION_CODE）
    private String stockConditionCode;

    // 資産区分コード（ASSET_TYPE_CODE）
    private String assetTypeCode;

    // 発注処理種別コード（ORDER_PROCESS_CLASS_CODE）
    private String orderProcessClassCode;

    // 発注番号（ORDER_NO）
    private String orderNo;

    // 発注商品明細番号（ORDER_ITEM_DETAIL_NO）
    private Integer orderItemDetailNo;

    // 生産完了日（PRODUCT_COMPLETE_DATE）
    private LocalDate productCompleteDate;

    // 船積日（SHIPPING_DATE）
    private LocalDate shippingDate;

    // 入庫処理種別コード（RECEIVE_PROCESS_CLASS_CODE）
    private String receiveProcessClassCode;

    // 入庫番号（RECEIVE_NO）
    private String receiveNo;

    // 入庫商品サマリ明細番号（RECEIVE_ITEM_SUMMARY_DETAIL_NO）
    private Integer receiveItemSummaryDetailNo;

    // 受注番号（ACCEPT_ORDER_NO）
    private String acceptOrderNo;

    // 受注商品明細番号（ACCEPT_ORDER_ITEM_DETAIL_NO）
    private Integer acceptOrderItemDetailNo;

    // 出庫処理種別コード（SEND_PROCESS_CLASS_CODE）
    private String sendProcessClassCode;

    // 出庫番号（SEND_NO）
    private String sendNo;

    // 出庫商品サマリ明細番号（SEND_ITEM_SUMMARY_DETAIL_NO）
    private Integer sendItemSummaryDetailNo;

    // 加工発注処理種別コード（MACHINING_ORDER_PROCESS_CLASS_CODE）
    private String machiningOrderProcessClassCode;

    // 加工発注番号（MACHINING_ORDER_NO）
    private String machiningOrderNo;

    // 加工発注商品明細番号（MACHINING_ORDER_ITEM_DETAIL_NO）
    private Integer machiningOrderItemDetailNo;

    // 初回発注番号（FIRST_ORDER_NO）
    private String firstOrderNo;

    // 初回入庫番号（FIRST_RECEIVE_NO）
    private String firstReceiveNo;

    // 発生元在庫番号（ORI_STOCK_NO）
    private String originalStockNo;

    // 発生元履歴番号（ORI_STOCK_HISTORY_NO）
    private Integer originalStockHistoryNo;

    // 商品コード（ITEM_CODE）
    private String itemCode;

    // 商品コード（メーカー）（ITEM_CODE_MAKER）
    private String itemCodeMaker;

    // ロット番号（LOT_NO）
    private String lotNo;

    // コイル番号（COIL_NO）
    private String coilNo;

    // 倉庫コード（WAREHOUSE_CODE）
    private String warehouseCode;

    // 納入先コード（DELIVERY_CODE）
    private String deliveryCode;

    // 梱包番号（PACKING_NO）
    private String packingNo;

    // 梱包数量（PACKING_QUANTITY）
    private BigDecimal packingQuantity;

    // 荷姿単位コード（PACKING_UNIT_CODE）
    private String packingUnitCode;

    // 按分計算基準金額（DIVIDE_CALC_BASE_AMOUNT）
    private BigDecimal divideCalcBaseAmount;

    // 在庫メモ（STOCK_NOTE）
    private String stockNote;

    // 在庫数量（STOCK_QUANTITY）
    private BigDecimal stockQuantity;

    // 在庫単位コード（STOCK_UNIT_CODE）
    private String stockUnitCode;

    // 仕入単価（BUY_UNIT_AMOUNT）
    private BigDecimal buyUnitAmount;

    // 仕入数量（BUY_QUANTITY）
    private BigDecimal buyQuantity;

    // 仕入単位コード（BUY_UNIT_CODE）
    private String buyUnitCode;

    // 販売単価（SELL_UNIT_AMOUNT）
    private BigDecimal sellUnitAmount;

    // 販売数量（SELL_QUANTITY）
    private BigDecimal sellQuantity;

    // 販売単位コード（SELL_UNIT_CODE）
    private String sellUnitCode;

    // 有償フラグ（PROFIT_FLAG）
    private String profitFlag;

    // 更新中フラグ（UPDATING_FLAG）
    private String updatingFlag;

    // 移行_入庫日（IKO_IN_DT）
    private LocalDate migrationReceiveDate;

    // 移行_仕入先コード（IKO_B_CD）
    private String migrationSupplierCode;

    // 登録日時（CREATE_DATETIME）
    private LocalDateTime createDatetime;

    // 登録者コード（CREATE_USER_CODE）
    private String createUserCode;

    // 更新日時（UPDATE_DATETIME）
    private LocalDateTime updateDatetime;

    // 更新者コード（UPDATE_USER_CODE）
    private String updateUserCode;
}