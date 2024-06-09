package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
/**
 * Outputs generated data via WebSocket.
 * Implements the OutputStrategy interface.
 */
public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;
    /**
     * Constructs a WebSocketOutputStrategy.
     * 
     * @param port the port for the WebSocket server
     */
    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }
    /**
     * Outputs data via WebSocket.
     * 
     * @param patientId the ID of the patient
     * @param timestamp the timestamp of the data
     * @param label the label for the data
     * @param data the actual data
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        // Broadcast the message to all connected clients
        for (WebSocket conn : server.getConnections()) {
            conn.send(message);
        }
    }
    /**
     * A simple WebSocket server that handles client connections and messages.
     */
    private static class SimpleWebSocketServer extends WebSocketServer {
        /**
         * Constructs a SimpleWebSocketServer.
         * 
         * @param address the address for the WebSocket server
         */
        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
