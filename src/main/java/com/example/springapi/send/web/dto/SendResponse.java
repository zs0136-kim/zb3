package com.example.springapi.send.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "出庫レスポンスDTO") // 出庫情報をフロントに返却するためのDTO
public class SendResponse {

    @Schema(description = "会社コード")
    private final String corpCode;

    @Schema(description = "処理種別コード")
    private final String processClassCode;

    @Schema(description = "出庫番号")
    private final String sendNo;

    @Schema(description = "訂正回数")
    private final Integer revisionNo;

    @Schema(description = "赤黒区分")
    private final String rbType;

    @Schema(description = "販売先コード")
    private final String customerCode;

    @Schema(description = "営業担当部署コード")
    private final String salesDeptCode;

    @Schema(description = "営業担当者コード")
    private final String salesStaffCode;

    @Schema(description = "出庫日")
    private final LocalDate sendDate;

    @Schema(description = "印字出庫番号")
    private final String printSendNo;

    @Schema(description = "入金予定日")
    private final LocalDate collectScheduleDate;

    @Schema(description = "税施行日")
    private final LocalDate taxEffectDate;

    @Schema(description = "確定区分")
    private final String fixType;

    @Schema(description = "取引分類コード")
    private final String tradeClassifyCode;

    @Schema(description = "通貨コード")
    private final String currencyCode;

    @Schema(description = "通貨レート")
    private final BigDecimal currencyRate;

    @Schema(description = "通貨レートID")
    private final Long currencyRateId;

    @Schema(description = "商品建値コード")
    private final String incotermCodeItem;

    @Schema(description = "合計建値コード")
    private final String incotermCodeTotal;

    @Schema(description = "売上計上日")
    private final LocalDate sellRecordDate;

    @Schema(description = "締日コード")
    private final String cutoffDateCode;

    @Schema(description = "支払回収日コード（月）")
    private final String payCollectDateCodeMonth;

    @Schema(description = "支払回収日コード（日）")
    private final String payCollectDateCodeDate;

    @Schema(description = "支払回収方法コード")
    private final String payCollectMethodCode;

    @Schema(description = "手形期間")
    private final Integer billPeriod;

    @Schema(description = "起算条件コード")
    private final String reckonConditionCode;

    @Schema(description = "収納備考")
    private final String collectNote;

    @Schema(description = "納品書備考")
    private final String deliveryReportNote;

    @Schema(description = "社内メモ")
    private final String insideNote;

    @Schema(description = "出庫元倉庫コード")
    private final String sendFromWarehouseCode;

    @Schema(description = "小割作業日")
    private final LocalDate divideOperationDate;

    @Schema(description = "小割後出庫先倉庫コード")
    private final String divideAfterWarehouseCode;

    @Schema(description = "倉庫コード")
    private final String warehouseCode;

    @Schema(description = "納入先コード")
    private final String deliveryCode;

    @Schema(description = "納入先追記")
    private final String deliveryNote;

    @Schema(description = "運送会社コード")
    private final String carrierCode;

    @Schema(description = "運送会社への指示")
    private final String carrierOrderNote;

    @Schema(description = "荷渡指図書発行日")
    private final LocalDate sendDeliveryOrderReportDate;

    @Schema(description = "荷渡指図書番号")
    private final String sendDeliveryOrderReportNumber;

    @Schema(description = "荷渡指図書指示内容")
    private final String sendDeliveryOrderReportInstructions;

    @Schema(description = "引用元出庫番号")
    private final String sourceSendNo;

    @Schema(description = "船名")
    private final String shipName;

    @Schema(description = "航路番号")
    private final String voyageNo;

    @Schema(description = "船出港日")
    private final LocalDate shipLeaveDate;
    private final LocalDate shipArriveDate;

    @Schema(description = "請求書作成日")
    private final LocalDate invoiceCreateDate;

    @Schema(description = "荷渡指図書作成日")
    private final LocalDate blCreateDate;

    @Schema(description = "積出国コード")
    private final String loadCountryCode;

    @Schema(description = "仕向国コード")
    private final String deliveryCountryCode;

    @Schema(description = "出荷地コード")
    private final String shipPortCode;

    @Schema(description = "積地コード")
    private final String loadPortCode;

    @Schema(description = "揚地コード")
    private final String unloadPortCode;

    @Schema(description = "経由地コード")
    private final String viaPortCode;

    @Schema(description = "最終仕向地コード")
    private final String finalDestinationPortCode;

    @Schema(description = "荷主コード")
    private final String consignorCode;

    @Schema(description = "荷主備考")
    private final String consignorNote;
    private final String blReceiverCode;

    @Schema(description = "B/L上荷受人備考")
    private final String blReceiverNote;

    @Schema(description = "着荷通知先1")
    private final String arrivalContact1;

