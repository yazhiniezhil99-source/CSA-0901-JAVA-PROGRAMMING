
import java.io.*;

public class SearchBenchmark {

    public static void main(String[] args) throws IOException {

        int[] sizes = {1000, 10000, 100000};

        for (int size : sizes) {

            String fileName = "search" + size + ".txt";

            // Generate test records
            try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

                for (int i = 1; i <= size; i++) {
                    writer.write(
                        "P" + i + ",REG" + i +
                        ",Student" + i + ",R12,1500.0"
                    );
                    writer.newLine();
                }
            }

            PassStore store = new PassStore(fileName);

            String target = "P" + size;

            // Warm-up search
            store.find(target);

            // Measure 5 searches
            long start = System.nanoTime();

            for (int i = 0; i < 5; i++) {
                store.find(target);
            }

            long end = System.nanoTime();

            double averageMs =
                (end - start) / 5.0 / 1_000_000.0;

            System.out.println("Records: " + size);
            System.out.println("Search ID: " + target);
            System.out.println(
                "Average search time: " + averageMs + " ms"
            );
            System.out.println("-------------------------");
        }
    }
}
