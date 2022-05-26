package conductor;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conductor.service.NotificationService;

public class NotificationWorker implements Worker {

    Logger logger = LoggerFactory.getLogger(NotificationWorker.class);

    private final String taskDefName;
    private NotificationService notificationService;

    public NotificationWorker(String taskDefName, NotificationService notificationService) {
        this.taskDefName = taskDefName;
        this.notificationService = notificationService;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {

        // Do the work
        String emailAddress = (String) task.getInputData().get("email");
        String message = (String) task.getInputData().get("message");

        logger.info("Sending \"{}\" to: {}", message, emailAddress);

        notificationService.Notify(emailAddress, message);

        // Return the result
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        return result;
    }
}