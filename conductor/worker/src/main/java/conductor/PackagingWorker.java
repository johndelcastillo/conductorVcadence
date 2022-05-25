package conductor;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conductor.service.StockService;

public class PackagingWorker implements Worker {

    private final String taskDefName;
    private StockService stockService;

    Logger logger = LoggerFactory.getLogger(PackagingWorker.class);

    public PackagingWorker(String taskDefName, StockService stockService) {
        this.taskDefName = taskDefName;
        this.stockService = stockService;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        // Do the work
        String orderId = (String) task.getInputData().get("orderId");
        String address = (String) task.getInputData().get("address");

        logger.info("Packaging order: {} and sending to: {}", orderId, address);
        String trackingRef = this.stockService.packageAndSendOrder(orderId, address);

        // Return the result
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        // Register the output of the task
        result.getOutputData().put("trackingRef", trackingRef);

        return result;
    }
}