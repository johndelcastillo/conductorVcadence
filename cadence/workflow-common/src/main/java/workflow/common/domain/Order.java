package workflow.common.domain;

public class Order {
    private String orderId;
    private String accountEmail;
    private String accountAddress;

    public Order(String orderId, String accountEmail, String accountAddress) {
        this.setOrderId(orderId);
        this.setAccountEmail(accountEmail);
        this.setAccountAddress(accountAddress);
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
