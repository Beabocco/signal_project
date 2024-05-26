package com.cardio_generator;
import java.io.IOException;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.Patient;


public class Main {
    public static void main(String[] args) {
        // Initialize the DataStorage
        DataStorage storage = new DataStorage();

        // Initialize the reader
        FileDataReader reader = new FileDataReader(storage);

        // Read data into the storage
        try {
            reader.readData("path/to/data");
        } catch (IOException e) {
            System.err.println("Error reading data: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if data cannot be read
        }
        

        // Initialize the AlertGenerator with the storage
        AlertGenerator alertGenerator = new AlertGenerator(storage);

        // Evaluate all patients' data to check for conditions that may trigger alerts
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }
}
