package com.data_management;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implementation of DataReader interface to read patient data from files in a specified directory.
 */
public class FileDataReader implements DataReader {
    private DataStorage dataStorage;

    /**
     * Constructs a FileDataReader with the specified data storage.
     * 
     * @param dataStorage the storage where data will be stored
     */
    public FileDataReader(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Reads data from a specified directory and stores it in the data storage.
     * 
     * @param directoryPath the path to the directory containing the data files
     * @throws IOException if there is an error reading the data
     */
    @Override
    public void readData(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt")); // Assuming data files are .txt
        if (files != null) {
            for (File file : files) {
                parseFile(file);
            }
        }
    }

    /**
     * Parses a file to read patient data and stores it in the data storage.
     * 
     * @param file the file to be parsed
     * @throws IOException if there is an error reading the file
     */
    private void parseFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
        for (String line : lines) {
            parseLine(line);
        }
    }

    /**
     * Parses a line of data and stores it in the data storage.
     * 
     * @param line the line of data to be parsed
     */
    private void parseLine(String line) {
        // Assume the line format is: patientId,measurementValue,recordType,timestamp
        String[] parts = line.split(",");
        if (parts.length == 4) {
            try {
                int patientId = Integer.parseInt(parts[0]);
                double measurementValue = Double.parseDouble(parts[1]);
                String recordType = parts[2];
                long timestamp = Long.parseLong(parts[3]);

                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing data in line: " + line);
            }
        } else {
            System.err.println("Invalid data format in line: " + line);
        }
    }
}