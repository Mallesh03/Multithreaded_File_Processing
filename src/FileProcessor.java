import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class FileProcessor implements Runnable {

    private final String inputFilePath;
    private final String outputFilePath;

    public FileProcessor(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        String threadName = Thread.currentThread().getName();
        String fileName = Paths.get(inputFilePath).getFileName().toString();
        int lineCount = 0;
        int wordCount = 0;

        try {
            System.out.println(threadName + " is processing " + fileName);

            // BufferedReader reads text efficiently by buffering input from the file stream.
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

                String currentLine;
                StringBuilder processedContent = new StringBuilder();

                while ((currentLine = reader.readLine()) != null) {
                    lineCount++;
                    wordCount += countWords(currentLine);
                    processedContent.append(currentLine.toUpperCase())
                            .append(System.lineSeparator());
                }

                // BufferedWriter batches output writes, which improves performance for file generation.
                writer.write(processedContent.toString());
            }

            long processingTime = System.currentTimeMillis() - startTime;
            LoggerUtil.logProcessingResult(threadName, fileName, "SUCCESS", lineCount, wordCount, processingTime);
            System.out.println(threadName + " completed " + fileName + " successfully.");
        } catch (FileNotFoundException exception) {
            long processingTime = System.currentTimeMillis() - startTime;
            LoggerUtil.logProcessingResult(threadName, fileName, "FAILED - FILE NOT FOUND", lineCount, wordCount, processingTime);
            System.out.println("File not found: " + inputFilePath);
        } catch (IOException exception) {
            long processingTime = System.currentTimeMillis() - startTime;
            LoggerUtil.logProcessingResult(threadName, fileName, "FAILED - IO ERROR", lineCount, wordCount, processingTime);
            System.out.println("I/O error while processing " + inputFilePath + ": " + exception.getMessage());
        }
    }

    private int countWords(String line) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return 0;
        }

        return trimmedLine.split("\\s+").length;
    }
}
