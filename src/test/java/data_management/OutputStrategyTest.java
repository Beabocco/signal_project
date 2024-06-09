package data_management;

import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.cardio_generator.outputs.FileOutputStrategy;
import com.cardio_generator.outputs.OutputStrategy;
import com.cardio_generator.outputs.TcpOutputStrategy;
import com.cardio_generator.outputs.WebSocketOutputStrategy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for various output strategies.
 */
public class OutputStrategyTest {
    /**
     * Tests the ConsoleOutputStrategy.
     */
    @Test
    void testConsoleOutputStrategy() {
        OutputStrategy consoleOutput = new ConsoleOutputStrategy();
        consoleOutput.output(1, System.currentTimeMillis(), "testLabel", "Test data");
        // Since ConsoleOutputStrategy outputs to the console, we can visually inspect the console for correctness.
    }
    /**
     * Tests the FileOutputStrategy.
     */
    @Test
    void testFileOutputStrategy() throws IOException {
        String baseDirectory = "test_output";
        OutputStrategy fileOutput = new FileOutputStrategy(baseDirectory);

        fileOutput.output(1, System.currentTimeMillis(), "testLabel", "Test data");
        Path filePath = Paths.get(baseDirectory, "testLabel.txt");
        assertTrue(Files.exists(filePath));

        String content = Files.readString(filePath);
        assertTrue(content.contains("Test data"));

        Files.deleteIfExists(filePath);
        Files.deleteIfExists(Paths.get(baseDirectory));
    }
    /**
     * Tests the WebSocketOutputStrategy.
     */
    @Test
    void testWebSocketOutputStrategy() throws IOException, InterruptedException {
        int port = 8080;
        OutputStrategy webSocketOutput = new WebSocketOutputStrategy(port);
        
        // Since WebSocketOutputStrategy requires an actual WebSocket client to test fully,
        // we will just ensure the server starts and is ready.
        // This test does not perform full WebSocket communication testing.
        
        // Allow some time for the server to start
        TimeUnit.SECONDS.sleep(1);
        
        // Simulate output
        webSocketOutput.output(1, System.currentTimeMillis(), "testLabel", "Test data");
        
        // No specific assertions since we don't have a WebSocket client connected
        // In a full test suite, we would use a WebSocket client to verify the message
    }

    @Test
    void testTcpOutputStrategy() throws IOException, InterruptedException {
        int port = 9090;
        OutputStrategy tcpOutput = new TcpOutputStrategy(port);
        
        // Since TcpOutputStrategy requires an actual TCP client to test fully,
        // we will just ensure the server starts and is ready.
        // This test does not perform full TCP communication testing.
        
        // Allow some time for the server to start
        TimeUnit.SECONDS.sleep(1);
        
        // Simulate output
        tcpOutput.output(1, System.currentTimeMillis(), "testLabel", "Test data");
        
        // No specific assertions since we don't have a TCP client connected
        // In a full test suite, we would use a TCP client to verify the message
    }
}
