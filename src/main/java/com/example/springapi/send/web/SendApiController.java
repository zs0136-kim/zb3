package com.example.springapi.send.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import com.example.springapi.common.api.BaseApiController;
import com.example.springapi.send.application.SendApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import com.example.springapi.common.api.ApiResponse;
import com.example.springapi.send.web.dto.SendSearchCondition;
import com.example.springapi.send.web.dto.SendResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Send", description = "出庫管理API") // 出庫関連エンドポイントのグループ名
@RestController
@RequestMapping("/api/v1/sends")
@RequiredArgsConstructor
public class SendApiController extends BaseApiController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SendApplicationService sendApplicationService;

    @Operation(summary = "出庫情報を検索", description = "出庫情報を検索します。")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SendResponse>>> search(
        @Parameter(description = "出庫検索条件") SendSearchCondition sendSearchCondition
    ) {

        // テスト用：corpCode = 1000, processClassCode = 1000, sendNo = 1000
        // http://localhost:8080/api/v1/sends?corpCode=000001&processClassCode=38&sendNo=25DL000395
        // sendSearchCondition.setCorpCode("000001");
        // sendSearchCondition.setProcessClassCode("38");
        // sendSearchCondition.setSendNo("25DL000395");

        List<SendResponse> list = sendApplicationService.search(sendSearchCondition);
        return ok(list);
    }
}
