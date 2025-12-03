package com.example.springapi.stock.application;

import com.example.springapi.common.exception.BusinessException;
import com.example.springapi.common.exception.ErrorCode;
import com.example.springapi.stock.domain.model.Stock;
import com.example.springapi.stock.infrastructure.repository.StockRepository;
import com.example.springapi.stock.web.dto.StockRequest;
import com.example.springapi.stock.web.dto.StockResponse;
import com.example.springapi.stock.web.dto.StockSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockApplicationService {

    private final StockRepository stockRepository;

    public StockResponse get(String stockNo) {
        Stock stock = stockRepository.findById(stockNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        return toResponse(stock);
    }

    public List<StockResponse> search(StockSearchCondition condition) {
        return stockRepository.findByCondition(condition)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public StockResponse create(StockRequest request) {
        Stock stock = Stock.builder()
                .corpCode(request.getCorpCode())
                .warehouseCode(request.getWarehouseCode())
                .itemCode(request.getItemCode())
                .quantity(request.getQuantity())
                .build();

        Stock saved = stockRepository.save(stock);
        return toResponse(saved);
    }

    public StockResponse update(String stockNo, StockRequest request) {
        Stock existing = stockRepository.findById(stockNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        Stock updated = Stock.builder()
                .stockNo(existing.getStockNo())
                .corpCode(request.getCorpCode())
                .warehouseCode(request.getWarehouseCode())
                .itemCode(request.getItemCode())
                .quantity(request.getQuantity())
                .build();

        Stock saved = stockRepository.save(updated);
        return toResponse(saved);
    }

    public void delete(Long id) {
        stockRepository.delete(id);
    }

    private StockResponse toResponse(Stock stock) {
        return StockResponse.builder()
                .stockNo(stock.getStockNo())
                .corpCode(stock.getCorpCode())
                .warehouseCode(stock.getWarehouseCode())
                .itemCode(stock.getItemCode())
                .quantity(stock.getQuantity())
                .build();
    }
}
