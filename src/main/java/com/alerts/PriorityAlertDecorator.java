package com.alerts;
/**
 * Decorator class for adding priority to alerts.
 */
public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;
    /**
     * Constructs a new PriorityAlertDecorator with the specified Alert and priority.
     *
     * @param decoratedAlert the Alert to be decorated
     * @param priority the priority level of the alert
     */
    public PriorityAlertDecorator(Alert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    @Override
    public String getMessage() {
        return "[Priority: " + priority + "] " + decoratedAlert.getMessage();
    }
    /**
     * Returns the priority level of the alert.
     *
     * @return the priority level
     */
    public String getPriority() {
        return priority;
    }
}
