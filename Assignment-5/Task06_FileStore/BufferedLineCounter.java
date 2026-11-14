
import java.io.*;

public class BufferedLineCounter {

    public static void main(String[] args) {

        String fileName = args.length > 0
                ? args[0] : "attendance.txt";

        long bytes = 0;
        long lines = 0;
        int lastByte = -1;

        long startTime = System.nanoTime();

        try (BufferedInputStream input =
                 new BufferedInputStream(new FileInputStream(fileName))) {

            int data;

            while ((data = input.read()) != -1) {
                bytes++;
                lastByte = data;

                if (data == '\n') {
                    lines++;
                }
            }

            if (bytes > 0 && lastByte != '\n') {
                lines++;
            }

            long endTime = System.nanoTime();
            double timeMs = (endTime - startTime) / 1_000_000.0;

            System.out.println("File: " + fileName);
            System.out.println("Total bytes: " + bytes);
            System.out.println("Total lines: " + lines);
            System.out.println("Reading time: " + timeMs + " ms");

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
