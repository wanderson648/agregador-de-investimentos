package com.wo.agregadordeinvestimentos.service;

import com.wo.agregadordeinvestimentos.client.BrapiClient;
import com.wo.agregadordeinvestimentos.controller.dto.AccountStockResponseDto;
import com.wo.agregadordeinvestimentos.controller.dto.AssociateStockDto;
import com.wo.agregadordeinvestimentos.controller.dto.CreateStockDto;
import com.wo.agregadordeinvestimentos.entity.AccountStock;
import com.wo.agregadordeinvestimentos.entity.AccountStockId;
import com.wo.agregadordeinvestimentos.entity.Stock;
import com.wo.agregadordeinvestimentos.repository.AccountRepository;
import com.wo.agregadordeinvestimentos.repository.AccountStockRepository;
import com.wo.agregadordeinvestimentos.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    public final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public void associateStock(String accountId, AssociateStockDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );

        accountStockRepository.save(entity);

    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks().stream()
                .map(as -> new AccountStockResponseDto(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotal(as.getQuantity(), as.getStock().getStockId())
                )).toList();
    }

    private double getTotal(Integer quantity, String stockId) {

        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().get(0).regularMarketPrice();

        return quantity * price;

    }
}
