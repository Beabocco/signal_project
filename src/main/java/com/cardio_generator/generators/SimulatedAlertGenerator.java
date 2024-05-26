package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates simulated alert data for patient.
 * Alerts are represented as boolean values, where false indicates that the alertis resolved, 
 * and true indicates that the alert is triggered.
 */
public class SimulatedAlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    //changed variable name to camelCase
    private boolean[] alertStates; // false = resolved, true = pressed
/** 
* constructor of the AlertGenerator class.
* @parem {int} patientCount the number of patients for which alert data will be simulated.
*/
    public SimulatedAlertGenerator(int patientCount) {
        //changed variable name to camelCase
        alertStates = new boolean[patientCount + 1];
    }
/**
* Generates alert data for a specific patient and outputs it using the provided strategy.
*
* If an alert is triggered, it is output as "triggered"; if a previously
* triggered alert is resolved, it is output as "resolved".
*
* @param patientId The ID of the patient for which data is generated.
* @param outputStrategy The output strategy used to output the generated data.
* @throws Exception if an error occurs during data generation or output.
*/
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) { //changed variable name to camelCase
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //changed variable name to camelCase
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
