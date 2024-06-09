package data_management;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cardio_generator.generators.BloodLevelsDataGenerator;
import com.cardio_generator.generators.BloodPressureDataGenerator;
import com.cardio_generator.generators.BloodSaturationDataGenerator;
import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
/**
 * Unit tests for various data generators.
 */
public class DataGeneratorsTest {

    private OutputStrategy mockOutputStrategy;
    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    void setUp() {
        mockOutputStrategy = mock(OutputStrategy.class);
    }
    /**
     * Tests the ECGDataGenerator.
     */
    @Test
    void testECGDataGenerator() {
        ECGDataGenerator ecgDataGenerator = new ECGDataGenerator(1);
        ecgDataGenerator.generate(1, mockOutputStrategy);
        verify(mockOutputStrategy, times(1)).output(anyInt(), anyLong(), anyString(), anyString());
    }
    /**
     * Tests the BloodSaturationDataGenerator.
     */
    @Test
    void testBloodSaturationDataGenerator() {
        BloodSaturationDataGenerator saturationDataGenerator = new BloodSaturationDataGenerator(1);
        saturationDataGenerator.generate(1, mockOutputStrategy);
        verify(mockOutputStrategy, times(1)).output(anyInt(), anyLong(), anyString(), anyString());
    }
    /**
     * Tests the BloodPressureDataGenerator.
     */
    @Test
    void testBloodPressureDataGenerator() {
        BloodPressureDataGenerator pressureDataGenerator = new BloodPressureDataGenerator(1);
        pressureDataGenerator.generate(1, mockOutputStrategy);
        verify(mockOutputStrategy, times(2)).output(anyInt(), anyLong(), anyString(), anyString());
    }
    /**
     * Tests the BloodLevelsDataGenerator.
     */
    @Test
    void testBloodLevelsDataGenerator() {
        BloodLevelsDataGenerator levelsDataGenerator = new BloodLevelsDataGenerator(1);
        levelsDataGenerator.generate(1, mockOutputStrategy);
        verify(mockOutputStrategy, times(3)).output(anyInt(), anyLong(), anyString(), anyString());
    }
}
