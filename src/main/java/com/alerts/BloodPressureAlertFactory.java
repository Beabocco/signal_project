package com.alerts;
/**
 * Factory class for creating BloodPressureAlert objects.
 */
public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(int patientId, long timestamp, String message) {
        return new Alert.BloodPressureAlert(patientId, timestamp, message);
    }
}
