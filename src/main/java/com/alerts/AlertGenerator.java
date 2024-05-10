package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

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
        checkBloodPressureAlerts(patient);
        checkBloodSaturationAlerts(patient);
        checkCombinedAlert(patient);
        checkECGAlerts(patient);
        checkTriggeredAlert(patient);
    }
     private void checkBloodPressureAlerts(Patient patient) {
        List<PatientRecord> records = patient.getRecords();
        double previousSystolic = 0, previousDiastolic = 0;
        int consecutiveIncrease = 0, consecutiveDecrease = 0;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressure")) {
                double systolic = record.getMeasurementValue();
                double diastolic = record.getDiastolicPressure();

                if (systolic > 180 || systolic < 90 || diastolic > 120 || diastolic < 60) {
                    triggerAlert(new Alert(patient.getId(), "Critical Blood Pressure Threshold Exceeded", record.getTimestamp()));
                }

                if (previousSystolic != 0 && previousDiastolic != 0) {
                    if (systolic > previousSystolic + 10) {
                        consecutiveIncrease++;
                    } else if (systolic < previousSystolic - 10) {
                        consecutiveDecrease++;
                    } else {
                        consecutiveIncrease = 0;
                        consecutiveDecrease = 0;
                    }
                }

                if (consecutiveIncrease >= 2 || consecutiveDecrease >= 2) {
                    triggerAlert(new Alert(patient.getId(), "Trend Alert: Blood Pressure", record.getTimestamp()));
                }

                previousSystolic = systolic;
                previousDiastolic = diastolic;
            }
        }
    }

    private void checkBloodSaturationAlerts(Patient patient) {
        List<PatientRecord> records = patient.getRecords();
        double previousSaturation = 0;
        long previousTimestamp = 0;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodSaturation")) {
                double saturation = record.getMeasurementValue();
                long timestamp = record.getTimestamp();

                // Low Saturation Alert
                if (saturation < 92) {
                    triggerAlert(new Alert(patient.getId(), "Low Blood Saturation Alert", timestamp));
                }

                // Rapid Drop Alert
                if (previousSaturation != 0 && previousTimestamp != 0) {
                    long timeDifference = timestamp - previousTimestamp;
                    double saturationChange = previousSaturation - saturation;
                    double saturationDropPercentage = (saturationChange / previousSaturation) * 100;

                    if (timeDifference <= 600000 && saturationDropPercentage >= 5) { // 600000 ms = 10 minutes
                        triggerAlert(new Alert(patient.getId(), "Rapid Blood Saturation Drop Alert", timestamp));
                    }
                }

                previousSaturation = saturation;
                previousTimestamp = timestamp;
            }
        }
    }

    private void checkCombinedAlert(Patient patient) {
        boolean lowSystolicBP = false;
        boolean lowOxygenSaturation = false;

        List<PatientRecord> records = patient.getRecords();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressure")) {
                double pressure = record.getMeasurementValue();
                if (pressure < 90) {
                    lowSystolicBP = true;
                }
            } else if (record.getRecordType().equals("BloodSaturation")) {
                double saturation = record.getMeasurementValue();
                if (saturation < 92) {
                    lowOxygenSaturation = true;
                }
            }
        }

        if (lowSystolicBP && lowOxygenSaturation) {
            long timestamp = System.currentTimeMillis(); // Assuming current time
            triggerAlert(new Alert(patient.getId(), "Hypotensive Hypoxemia Alert", timestamp));
        }
    }

    private void checkECGAlerts(Patient patient) {
        boolean abnormalHeartRate = false;
        boolean irregularBeat = false;

        List<PatientRecord> records = patient.getRecords();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("ECG")) {
                double heartRate = record.getMeasurementValue();
                if (heartRate < 50 || heartRate > 100) {
                    abnormalHeartRate = true;
                }
                double heartRateDifference = Math.abs(nextHeartRate - currentHeartRate);
            if (heartRateDifference > 10) {
                irregularBeat = true;
            }
            }
        }

        if (abnormalHeartRate || irregularBeat) {
            long timestamp = System.currentTimeMillis(); // Assuming current time
            triggerAlert(new Alert(patient.getId(), "ECG Alert", timestamp));
        }
    }

    private void checkTriggeredAlert(Patient patient) {
    List<PatientRecord> records = patient.getRecords();
    for (PatientRecord record : records) {
        if (record.getRecordType().equals("TriggeredAlert")) {
            String condition = record.getCondition();
            long timestamp = record.getTimestamp();

            // Check if the alert is triggered or untriggered based on the condition
            if (condition.equals("Triggered")) {
                triggerAlert(new Alert(patient.getId(), "Triggered Alert", timestamp));
            } 
        }
    }
}

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }
}
