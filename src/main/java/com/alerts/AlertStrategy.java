package com.alerts;

import java.util.List;

import com.data_management.PatientRecord;
/**
 * Interface for defining alert strategies based on patient records.
 */
public interface AlertStrategy {
    void checkAlert(PatientRecord record, List<Alert> alerts, AlertFactory alertFactory);
}

