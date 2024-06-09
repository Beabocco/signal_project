package com.alerts;

import com.data_management.PatientRecord;
import java.util.List;
/**
 * Strategy for generating alerts based on oxygen saturation readings.
 */
public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public void checkAlert(PatientRecord record, List<Alert> alerts, AlertFactory alertFactory) {
        double oxygenSaturation = record.getMeasurementValue();
        if (oxygenSaturation < 95) {
            Alert alert = alertFactory.createAlert(record.getPatientId(), record.getTimestamp(), "Low oxygen saturation detected: " + oxygenSaturation);
            alerts.add(alert);
            System.out.println("Alert generated: " + alert.getMessage());
        }
    }
}
