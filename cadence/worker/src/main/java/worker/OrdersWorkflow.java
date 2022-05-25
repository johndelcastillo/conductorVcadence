
package worker;

import com.uber.cadence.workflow.WorkflowMethod;

import workflow.common.domain.Order;

public interface OrdersWorkflow {
    @WorkflowMethod
    void processOrder(Order order);
}
