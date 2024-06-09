package com.alerts;
/**
 * Abstract factory class for creating Alert objects.
 */
public abstract class AlertFactory {
    public abstract Alert createAlert(int patientId, long timestamp, String message);
}
