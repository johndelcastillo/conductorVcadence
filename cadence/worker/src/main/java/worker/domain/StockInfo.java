package worker.domain;

import java.time.Instant;

public class StockInfo {
    private Boolean inStock;
    private Long restockEta;

    public StockInfo(Long restockEta) {
        this.setInStock(Instant.ofEpochSecond(restockEta).isBefore(Instant.now()));
        this.setRestockEta(restockEta);
    }

    public Boolean isInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Long getRestockEta() {
        return restockEta;
    }

    public void setRestockEta(Long restockEta) {
        this.restockEta = restockEta;
    }
}
