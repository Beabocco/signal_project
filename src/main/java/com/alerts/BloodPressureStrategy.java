package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;
/**
 * Strategy for generating alerts based on blood pressure readings.
 */
public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public void checkAlert(PatientRecord record, List<Alert> alerts, AlertFactory alertFactory) {
        double bloodPressure = record.getMeasurementValue();
        if (bloodPressure < 90 || bloodPressure > 140) {
            Alert alert = alertFactory.createAlert(record.getPatientId(), record.getTimestamp(), "Abnormal blood pressure detected: " + bloodPressure);
            alerts.add(alert);
            System.out.println("Alert generated: " + alert.getMessage());
        }
    }
}

