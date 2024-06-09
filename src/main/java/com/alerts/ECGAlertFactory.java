package com.alerts;
/**
 * Factory class for creating ECGAlert objects.
 */
public class ECGAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(int patientId, long timestamp, String message) {
        return new Alert.ECGAlert(patientId, timestamp, message);
    }
}