    @Schema(description = "着荷通知先2")
    private final String arrivalContact2;

    @Schema(description = "着荷通知先3")
    private final String arrivalContact3;

    @Schema(description = "船会社")
    private final String shippingCompany;

    @Schema(description = "乙仲")
    private final String shippingBroker;

    @Schema(description = "海外運送会社コード")
    private final String overseasCarrierCode;

    @Schema(description = "船腹予約番号")
    private final String bookingNo;

    @Schema(description = "B/L発行場所")
    private final String blCreatePlace;

    @Schema(description = "B/Lタイプ区分")
    private final String blType;

    @Schema(description = "B/Lタイプその他")
    private final String blTypeOther;

    @Schema(description = "B/L発行部数")
    private final String blPublishQuantity;

    @Schema(description = "サービスタイプ")
    private final String serviceType;
    private final String passPlaceCode;

    @Schema(description = "受渡日")
    private final LocalDate passDate;

    @Schema(description = "カット日")
    private final LocalDate cutDate;

    @Schema(description = "連絡事項")
    private final String informNote;

    @Schema(description = "運賃支払方法コード")
    private final String freightPayMethodCode;

    @Schema(description = "提出書類フラグ（INVOICE）")
    private final String sendReportFlagInvoice;

    @Schema(description = "提出書類フラグ（PACKING LIST）")
    private final String sendReportFlagPackingList;

    @Schema(description = "提出書類フラグ（MILL SHEET）")
    private final String sendReportFlagMillSheet;

    @Schema(description = "提出書類フラグ（OTHERS）")
    private final String sendReportFlagOthers;

    @Schema(description = "提出書類（OTHERS）")
    private final String sendReportOthers;

    @Schema(description = "B/L指示")
    private final String blOrderNote;

    @Schema(description = "会社口座ID")
    private final Long corpBankId;

    @Schema(description = "船積書類フラグ（INVOICE）")
    private final String shippingAdviceFlagInvoice;

    @Schema(description = "船積書類フラグ（PACKING LIST）")
    private final String shippingAdviceFlagPackingList;

    @Schema(description = "船積書類フラグ（BL）")
    private final String shippingAdviceFlagBl;

    @Schema(description = "船積書類フラグ（INSURANCE POLICY）")
    private final String shippingAdviceFlagInsurancePolicy;

    @Schema(description = "船積書類フラグ（COUNTRY OF ORIGIN）")
    private final String shippingAdviceCountryOfOrigin;

    @Schema(description = "船積書類フラグ（MILL SHEET）")   
    private final String shippingAdviceFlagMillSheet;

    @Schema(description = "船積書類フラグ（CERTIFICATE OF ANALYSIS）")
    private final String shippingAdviceFlagCertificateOfAnalysis;

    @Schema(description = "船積書類フラグ（TEST CERTIFICATION）")
    private final String shippingAdviceFlagTestCertification;

    @Schema(description = "船積書類フラグ（COIL LIST）")
    private final String shippingAdviceFlagCoilList;

    @Schema(description = "船積書類フラグ（OTHERS）")
    private final String shippingAdviceFlagOthers;

    @Schema(description = "船積書類（OTHERS）")
    private final String shippingAdviceOthers;

    @Schema(description = "販売先連絡事項")
    private final String customerInformNote;

    @Schema(description = "M3帳票出力フラグ")
    private final String m3ReportOutputFlag;

    @Schema(description = "加工発注処理種別コード")
    private final String machiningOrderProcessClassCode;

    @Schema(description = "加工発注番号")
    private final String machiningOrderNo;

    @Schema(description = "販売金額")
    private final BigDecimal sellAmount;

    @Schema(description = "販売消費税金額")
    private final BigDecimal sellTaxAmount;

    @Schema(description = "基準通貨販売金額")
    private final BigDecimal baseSellAmount;

    @Schema(description = "基準通貨販売消費税金額")
    private final BigDecimal baseSellTaxAmount;

    @Schema(description = "小割梱包数量")
    private final BigDecimal dividePackingQuantity;

    @Schema(description = "小割合計入庫数量")
    private final BigDecimal divideSumReceiveQuantity;

    @Schema(description = "押印社員コード1")
    private final String sealUserCode1;

    @Schema(description = "押印社員コード2")
    private final String sealUserCode2;

    @Schema(description = "押印社員コード3")
    private final String sealUserCode3;

    @Schema(description = "正方形押印フラグ")
    private final String squareSealFlag;

    @Schema(description = "最新荷渡指図書出力日時")
    private final LocalDateTime latestDeliveryReportOutputDatetime;

    @Schema(description = "署名社員コード")
    private final String signatureUserCode;

    @Schema(description = "署名タイトルコード")
    private final String signatureTitleCode;
}
