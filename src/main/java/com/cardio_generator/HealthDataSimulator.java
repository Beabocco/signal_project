package com.cardio_generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import com.alerts.AlertGenerator;
import com.cardio_generator.generators.BloodLevelsDataGenerator;
import com.cardio_generator.generators.BloodPressureDataGenerator;
import com.cardio_generator.generators.BloodSaturationDataGenerator;
import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.cardio_generator.outputs.FileOutputStrategy;
import com.cardio_generator.outputs.OutputStrategy;
import com.cardio_generator.outputs.TcpOutputStrategy;
import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
/**
* simulates health data for a given number or parients utilizing different data generators.
* The generated data is then outputted using different strategies.
*/
public class HealthDataSimulator {
    private static HealthDataSimulator instance; // Singleton instance
    private static int patientCount = 50; // Default number of patients
    private static ScheduledExecutorService scheduler;
    private static OutputStrategy outputStrategy = new ConsoleOutputStrategy(); // Default output strategy
    private static Random random = new Random(); // Instance of Random

    public HealthDataSimulator() {
    }

    /**
     * Provides global access to the singleton instance of HealthDataSimulator.
     *
     * @return the singleton instance of HealthDataSimulator
     */
    public static synchronized HealthDataSimulator getInstance() {
        if (instance == null) {
            instance = new HealthDataSimulator();
        }
        return instance;
    }

     // Helper methods for testing
     public static void resetInstance() {
        instance = null;
        patientCount = 50;
        scheduler = null;
        outputStrategy = new ConsoleOutputStrategy();
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        HealthDataSimulator.scheduler = scheduler;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void setOutputStrategy(OutputStrategy outputStrategy) {
        HealthDataSimulator.outputStrategy = outputStrategy;
    }

    public OutputStrategy getOutputStrategy() {
        return outputStrategy;
    }

    public int getPatientCount() {
        return patientCount;
    }

/** the main method.  It handles the parsing of command-line arguments, sets up required components,
 * and schedules tasks for generating health data.
 * @param {String[]} args Command line arguments
 * @throws IOException If an I/O error occurs during argument parsing
*/
    public static void main(String[] args) throws IOException {
        HealthDataSimulator simulator = HealthDataSimulator.getInstance();
        simulator.run(args);
    }

    /**
     * Runs the health data simulation.
     * 
     * @param args Command line arguments
     * @throws IOException If an I/O error occurs during argument parsing
     */
    public void run(String[] args) throws IOException {
        parseArguments(args);
        scheduler = Executors.newScheduledThreadPool(patientCount * 4);
        List<Integer> patientIds = initializePatientIds(patientCount);
        Collections.shuffle(patientIds); // Randomize the order of patient IDs
        scheduleTasksForPatients(patientIds);
    }

    /**
     * Parses command line arguments to determine the number of patients to simulate
     * and the output strategy for the generated data.
     *
     * @param args Command line arguments passed to the application.
     * @throws IOException If there's an issue while reading or processing the command line arguments.
     */
    public static void parseArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    printHelp();
                    System.exit(0);
                    break;
                case "--patient-count":
                    if (i + 1 < args.length) {
                        try {
                            patientCount = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err
                                    .println("Error: Invalid number of patients. Using default value: " + patientCount);
                        }
                    }
                    break;
                case "--output":
                    if (i + 1 < args.length) {
                        String outputArg = args[++i];
                        if (outputArg.equals("console")) {
                            outputStrategy = new ConsoleOutputStrategy();
                        } else if (outputArg.startsWith("file:")) {
                            String baseDirectory = outputArg.substring(5);
                            Path outputPath = Paths.get(baseDirectory);
                            if (!Files.exists(outputPath)) {
                                Files.createDirectories(outputPath);
                            }
                            outputStrategy = new FileOutputStrategy(baseDirectory);
                        } else if (outputArg.startsWith("websocket:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(10));
                                
                                outputStrategy = new WebSocketOutputStrategy(port);
                                System.out.println("WebSocket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println(
                                        "Invalid port for WebSocket output. Please specify a valid port number.");
                            }
                        } else if (outputArg.startsWith("tcp:")) {
                            try {
                                int port = Integer.parseInt(outputArg.substring(4));
                                
                                outputStrategy = new TcpOutputStrategy(port);
                                System.out.println("TCP socket output will be on port: " + port);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid port for TCP output. Please specify a valid port number.");
                            }
                        } else {
                            System.err.println("Unknown output type. Using default (console).");
                        }
                    }
                    break;
                default:
                    System.err.println("Unknown option '" + args[i] + "'");
                    printHelp();
                    System.exit(1);
            }
        }
    }

    /**
     * Prints usage information for the HealthDataSimulator application.
     */
    public static void printHelp() {
        System.out.println("Usage: java HealthDataSimulator [options]");
        System.out.println("Options:");
        System.out.println("  -h                       Show help and exit.");
        System.out.println(
                "  --patient-count <count>  Specify the number of patients to simulate data for (default: 50).");
        System.out.println("  --output <type>          Define the output method. Options are:");
        System.out.println("                             'console' for console output,");
        System.out.println("                             'file:<directory>' for file output,");
        System.out.println("                             'websocket:<port>' for WebSocket output,");
        System.out.println("                             'tcp:<port>' for TCP socket output.");
        System.out.println("Example:");
        System.out.println("  java HealthDataSimulator --patient-count 100 --output websocket:8080");
        System.out.println(
                "  This command simulates data for 100 patients and sends the output to WebSocket clients connected to port 8080.");
    }
    /**
     * Initializes a list of patient IDs, starting from 1.
     * 
     * @param patientCount The number of patients.
     * @return A list of patient IDs.
     */
    public static List<Integer> initializePatientIds(int patientCount) {
        List<Integer> patientIds = new ArrayList<>();
        for (int i = 1; i <= patientCount; i++) {
            patientIds.add(i);
        }
        return patientIds;
    }
/** schedules a task for each patient. This includes generating ECG data, blood saturation data,
* blood pressure data, blood levels data, and alerts for each patient.
* @param patientIds List of patient IDs
**/
    public static void scheduleTasksForPatients(List<Integer> patientIds) {
        ECGDataGenerator ecgDataGenerator = new ECGDataGenerator(patientCount);
        BloodSaturationDataGenerator bloodSaturationDataGenerator = new BloodSaturationDataGenerator(patientCount);
        BloodPressureDataGenerator bloodPressureDataGenerator = new BloodPressureDataGenerator(patientCount);
        BloodLevelsDataGenerator bloodLevelsDataGenerator = new BloodLevelsDataGenerator(patientCount);
        DataStorage dataStorage = new DataStorage();
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        for (int patientId : patientIds) {
            scheduleTask(() -> ecgDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodSaturationDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.SECONDS);
            scheduleTask(() -> bloodPressureDataGenerator.generate(patientId, outputStrategy), 1, TimeUnit.MINUTES);
            scheduleTask(() -> bloodLevelsDataGenerator.generate(patientId, outputStrategy), 2, TimeUnit.MINUTES);
        }
    }

 /** Schedules a task for execution with a fixed rate.
 * @param task     The task to be scheduled
 * @param period   The time period of the task
 * @param timeUnit The time unit of the period parameter
 **/
    public static void scheduleTask(Runnable task, long period, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, random.nextInt(5), period, timeUnit);
    }
}
