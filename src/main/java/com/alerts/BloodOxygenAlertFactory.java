package com.alerts;
/**
 * Factory class for creating BloodOxygenAlert objects.
 */
public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(int patientId, long timestamp, String message) {
        return new Alert.BloodOxygenAlert(patientId, timestamp, message);
    }
}
