package com.data_management;

import java.io.IOException;
import java.net.URISyntaxException;

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
    /**
     * Reads real-time data from a WebSocket server.
     * 
     * @param websocketUrl the WebSocket URL to connect to
     */
    void readRealTimeData(String websocketUrl) throws URISyntaxException, InterruptedException;
    
}