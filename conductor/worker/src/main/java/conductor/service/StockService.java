package conductor.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class StockService {
    public long getRestockEta(String orderId) {
        if (orderId.endsWith("x")) {
            return Instant.now().getEpochSecond();
        } else {
            return Instant.now().plus(15, ChronoUnit.DAYS).getEpochSecond();
        }
    }

    public String packageAndSendOrder(String orderId, String address) {
        return UUID.randomUUID().toString();
    }
}
