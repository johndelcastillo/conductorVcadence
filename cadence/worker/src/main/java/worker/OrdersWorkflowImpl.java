package worker;

import java.time.Duration;
import java.time.Instant;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import worker.domain.StockInfo;
import workflow.common.domain.Order;

public class OrdersWorkflowImpl implements OrdersWorkflow {

    Logger logger = LoggerFactory.getLogger(OrdersWorkflowImpl.class);

    @Override
    public void processOrder(Order order) {

        final OrdersActivities activities = Workflow.newActivityStub(OrdersActivities.class,
                new ActivityOptions.Builder()
                        .setRetryOptions(new RetryOptions.Builder()
                                .setInitialInterval(Duration.ofSeconds(10))
                                .setMaximumAttempts(3)
                                .build())
                        .setScheduleToCloseTimeout(Duration.ofMinutes(5)).build());

        // Check stock
        StockInfo stockInfo = activities.checkStock(order.getOrderId());

        if (!stockInfo.isInStock()) {
            // Notify customer of delay
            activities.notifyDelay(order.getAccountEmail(), stockInfo.getRestockEta());

            // Wait for restock
            Workflow.sleep(stockInfo.getWaitTime());
            // Start a new workflow
            Workflow.continueAsNew(order);
        }

        // Package and send, returning tracking reference
        String trackingId = activities.packageAndSendOrder(order.getOrderId(), order.getAccountAddress());

        // Notify customer
        activities.notifySent(order.getAccountEmail(), trackingId);
    }
}