package com.example.springapi.send.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "出庫検索条件DTO") // 出庫一覧検索に使用する条件DTO
public class SendSearchCondition {
    
    @Schema(description = "会社コード", example = "1000")
    private String corpCode;

    @Schema(description = "処理種別コード", example = "1000")
    private String processClassCode;

    @Schema(description = "出庫番号", example = "1000")
    private String sendNo;
}
