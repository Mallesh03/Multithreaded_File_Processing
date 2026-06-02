import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Multithreaded File Processing System...");

        try {
            initializeFolders();
            createSampleInputFilesIfMissing();

            String[] inputFiles = {
                    "input/file1.txt",
                    "input/file2.txt",
                    "input/file3.txt"
            };

            List<Thread> workerThreads = new ArrayList<>();

            for (String inputFile : inputFiles) {
                String outputFile = buildOutputFilePath(inputFile);
                Runnable fileProcessor = new FileProcessor(inputFile, outputFile);

                // Each file is processed by a separate Thread instance to demonstrate multithreading.
                Thread workerThread = new Thread(fileProcessor, "Processor-" + new File(inputFile).getName());
                workerThreads.add(workerThread);
                workerThread.start();
            }

            // join() ensures the main thread waits until every worker thread finishes processing.
            for (Thread workerThread : workerThreads) {
                workerThread.join();
            }

            System.out.println("All files have been processed successfully.");
            System.out.println("Processed files are available in the output/ folder.");
            System.out.println("Logs are available in logs/processing_log.txt");
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread was interrupted while waiting for file processing to finish.");
        } catch (IOException exception) {
            System.out.println("Setup failed: " + exception.getMessage());
        }
    }

    private static void initializeFolders() throws IOException {
        Files.createDirectories(Paths.get("input"));
        Files.createDirectories(Paths.get("output"));
        Files.createDirectories(Paths.get("logs"));
    }

    private static void createSampleInputFilesIfMissing() throws IOException {
        createSampleFile("input/file1.txt",
                "Java is a powerful programming language.\n" +
                "It supports multithreading and object-oriented programming.\n" +
                "This file will be processed by a separate thread.");

        createSampleFile("input/file2.txt",
                "BufferedReader reads text efficiently line by line.\n" +
                "BufferedWriter writes output data in a clean and efficient way.\n" +
                "Concurrency helps applications use system resources better.");

        createSampleFile("input/file3.txt",
                "Thread safety is important in concurrent applications.\n" +
                "Synchronization prevents data corruption when multiple threads access shared resources.\n" +
                "This sample demonstrates core Java concepts.");

        Path logFilePath = Paths.get("logs/processing_log.txt");
        if (!Files.exists(logFilePath)) {
            Files.createFile(logFilePath);
        }
    }

    private static void createSampleFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                writer.write(content);
            }
        }
    }

    private static String buildOutputFilePath(String inputFilePath) {
        String fileName = new File(inputFilePath).getName();
        return "output/processed_" + fileName;
    }
}
