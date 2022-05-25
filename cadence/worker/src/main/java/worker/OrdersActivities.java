package worker;

import com.uber.cadence.activity.ActivityMethod;

import worker.domain.StockInfo;

public interface OrdersActivities {
    @ActivityMethod
    StockInfo checkStock(String orderId);

    @ActivityMethod
    void notifyDelay(String emailAddress, Long eta);

    @ActivityMethod
    String packageAndSendOrder(String orderId, String address);

    @ActivityMethod
    void notifySent(String emailAddress, String trackingId);
}
