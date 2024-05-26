package com.data_management;

import java.io.IOException;

/**
 * Interface for reading patient data and storing it in a data storage.
 */
public interface DataReader {

    /**
     * Reads data from a specified directory and stores it in the data storage.
     * 
     * @param directoryPath the path to the directory containing the data files
     * @throws IOException if there is an error reading the data
     */
    void readData(String directoryPath) throws IOException;
}