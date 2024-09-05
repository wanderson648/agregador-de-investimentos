package com.wo.agregadordeinvestimentos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tb_stock")
@NoArgsConstructor
public class Stock implements Serializable {

    @Id
    private String stockId;

    private String description;

    public Stock(String stockId, String description) {
        this.stockId = stockId;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock stock)) return false;
        return Objects.equals(getStockId(), stock.getStockId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStockId());
    }
}
