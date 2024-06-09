package data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the FileDataReader class.
 */
public class DataReaderTest {

    private DataStorage mockDataStorage;
    private FileDataReader fileDataReader;
    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    public void setUp() {
        mockDataStorage = mock(DataStorage.class);
        fileDataReader = new FileDataReader(mockDataStorage);
    }
    /**
     * Tests reading data from an invalid directory.
     */
    @Test
    public void testReadData_InvalidDirectory() {
        assertThrows(IllegalArgumentException.class, () -> fileDataReader.readData("invalid/path"));
    }
    /**
     * Tests reading data from a valid directory.
     */
    @Test
    public void testReadData_ValidDirectory() throws IOException {
        // Create a temporary directory and file for testing
        Path tempDir = Files.createTempDirectory("testData");
        Path tempFile = Files.createFile(Paths.get(tempDir.toString(), "testData.txt"));
        Files.write(tempFile, "1,75.5,HeartRate,1627889182000".getBytes());

        // Use the temporary directory path for the test
        fileDataReader.readData(tempDir.toString());

        // Clean up the temporary files
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }
     /**
     * Tests reading real-time data (not supported in FileDataReader).
     */
    @Test
    public void testReadRealTimeData_UnsupportedOperation() {
        assertThrows(UnsupportedOperationException.class, () -> fileDataReader.readRealTimeData("ws://localhost:8080"));
    }
}
