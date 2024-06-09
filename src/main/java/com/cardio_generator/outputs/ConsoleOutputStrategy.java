package com.cardio_generator.outputs;
/**
 * Outputs generated data to the console.
 * Implements the OutputStrategy interface.
 */
public class ConsoleOutputStrategy implements OutputStrategy {
    /**
     * Outputs data to the console.
     * 
     * @param patientId the ID of the patient
     * @param timestamp the timestamp of the data
     * @param label the label for the data
     * @param data the actual data
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
