package data_management;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
/**
 * Unit tests for the DataStorage class.
 */
public class DataStorageTest {

    private DataStorage dataStorage;
    /**
     * Sets up the DataStorage instance before each test.
     */
    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
    }
    /**
     * Tests adding and retrieving records in DataStorage.
     */
    @Test
    void testAddAndGetRecords() {
        int patientId = 1;
        double measurementValue = 98.6;
        String recordType = "BodyTemperature";
        long timestamp = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);

        long startTime = timestamp - 1000;
        long endTime = timestamp + 1000;
        List<PatientRecord> records = dataStorage.getRecords(patientId, startTime, endTime);

        assertNotNull(records);
        assertEquals(1, records.size());

        PatientRecord record = records.get(0);
        assertEquals(patientId, record.getPatientId());
        assertEquals(measurementValue, record.getMeasurementValue());
        assertEquals(recordType, record.getRecordType());
        assertEquals(timestamp, record.getTimestamp());
    }
}
