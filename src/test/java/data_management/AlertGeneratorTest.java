package data_management;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import com.data_management.PatientRecord;
import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the AlertGenerator class.
 */
public class AlertGeneratorTest {

    private DataStorage mockDataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    /**
     * Sets up the mock objects before each test.
     */
    void setUp() {
        mockDataStorage = mock(DataStorage.class);
        alertGenerator = new AlertGenerator(mockDataStorage);
    }
    /**
     * Tests the generateAlert method of AlertGenerator.
     */
    @Test
    void testGenerateAlert() throws Exception {
        // Mock a PatientRecord
        PatientRecord mockPatientRecord = mock(PatientRecord.class);
        when(mockPatientRecord.getPatientId()).thenReturn(1);
        when(mockPatientRecord.getTimestamp()).thenReturn(System.currentTimeMillis());
        when(mockPatientRecord.getRecordType()).thenReturn("BloodPressure");

        // Invoke the generateAlert method directly
        alertGenerator.generateAlert(mockPatientRecord, "High blood pressure detected");

        // Verify that the alert was added
        List<Alert> alerts = alertGenerator.getAlerts();
        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).getMessage().contains("High blood pressure detected"));
    }
}
