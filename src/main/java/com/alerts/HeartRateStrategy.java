package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;
/**
 * Strategy for generating alerts based on heart rate readings.
 */
public class HeartRateStrategy implements AlertStrategy {
    @Override
    public void checkAlert(PatientRecord record, List<Alert> alerts, AlertFactory alertFactory) {
        double heartRate = record.getMeasurementValue();
        if (heartRate < 60 || heartRate > 100) {
            Alert alert = alertFactory.createAlert(record.getPatientId(), record.getTimestamp(), "Abnormal heart rate detected: " + heartRate);
            alerts.add(alert);
            System.out.println("Alert generated: " + alert.getMessage());
        }
    }
}
