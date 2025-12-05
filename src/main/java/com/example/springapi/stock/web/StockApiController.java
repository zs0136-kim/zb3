package com.example.springapi.stock.web;

import com.example.springapi.common.api.ApiResponse;
import com.example.springapi.common.api.BaseApiController;
import com.example.springapi.stock.application.StockApplicationService;
import com.example.springapi.stock.web.dto.StockRequest;
import com.example.springapi.stock.web.dto.StockResponse;
import com.example.springapi.stock.web.dto.StockSearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Stock", description = "在庫管理API") // 在庫関連エンドポイントのグループ名
@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockApiController extends BaseApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final StockApplicationService stockApplicationService;

    @Operation(summary = "在庫1件取得", description = "在庫番号を指定して在庫情報を1件取得します。")
    @GetMapping("/{stockNo}")
    public ResponseEntity<ApiResponse<StockResponse>> get(
            @Parameter(description = "在庫番号", required = true) // 在庫キーとなる在庫番号
            @PathVariable(name = "stockNo") String stockNo
    ) {
        // テスト：stockNo = 0000000001
        // http://localhost:8080/api/v1/stocks/0000000001
        StockResponse res = stockApplicationService.get(stockNo);

        log.info("StockResponse: {}", res);
        
        return ok(res);
    }

    @Operation(summary = "在庫検索", description = "会社コード・倉庫コード・商品コードで在庫一覧を検索します。（最大500件まで）")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StockResponse>>> search(StockSearchCondition condition) {
        List<StockResponse> list = stockApplicationService.search(condition);
        return ok(list);
    }

    @Operation(summary = "在庫新規登録", description = "在庫情報を1件新規登録します。")
    @PostMapping
    public ResponseEntity<ApiResponse<StockResponse>> create(@RequestBody StockRequest request) {
        StockResponse created = stockApplicationService.create(request);
        return created(created);
    }

    @Operation(summary = "在庫更新", description = "在庫番号を指定して在庫情報を更新します。")
    @PutMapping("/{stockNo}")
    public ResponseEntity<ApiResponse<StockResponse>> update(
            @Parameter(description = "在庫番号", required = true)
            @PathVariable(name = "stockNo") String stockNo,
            @RequestBody StockRequest request
    ) {
        StockResponse updated = stockApplicationService.update(stockNo, request);
        return ok(updated);
    }

    @Operation(summary = "在庫削除", description = "在庫IDを指定して在庫情報を削除します。")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "在庫ID", required = true)
            @PathVariable(name = "id") Long id
    ) {
        stockApplicationService.delete(id);
        return noContent();
    }
}
