import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {

    private static final String LOG_FILE_PATH = "logs/processing_log.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerUtil() {
        // Utility class: prevent direct object creation.
    }

    // Synchronization ensures only one thread writes to the log file at a time.
    public static synchronized void logProcessingResult(String threadName,
                                                        String fileName,
                                                        String status,
                                                        int lineCount,
                                                        int wordCount,
                                                        long processingTimeMillis) {
        try {
            Path logFilePath = Paths.get(LOG_FILE_PATH);
            Path logDirectory = logFilePath.getParent();
            if (logDirectory != null) {
                Files.createDirectories(logDirectory);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
                String logEntry = String.format(
                        "[%s] Thread=%s | File=%s | Status=%s | Lines=%d | Words=%d | Time=%d ms",
                        LocalDateTime.now().format(DATE_TIME_FORMATTER),
                        threadName,
                        fileName,
                        status,
                        lineCount,
                        wordCount,
                        processingTimeMillis);

                writer.write(logEntry);
                writer.newLine();
            }
        } catch (IOException exception) {
            System.out.println("Failed to write log entry: " + exception.getMessage());
        }
    }
}
