package data_management;

import com.cardio_generator.HealthDataSimulator;
import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.cardio_generator.outputs.OutputStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the HealthDataSimulator class.
 */
public class HealthDataSimulatorTest {
    /**
     * Resets the singleton instance before each test.
     */
    @BeforeEach
    public void setUp() {
       
        HealthDataSimulator.resetInstance();
    }
    /**
     * Tests the singleton instance of HealthDataSimulator.
     */
    @Test
    public void testSingletonInstance() {
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();
        assertSame(instance1, instance2, "Instances are not the same. Singleton pattern might be broken.");
    }
    /**
     * Tests setting the output strategy.
     */
    @Test
    public void testSetOutputStrategy() {
        OutputStrategy strategy = new ConsoleOutputStrategy();
        HealthDataSimulator.getInstance().setOutputStrategy(strategy);
        assertEquals(strategy, HealthDataSimulator.getInstance().getOutputStrategy(), "Output strategy is not set correctly.");
    }
    /**
     * Tests setting the scheduler.
     */
    @Test
    public void testSetScheduler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        HealthDataSimulator.getInstance().setScheduler(scheduler);
        assertEquals(scheduler, HealthDataSimulator.getInstance().getScheduler(), "Scheduler is not set correctly.");
    }
    /**
     * Tests parsing command line arguments.
     */
    @Test
    public void testParseArguments() throws IOException {
        String[] args = {"--patient-count", "100"};
        HealthDataSimulator.getInstance().parseArguments(args);
        assertEquals(100, HealthDataSimulator.getInstance().getPatientCount(), "Patient count is not parsed correctly.");

        String[] argsWithOutput = {"--output", "console"};
        HealthDataSimulator.getInstance();
    }
}