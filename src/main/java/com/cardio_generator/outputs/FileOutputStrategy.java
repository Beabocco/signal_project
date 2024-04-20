package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Represents an output strategy for storing patient data in files.
 */

//the class name should match the name of the file
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; //changed variable name to camelCase

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();
/**
* Constructs a FileOutputStrategy.
* @param {String} baseDirectory The base directory where output files will be stored.
*/
    //the constructor should have the same name as the class
    public fileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory; //changed variable name to camelCase
    }
/**
 * Outputs patient data to files based on the provided patient ID, timestamp, label, and data.
 *
 * @param {int} patientId The ID of the patient associated with the data.
 * @param {long} timeStamp The timestamp indicating when the data was generated.
 * @param {String} label The label or type of data being output.
 * @param {String} data The actual data to be written to the file.
 * @throws IOException if an I/O error occurs while writing to the file.
 */
    @Override
    public void output(int patientId, long timeStamp, String label, String data) { //canged variable name to camelCase(timeStamp)
        
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory)); //changed variable name to camelCase
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label, k -> Paths.get(BaseDirectory, label + ".txt").toString());
        //create a path object so it's easier to use it later
        Path file = Paths.get(filePath);

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        } catch (IOException e) { //to catch an exemption is IOExeption e not Exeption e
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}
