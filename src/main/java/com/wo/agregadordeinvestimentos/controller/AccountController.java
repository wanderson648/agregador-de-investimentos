package com.wo.agregadordeinvestimentos.controller;

import com.wo.agregadordeinvestimentos.controller.dto.AccountStockResponseDto;
import com.wo.agregadordeinvestimentos.controller.dto.AssociateStockDto;
import com.wo.agregadordeinvestimentos.controller.dto.CreateStockDto;
import com.wo.agregadordeinvestimentos.service.AccountService;
import com.wo.agregadordeinvestimentos.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
            @RequestBody AssociateStockDto dto) {

        accountService.associateStock(accountId, dto);

        return ResponseEntity.created(URI.create("/v1/accounts")).build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listStocks(@PathVariable("accountId") String accountId) {

        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }
}
