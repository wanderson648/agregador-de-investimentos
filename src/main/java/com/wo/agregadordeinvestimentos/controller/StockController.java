package com.wo.agregadordeinvestimentos.controller;

import com.wo.agregadordeinvestimentos.controller.dto.CreateStockDto;
import com.wo.agregadordeinvestimentos.controller.dto.CreateUserDto;
import com.wo.agregadordeinvestimentos.entity.User;
import com.wo.agregadordeinvestimentos.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto) {
        stockService.createStock(createStockDto);

        return ResponseEntity.created(URI.create("v1/stocks")).build();
    }
}
