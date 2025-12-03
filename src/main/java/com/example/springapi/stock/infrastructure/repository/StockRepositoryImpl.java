package com.example.springapi.stock.infrastructure.repository;

import com.example.springapi.stock.domain.model.Stock;
import com.example.springapi.stock.infrastructure.mapper.StockMapper;
import com.example.springapi.stock.web.dto.StockSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockMapper mapper;

    @Override
    public Optional<Stock> findById(String stockNo) {
        StockEntity e = mapper.selectById(stockNo);
        return Optional.ofNullable(e).map(this::toDomain);
    }

    @Override
    public List<Stock> findByCondition(StockSearchCondition condition) {
        return mapper.selectByCondition(condition)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity e = toEntity(stock);
        if (e.getStockNo() == null) {
            mapper.insert(e);
            // PK設定方式に応じてここで再取得 or selectKey使用
        } else {
            mapper.update(e);
        }
        return toDomain(e);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }

    private Stock toDomain(StockEntity e) {
        return Stock.builder()
                .stockNo(e.getStockNo())
                .corpCode(e.getCorpCode())
                .warehouseCode(e.getWarehouseCode())
                .itemCode(e.getItemCode())
                // 在庫数量をドメインの数量にマッピング
                .quantity(e.getStockQuantity())
                .build();
    }

    private StockEntity toEntity(Stock d) {
        StockEntity e = new StockEntity();
        e.setStockNo(d.getStockNo());
        e.setCorpCode(d.getCorpCode());
        e.setWarehouseCode(d.getWarehouseCode());
        e.setItemCode(d.getItemCode());
        // ドメインの数量を在庫数量にマッピング
        e.setStockQuantity(d.getQuantity());
        return e;
    }
}
