# Multithreaded File Processing System

## Project Overview
This is a console-based Java application that processes multiple text files simultaneously using multithreading. Each file is handled by a separate thread, converted to uppercase, counted for lines and words, and written to a processed output file.

## Features
- Console-based Java application
- Multithreading using `Runnable` and `Thread`
- Reads input files with `BufferedReader`
- Writes output files with `BufferedWriter`
- Converts content to uppercase
- Counts lines and words for each file
- Synchronized logging to a shared log file
- Automatic sample file generation when input files are missing
- Clean exception handling with meaningful messages

## Technologies Used
- Java Core
- Java I/O
- Multithreading
- Buffered streams
- Synchronization
- Try-with-resources

## Folder Structure
```text
MultithreadedFileProcessing/
│
├── src/
│   ├── Main.java
│   ├── FileProcessor.java
│   ├── LoggerUtil.java
│
├── input/
│   ├── file1.txt
│   ├── file2.txt
│   ├── file3.txt
│
├── output/
│
├── logs/
│   └── processing_log.txt
│
└── README.md
```

## How to Run
### In IntelliJ IDEA, Eclipse, or VS Code
1. Open the `MultithreadedFileProcessing` folder as a Java project.
2. Ensure `src` is marked as the source folder if your IDE asks for it.
3. Run `Main.java`.
4. The application will create missing folders and sample input files automatically.

### Using the Command Line
From the project root, compile and run:
```bash
javac src/*.java
java -cp src Main
```

## Sample Output
When the application runs, you may see output like this:
```text
Starting Multithreaded File Processing System...
Processor-file1.txt is processing file1.txt
Processor-file2.txt is processing file2.txt
Processor-file3.txt is processing file3.txt
Processor-file1.txt completed file1.txt successfully.
Processor-file2.txt completed file2.txt successfully.
Processor-file3.txt completed file3.txt successfully.
All files have been processed successfully.
Processed files are available in the output/ folder.
Logs are available in logs/processing_log.txt
```

The `output/` folder will contain processed files such as:
- `processed_file1.txt`
- `processed_file2.txt`
- `processed_file3.txt`

## Future Enhancements
- Add support for processing more file formats
- Include a thread pool using `ExecutorService`
- Add richer statistics such as character counts and frequency analysis
- Provide a menu-driven console interface
- Store logs in CSV or JSON format for easier reporting
