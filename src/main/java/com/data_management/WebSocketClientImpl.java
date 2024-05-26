package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientImpl extends WebSocketClient {

    private DataStorage dataStorage;

    public WebSocketClientImpl(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server.");
    }

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

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: ");
        ex.printStackTrace();
    }

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
