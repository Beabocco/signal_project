package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private List<Alert> alerts;
    private Map<String, AlertStrategy> strategies;
    private Map<String, AlertFactory> factories;


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
        this.strategies = new HashMap<>();
        this.factories = new HashMap<>();
        initializeStrategies();
        initializeFactories();
    }
    /**
     * Initializes alert strategies for different record types.
     */
    public void initializeStrategies() {
        strategies.put("BloodPressure", new BloodPressureStrategy());
        strategies.put("HeartRate", new HeartRateStrategy());
        strategies.put("OxygenSaturation", new OxygenSaturationStrategy());
        
    }
    /**
     * Initializes alert factories for different record types.
     */
    public void initializeFactories() {
        factories.put("BloodPressure", new BloodPressureAlertFactory());
        factories.put("HeartRate", new ECGAlertFactory()); // Assuming HeartRate and ECG use the same factory
        factories.put("OxygenSaturation", new BloodOxygenAlertFactory());
        
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
            AlertStrategy strategy = strategies.get(record.getRecordType());
            AlertFactory factory = factories.get(record.getRecordType());
            if (strategy != null && factory != null) {
                strategy.checkAlert(record, alerts, factory);
            }
        }
    }
    /**
     * Generates an alert based on the specified record and message, adding decorators.
     *
     * @param record  the PatientRecord that triggered the alert
     * @param message the alert message
     */
    public void generateAlert(PatientRecord record, String message) {
        System.out.println("generateAlert called with record: " + record + " and message: " + message);
        Alert alert = null;
        switch (record.getRecordType()) {
            case "BloodPressure":
                System.out.println("Creating BloodPressureAlert");
                alert = new Alert.BloodPressureAlert(record.getPatientId(), record.getTimestamp(), message);
                break;
            case "OxygenSaturation":
                System.out.println("Creating BloodOxygenAlert");
                alert = new Alert.BloodOxygenAlert(record.getPatientId(), record.getTimestamp(), message);
                break;
            case "HeartRate":
            case "ECG":
                System.out.println("Creating ECGAlert");
                alert = new Alert.ECGAlert(record.getPatientId(), record.getTimestamp(), message);
                break;
            default:
                System.out.println("Unknown record type: " + record.getRecordType());
        }

        if (alert != null) {
            System.out.println("Decorating alert");
            alert = new PriorityAlertDecorator(alert, "High");
            alert = new RepeatedAlertDecorator(alert, 3);
            alerts.add(alert);
            System.out.println("Alert generated: " + alert.getMessage());
        } else {
            System.out.println("Alert not created due to unknown record type.");
        }
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