package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private List<Alert> alerts;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alerts = new ArrayList<>();
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getPatientRecords();

        for (PatientRecord record : records) {
            switch (record.getRecordType()) {
                case "HeartRate":
                    checkHeartRate(record);
                    break;
                case "BloodPressure":
                    checkBloodPressure(record);
                    break;
                case "OxygenSaturation":
                    checkOxygenSaturation(record);
                    break;
                case "Temperature":
                    checkTemperature(record);
                    break;
                case "RespiratoryRate":
                    checkRespiratoryRate(record);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Checks if the heart rate is within the normal range and generates an alert if not.
     *
     * @param record the PatientRecord to be checked
     */
    private void checkHeartRate(PatientRecord record) {
        double heartRate = record.getMeasurementValue();
        if (heartRate < 60 || heartRate > 100) {
            generateAlert(record, "Abnormal heart rate detected: " + heartRate);
        }
    }

    /**
     * Checks if the blood pressure is within the normal range and generates an alert if not.
     *
     * @param record the PatientRecord to be checked
     */
    private void checkBloodPressure(PatientRecord record) {
        double bloodPressure = record.getMeasurementValue();
        // Assuming the blood pressure value represents systolic pressure
        if (bloodPressure < 90 || bloodPressure > 140) {
            generateAlert(record, "Abnormal blood pressure detected: " + bloodPressure);
        }
    }

    /**
     * Checks if the oxygen saturation is within the normal range and generates an alert if not.
     *
     * @param record the PatientRecord to be checked
     */
    private void checkOxygenSaturation(PatientRecord record) {
        double oxygenSaturation = record.getMeasurementValue();
        if (oxygenSaturation < 95) {
            generateAlert(record, "Low oxygen saturation detected: " + oxygenSaturation);
        }
    }

    /**
     * Checks if the temperature is within the normal range and generates an alert if not.
     *
     * @param record the PatientRecord to be checked
     */
    private void checkTemperature(PatientRecord record) {
        double temperature = record.getMeasurementValue();
        if (temperature < 36.1 || temperature > 37.2) {
            generateAlert(record, "Abnormal temperature detected: " + temperature);
        }
    }

    /**
     * Checks if the respiratory rate is within the normal range and generates an alert if not.
     *
     * @param record the PatientRecord to be checked
     */
    private void checkRespiratoryRate(PatientRecord record) {
        double respiratoryRate = record.getMeasurementValue();
        if (respiratoryRate < 12 || respiratoryRate > 20) {
            generateAlert(record, "Abnormal respiratory rate detected: " + respiratoryRate);
        }
    }

    /**
     * Generates an alert based on the specified record and message.
     *
     * @param record the PatientRecord that triggered the alert
     * @param message the alert message
     */
    private void generateAlert(PatientRecord record, String message) {
        Alert alert = new Alert(record.getPatientId(), record.getTimestamp(), message);
        alerts.add(alert);
        System.out.println("Alert generated: " + message);
    }

    /**
     * Returns the list of generated alerts.
     *
     * @return the list of alerts
     */
    public List<Alert> getAlerts() {
        return alerts;
    }
}