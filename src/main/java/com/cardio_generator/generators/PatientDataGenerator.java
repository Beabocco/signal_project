package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/** 
*This interface specifies a method for generating simulated patient data for health monitoring simulations.
*/
public interface PatientDataGenerator {
/**
 * Generates simulated patient data for the specified patient ID using the provided output strategy.
 * @param {int} patientId The ID of the patient for whom data is generated.
 * @param {OutputStrategy} outputStrategy The output strategy used to output the generated patient data.
 */
    void generate(int patientId, OutputStrategy outputStrategy);
}
