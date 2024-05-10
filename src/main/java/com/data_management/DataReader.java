package com.data_management;

import java.io.IOException;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException{
         File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 4) {
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
    }
}
