package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates simulated blood pressure data for patients.
 * This class simulates systolic and diastolic blood pressure levels for each patient
 * and outputs the data using the provided output strategy.
 */
public class BloodPressureDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();

    private int[] lastSystolicValues;
    private int[] lastDiastolicValues;
     /**
     * Constructor for BloodPressureDataGenerator.
     * Initializes the baseline systolic and diastolic values for each patient.
     *
     * @param patientCount the number of patients for which data will be generated
     */
    public BloodPressureDataGenerator(int patientCount) {
        lastSystolicValues = new int[patientCount + 1];
        lastDiastolicValues = new int[patientCount + 1];

        // Initialize with baseline values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSystolicValues[i] = 110 + random.nextInt(20); // Random baseline between 110 and 130
            lastDiastolicValues[i] = 70 + random.nextInt(15); // Random baseline between 70 and 85
        }
    }
    /**
     * Generates blood pressure data for a specific patient and outputs it using the provided strategy.
     *
     * @param patientId the ID of the patient for whom data is generated
     * @param outputStrategy the output strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            int systolicVariation = random.nextInt(5) - 2; // -2, -1, 0, 1, or 2
            int diastolicVariation = random.nextInt(5) - 2;
            int newSystolicValue = lastSystolicValues[patientId] + systolicVariation;
            int newDiastolicValue = lastDiastolicValues[patientId] + diastolicVariation;
            
            // Ensure the blood pressure stays within a realistic and safe range
            newSystolicValue = Math.min(Math.max(newSystolicValue, 90), 180);
            newDiastolicValue = Math.min(Math.max(newDiastolicValue, 60), 120);

            lastSystolicValues[patientId] = newSystolicValue;
            lastDiastolicValues[patientId] = newDiastolicValue;

            outputStrategy.output(patientId, System.currentTimeMillis(), "SystolicPressure", Double.toString(newSystolicValue));
            outputStrategy.output(patientId, System.currentTimeMillis(), "DiastolicPressure", Double.toString(newDiastolicValue));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood pressure data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
