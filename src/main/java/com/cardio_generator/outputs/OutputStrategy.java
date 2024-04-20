package com.cardio_generator.outputs;
/**
*  Defines an interface for output strategies.
*/
public interface OutputStrategy {
/**
 * Outputs patient data according to the implemented strategy.
 * @param {int} patientId The ID of the patient whose data is being output.
 * @param {long} timestamp The timestamp indicating when the data was generated.
 * @param {String} label The label or type of data being output.
 * @param {String} data The actual data associated with the label.
 */
    void output(int patientId, long timestamp, String label, String data);
}
