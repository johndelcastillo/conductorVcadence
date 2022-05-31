package worker.domain;

import java.time.Duration;
import java.time.Instant;

public class StockInfo {
    private Boolean inStock;
    private Long restockEta;
    private Duration waitTime;

    public StockInfo(Long restockEta) {
        this.setInStock(Instant.ofEpochSecond(restockEta).isBefore(Instant.now()));
        this.setRestockEta(restockEta);
        this.waitTime = Duration.between(Instant.now(), Instant.ofEpochSecond(restockEta));
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

    public Duration getWaitTime() {
        return this.waitTime;
    }
}
