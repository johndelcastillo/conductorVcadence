package worker;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import worker.domain.StockInfo;
import worker.service.NotificationService;
import worker.service.StockService;

public class OrdersWorker implements OrdersActivities {

    Logger logger = LoggerFactory.getLogger(OrdersWorker.class);

    private StockService stockService;
    private NotificationService notificationService;

    public OrdersWorker(StockService stockService, NotificationService notificationService) {
        this.stockService = stockService;
        this.notificationService = notificationService;
    }

    @Override
    public StockInfo checkStock(String orderId) {
        logger.info("Checking stock level for: {}", orderId);

        long stockEta = this.stockService.getRestockEta(orderId);
        return new StockInfo(stockEta);

    }

    @Override
    public void notifyDelay(String emailAddress, Long eta) {
        logger.info("Sending delay notification to: {}", emailAddress);

        String message = String.format("Stock is backordered, estimated eta: %s",
                Instant.ofEpochSecond(eta).toString());
        notificationService.Notify(emailAddress, message);
    }

    @Override
    public String packageAndSendOrder(String orderId, String address) {
        logger.info("Packaging order: {} and sending to: {}", orderId, address);
        return stockService.packageAndSendOrder(orderId, address);
    }

    @Override
    public void notifySent(String emailAddress, String trackingId) {
        logger.info("Sending shipping notification to: {} with tracking: {}",
                emailAddress, trackingId);

        String message = String.format("Your order is on the way, tracking: %s.",
                trackingId);
        notificationService.Notify(emailAddress, message);
    }
}
