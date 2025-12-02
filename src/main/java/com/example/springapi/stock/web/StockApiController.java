package com.example.springapi.stock.web;

import com.example.springapi.common.api.ApiResponse;
import com.example.springapi.common.api.BaseApiController;
import com.example.springapi.stock.application.StockApplicationService;
import com.example.springapi.stock.web.dto.StockRequest;
import com.example.springapi.stock.web.dto.StockResponse;
import com.example.springapi.stock.web.dto.StockSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockApiController extends BaseApiController {

    private final StockApplicationService stockApplicationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockResponse>> get(@PathVariable Long id) {
        StockResponse res = stockApplicationService.get(id);
        return ok(res);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockResponse>>> search(StockSearchCondition condition) {
        List<StockResponse> list = stockApplicationService.search(condition);
        return ok(list);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StockResponse>> create(@RequestBody StockRequest request) {
        StockResponse created = stockApplicationService.create(request);
        return created(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StockResponse>> update(
            @PathVariable Long id,
            @RequestBody StockRequest request
    ) {
        StockResponse updated = stockApplicationService.update(id, request);
        return ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        stockApplicationService.delete(id);
        return noContent();
    }
}
