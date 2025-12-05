package com.example.springapi.send.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SendEntity {

    // 会社コード（CORP_CODE）
    private String corpCode;

    // 処理種別コード（PROCESS_CLASS_CODE）
    private String processClassCode;

    // 出庫番号（SEND_NO）
    private String sendNo;

    // 訂正回数（REVISION_NO）
    private Integer revisionNo;

    // 赤黒区分（RB_TYPE）
    private String rbType;

    // 販売先コード（CUSTOMER_CODE）
    private String customerCode;

    // 営業担当部署コード（SALES_DEPT_CODE）
    private String salesDeptCode;

    // 営業担当者コード（SALES_STAFF_CODE）
    private String salesStaffCode;

    // 出庫日（SEND_DATE）
    private LocalDate sendDate;

    // 印字出庫番号（PRINT_SEND_NO）
    private String printSendNo;

    // 入金予定日（COLLECT_SCHEDULE_DATE）
    private LocalDate collectScheduleDate;

    // 税施行日（TAX_EFFECT_DATE）
    private LocalDate taxEffectDate;

    // 確定区分（FIX_TYPE）
    private String fixType;

    // 取引分類コード（TRADE_CLASSIFY_CODE）
    private String tradeClassifyCode;

    // 通貨コード（CURRENCY_CODE）
    private String currencyCode;

    // 通貨レート（CURRENCY_RATE）
    private BigDecimal currencyRate;

    // 通貨レートID（CURRENCY_RATE_ID）
    private Long currencyRateId;

    // 商品建値コード（INCOTERM_CODE_ITEM）
    private String incotermCodeItem;

    // 合計建値コード（INCOTERM_CODE_TOTAL）
    private String incotermCodeTotal;

    // 売上計上日（SELL_RECORD_DATE）
    private LocalDate sellRecordDate;

    // 締日コード（CUTOFF_DATE_CODE）
    private String cutoffDateCode;

    // 支払回収日コード（月）（PAY_COLLECT_DATE_CODE_MONTH）
    private String payCollectDateCodeMonth;

    // 支払回収日コード（日）（PAY_COLLECT_DATE_CODE_DATE）
    private String payCollectDateCodeDate;

    // 支払回収方法コード（PAY_COLLECT_METHOD_CODE）
    private String payCollectMethodCode;

    // 手形期間（BILL_PERIOD）
    private Integer billPeriod;

    // 起算条件コード（RECKON_CONDITION_CODE）
    private String reckonConditionCode;

    // 回収注記（COLLECT_NOTE）
    private String collectNote;

    // 納品書備考（DELIVERY_REPORT_NOTE）
    private String deliveryReportNote;

    // 社内メモ（INSIDE_NOTE）
    private String insideNote;

    // 出庫元倉庫コード（SEND_FROM_WAREHOUSE_CODE）
    private String sendFromWarehouseCode;

    // 小割作業日（DIVIDE_OPERATION_DATE）
    private LocalDate divideOperationDate;

    // 小割後出庫先倉庫コード（DIVIDE_AFTER_WAREHOUSE_CODE）
    private String divideAfterWarehouseCode;

    // 倉庫コード（WAREHOUSE_CODE）
    private String warehouseCode;

    // 納入先コード（DELIVERY_CODE）
    private String deliveryCode;

    // 納入先追記（DELIVERY_NOTE）
    private String deliveryNote;

    // 運送会社コード（CARRIER_CODE）
    private String carrierCode;

    // 運送会社への指示（CARRIER_ORDER_NOTE）
    private String carrierOrderNote;

    // 荷渡指図書発行日（SEND_DELIVERY_ORDER_REPORT_DATE）
    private LocalDate sendDeliveryOrderReportDate;

    // 荷渡指図書番号（SEND_DELIVERY_ORDER_REPORT_NUMBER）
    private String sendDeliveryOrderReportNumber;

    // 荷渡指図書指示内容（SEND_DELIVERY_ORDER_REPORT_INSTRUCTIONS）
    private String sendDeliveryOrderReportInstructions;

    // 引用元出庫番号（SOURCE_SEND_NO）
    private String sourceSendNo;

    // 船名（SHIP_NAME）
    private String shipName;

    // 航海番号（VOYAGE_NO）
    private String voyageNo;

    // 出港日（SHIP_LEAVE_DATE）
    private LocalDate shipLeaveDate;

    // 着港日（SHIP_ARRIVE_DATE）
    private LocalDate shipArriveDate;

    // インボイス作成日（INVOICE_CREATE_DATE）
    private LocalDate invoiceCreateDate;

    // B/L発行日（BL_CREATE_DATE）
    private LocalDate blCreateDate;

    // 積出国コード（LOAD_COUNTRY_CODE）
    private String loadCountryCode;

    // 仕向国コード（DELIVERY_COUNTRY_CODE）
    private String deliveryCountryCode;

    // 出荷地コード（SHIP_PORT_CODE）
    private String shipPortCode;

    // 積地コード（LOAD_PORT_CODE）
    private String loadPortCode;

    // 揚地コード（UNLOAD_PORT_CODE）
    private String unloadPortCode;

    // 経由地コード（VIA_PORT_CODE）
    private String viaPortCode;

    // 最終仕向地コード（FINAL_DESTINATION_PORT_CODE）
    private String finalDestinationPortCode;

    // 荷主コード（CONSIGNOR_CODE）
    private String consignorCode;

    // 荷主備考（CONSIGNOR_NOTE）
    private String consignorNote;

    // B/L上荷受人コード（BL_RECEIVER_CODE）
    private String blReceiverCode;

    // B/L上荷受人備考（BL_RECEIVER_NOTE）
    private String blReceiverNote;

    // 着荷通知先1（ARRIVAL_CONTACT_1）
    private String arrivalContact1;

    // 着荷通知先2（ARRIVAL_CONTACT_2）
    private String arrivalContact2;

    // 着荷通知先3（ARRIVAL_CONTACT_3）
    private String arrivalContact3;

    // 船会社（SHIPPING_COMPANY）
    private String shippingCompany;

    // 乙仲（SHIPPING_BROKER）
    private String shippingBroker;

    // 海外運送会社コード（OVERSES_CARRIER_CODE）
    private String overseasCarrierCode;

    // 船腹予約番号（BOOKING_NO）
    private String bookingNo;

    // B/L発行場所（BL_CREATE_PLACE）
    private String blCreatePlace;

    // B/Lタイプ区分（BL_TYPE）
    private String blType;

    // B/Lタイプその他（BL_TYPE_OTHER）
    private String blTypeOther;

    // B/L発行部数（BL_PUBLISH_QUANTITY）
    private String blPublishQuantity;

    // サービスタイプ（SERVICE_TYPE）
    private String serviceType;

    // 受渡場所コード（PASS_PLACE_CODE）
    private String passPlaceCode;

    // 受渡日（PASS_DATE）
    private LocalDate passDate;

    // カット日（CUT_DATE）
    private LocalDate cutDate;

    // 連絡事項（INFORM_NOTE）
    private String informNote;

    // 運賃支払方法コード（FREIGHT_PAY_METHOD_CODE）
    private String freightPayMethodCode;

    // 提出書類フラグ（INVOICE）（SEND_REPORT_FLAG_INVOICE）
    private String sendReportFlagInvoice;

    // 提出書類フラグ（PACKING LIST）（SEND_REPORT_FLAG_PACKING_LIST）
    private String sendReportFlagPackingList;

    // 提出書類フラグ（MILL SHEET）（SEND_REPORT_FLAG_MILL_SHEET）
    private String sendReportFlagMillSheet;

    // 提出書類フラグ（OTHERS）（SEND_REPORT_FLAG_OTHERS）
    private String sendReportFlagOthers;

    // 提出書類（OTHERS）（SEND_REPORT_OTHERS）
    private String sendReportOthers;

    // B/L指示（BL_ORDER_NOTE）
    private String blOrderNote;

    // 会社口座ID（CORP_BANK_ID）
    private Long corpBankId;

    // 船積書類フラグ（INVOICE）（SHIPPING_ADVICE_FLAG_INVOICE）
    private String shippingAdviceFlagInvoice;

    // 船積書類フラグ（PACKING_LIST）（SHIPPING_ADVICE_FLAG_PACKING_LIST）
    private String shippingAdviceFlagPackingList;

    // 船積書類フラグ（BL）（SHIPPING_ADVICE_FLAG_BL）
    private String shippingAdviceFlagBl;

    // 船積書類フラグ（INSURANCE_POLICY）（SHIPPING_ADVICE_FLAG_INSURANCE_POLICY）
    private String shippingAdviceFlagInsurancePolicy;

    // 船積書類フラグ（COUNTRY_OF_ORIGIN）（SHIPPING_ADVICE_COUNTRY_OF_ORIGIN）
    private String shippingAdviceCountryOfOrigin;

    // 船積書類フラグ（MILL_SHEET）（SHIPPING_ADVICE_FLAG_MILL_SHEET）
    private String shippingAdviceFlagMillSheet;

    // 船積書類フラグ（CERTIFICATE_OF_ANALYSIS）（SHIPPING_ADVICE_FLAG_CERTIFICATE_OF_ANALYSIS）
    private String shippingAdviceFlagCertificateOfAnalysis;

    // 船積書類フラグ（TEST_CERTIFICATION）（SHIPPING_ADVICE_FLAG_TEST_CERTIFICATION）
    private String shippingAdviceFlagTestCertification;

    // 船積書類フラグ（COIL_LIST）（SHIPPING_ADVICE_FLAG_COIL_LIST）
    private String shippingAdviceFlagCoilList;

    // 船積書類フラグ（OTHERS）（SHIPPING_ADVICE_FLAG_OTHERS）
    private String shippingAdviceFlagOthers;

    // 船積書類（OTHERS）（SHIPPING_ADVICE_OTHERS）
    private String shippingAdviceOthers;

    // 販売先連絡事項（CUSTOMER_INFORM_NOTE）
    private String customerInformNote;

    // M3帳票出力フラグ（M3_REPORT_OUTPUT_FLAG）
    private String m3ReportOutputFlag;

    // 加工発注処理種別コード（MACHINING_ORDER_PROCESS_CLASS_CODE）
    private String machiningOrderProcessClassCode;

    // 加工発注番号（MACHINING_ORDER_NO）
    private String machiningOrderNo;

    // 販売金額（SELL_AMOUNT）
    private BigDecimal sellAmount;

    // 販売消費税金額（SELL_TAX_AMOUNT）
    private BigDecimal sellTaxAmount;

    // 基準通貨販売金額（BASE_SELL_AMOUNT）
    private BigDecimal baseSellAmount;

    // 基準通貨販売消費税金額（BASE_SELL_TAX_AMOUNT）
    private BigDecimal baseSellTaxAmount;

    // 小割梱包数量（DIVIDE_PACKING_QUANTITY）
    private BigDecimal dividePackingQuantity;

    // 小割合計入庫数量（DIVIDE_SUM_RECEIVE_QUANTITY）
    private BigDecimal divideSumReceiveQuantity;

    // 押印社員コード1（SEAL_USER_CODE_1）
    private String sealUserCode1;

    // 押印社員コード2（SEAL_USER_CODE_2）
    private String sealUserCode2;

    // 押印社員コード3（SEAL_USER_CODE_3）
    private String sealUserCode3;

    // 角印フラグ（SQUARE_SEAL_FLAG）
    private String squareSealFlag;

    // 納品書最新出力日時（LATEST_DELIVERY_REPORT_OUTPUT_DATETIME）
    private LocalDateTime latestDeliveryReportOutputDatetime;

    // 署名社員コード（SIGNATURE_USER_CODE）
    private String signatureUserCode;

    // 肩書コード（SIGNATURE_TITLE_CODE）
    private String signatureTitleCode;
}

