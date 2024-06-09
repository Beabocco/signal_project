package com.alerts;
/**
 * Decorator class for adding repeat count to alerts.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;
    /**
     * Constructs a new RepeatedAlertDecorator with the specified Alert and repeat count.
     *
     * @param decoratedAlert the Alert to be decorated
     * @param repeatCount the number of times the alert should be repeated
     */
    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
    }

    @Override
    public String getMessage() {
        return decoratedAlert.getMessage() + " (Repeated " + repeatCount + " times)";
    }
    /**
     * Returns the repeat count of the alert.
     *
     * @return the repeat count
     */
    public int getRepeatCount() {
        return repeatCount;
    }
}
