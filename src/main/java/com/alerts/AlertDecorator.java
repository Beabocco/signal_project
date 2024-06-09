package com.alerts;

/**
 * Abstract decorator class for Alerts.
 */
public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    /**
     * Constructs a new AlertDecorator with the specified Alert to be decorated.
     *
     * @param decoratedAlert the Alert to be decorated
     */
    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.getPatientId(), decoratedAlert.getTimestamp(), decoratedAlert.getMessage());
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public int getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    @Override
    public String getMessage() {
        return decoratedAlert.getMessage();
    }
}
