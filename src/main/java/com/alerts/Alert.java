package com.alerts;

/**
 * Represents an alert generated based on patient data.
 */
public class Alert {
    private int patientId;
    private long timestamp;
    private String message;

    /**
     * Constructs a new Alert with the specified details.
     *
     * @param patientId the unique identifier for the patient
     * @param timestamp the time at which the alert was generated, in milliseconds since epoch
     * @param message the alert message
     */
    public Alert(int patientId, long timestamp, String message) {
        this.patientId = patientId;
        this.timestamp = timestamp;
        this.message = message;
    }

    /**
     * Returns the patient ID associated with this alert.
     *
     * @return the patient ID
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Returns the timestamp when this alert was generated.
     *
     * @return the timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the alert message.
     *
     * @return the alert message
     */
    public String getMessage() {
        return message;
    }
}
