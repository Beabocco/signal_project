package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
/**
 * WebSocket client implementation that connects to a server and handles incoming messages.
 */
public class WebSocketClientImpl extends WebSocketClient {

    private DataStorage dataStorage;
    /**
     * Constructor for WebSocketClientImpl.
     * 
     * @param serverUri The URI of the WebSocket server.
     * @param dataStorage The data storage instance to store received data.
     */
    public WebSocketClientImpl(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }
    /**
     * Called when the WebSocket connection is opened.
     * 
     * @param handshakedata The handshake data.
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server.");
    }
    /**
     * Called when a message is received from the WebSocket server.
     * 
     * @param message The received message.
     */
    @Override
    public void onMessage(String message) {
        // Parse the incoming message and store it in DataStorage
        try {
            String[] parts = message.split(",");
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            String data = parts[3];

            dataStorage.addPatientData(patientId, Double.parseDouble(data), label, timestamp);
        } catch (Exception e) {
            System.err.println("Error parsing message: " + message);
            e.printStackTrace();
        }
    }
    /**
     * Called when the WebSocket connection is closed.
     * 
     * @param code The closing code.
     * @param reason The reason for closing.
     * @param remote Whether the closing was initiated by the remote host.
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }
    /**
     * Called when there is an error in the WebSocket connection.
     * 
     * @param ex The exception that occurred.
     */
    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: ");
        ex.printStackTrace();
    }
    /**
     * Main method to create a WebSocket client instance and connect to the server.
     */
    public static void main(String[] args) {
        try {
            DataStorage storage = new DataStorage();
            WebSocketClientImpl client = new WebSocketClientImpl(new URI("ws://localhost:8080"), storage);
            client.connectBlocking();
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
