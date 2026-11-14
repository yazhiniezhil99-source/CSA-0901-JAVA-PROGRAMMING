# TASK A2 – DON'T READ WHAT YOU DON'T NEED

## A2.1 – Predict the Cost

The attendance log has a size of 50 MB.

When FileInputStream.read() is used to read one byte at a time, the program makes approximately 50 × 1024 × 1024 = 52,428,800 read calls.

Each read operation involves method-call overhead and repeated low-level I/O operations. This makes the program slow because it processes only one byte per read call instead of reading multiple bytes together.

The repeated interaction with the operating system increases execution time, especially for large files.

## A2.2 – Redesign Using Buffering

The program can be redesigned using BufferedInputStream or a byte array buffer.

A buffer temporarily stores a group of bytes in memory. Instead of reading one byte at a time, the program reads a block of bytes and processes them together.

**Example Java code:**

```java
import java.io.*;

public class BufferedCounter {
    public static void main(String[] args) throws IOException {
        long lines = 0;

        try (BufferedInputStream in =
             new BufferedInputStream(
                 new FileInputStream("attendance.log"))) {

            int data;

            while ((data = in.read()) != -1) {
                if (data == '\n') {
                    lines++;
                }
            }
        }

        System.out.println("Total lines: " + lines);
    }
}
```

A BufferedInputStream uses an internal buffer, typically 8 KB by default. It reads a block of data into memory and serves subsequent read requests from that buffer.

For a 50 MB file with an 8 KB buffer, approximately 6,400 buffer refills are needed instead of approximately 52 million individual low-level reads.

This significantly reduces I/O overhead and improves performance.

## A2.3 – When Buffering Does Not Help

Buffering provides little benefit when the program reads a very small file only once, because the overhead of creating and managing the buffer may not significantly improve execution time.

It may also provide limited improvement when the program is waiting for real-time input rather than reading from a file.

## Alternative Concept and Complexity Analysis

### Using Files.readAllLines()

Instead of BufferedInputStream, the entire file can be read into memory using Java's Files.readAllLines() method.

**Example:**

```java
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class ReadAllLinesExample {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("attendance.log");

        List<String> lines = Files.readAllLines(path);

        System.out.println("Total lines: " + lines.size());
    }
}
```

### Complexity Comparison

Let n represent the file size in bytes.

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Unbuffered reading | O(n) | O(1) |
| Buffered reading | O(n) | O(b) |
| Files.readAllLines() | O(n) | O(n) |

Here, b represents the buffer size.

### Memory Analysis for a 50 MB File

- Unbuffered reading: Uses very little additional memory.
- Buffered reading: Uses approximately 8 KB for the default buffer.
- Files.readAllLines(): Stores all lines in memory, requiring memory proportional to the file size, plus String and list-object overhead.

### Comparison and Evaluation

Buffered reading is suitable for large attendance logs because it reduces the number of low-level read operations while using limited memory.

Files.readAllLines() is appropriate for smaller files when all lines need to be accessed repeatedly and sufficient memory is available.

For a 50 MB attendance log, buffered reading is the better choice because it provides efficient sequential processing without loading the entire file into memory.

### Conclusion

Buffering improves file-reading performance by reducing repeated I/O operations. For large files, buffered reading is more memory-efficient than loading the entire file at once.