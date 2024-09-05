package com.wo.agregadordeinvestimentos.service;

import com.wo.agregadordeinvestimentos.controller.dto.CreateStockDto;
import com.wo.agregadordeinvestimentos.entity.Stock;
import com.wo.agregadordeinvestimentos.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    public final StockRepository stockRepository;

    public void createStock(CreateStockDto createStockDto) {

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
