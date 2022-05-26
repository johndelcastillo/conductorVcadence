package conductor;

import java.time.Duration;
import java.time.Instant;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conductor.service.StockService;

public class StockWorker implements Worker {

    private final String taskDefName;
    private StockService stockService;

    Logger logger = LoggerFactory.getLogger(StockWorker.class);

    public StockWorker(String taskDefName, StockService stockService) {
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

        logger.info("Checking stock level for: {}", orderId);
        long restockEta = this.stockService.getRestockEta(orderId);
        long restockEtaDays = Duration.ofSeconds(restockEta - Instant.now().getEpochSecond()).toDays();
        boolean inStock = Instant.ofEpochSecond(restockEta).isBefore(Instant.now());

        logger.info("In Stock: {}", inStock);

        // Return the result
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        // Register the output of the task
        result.getOutputData().put("inStock", inStock);
        result.getOutputData().put("restockEtaDays", restockEtaDays);

        return result;
    }
}