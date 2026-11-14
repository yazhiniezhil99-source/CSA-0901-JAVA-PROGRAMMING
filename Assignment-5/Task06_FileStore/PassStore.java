
import java.io.*;
import java.util.*;

public class PassStore {

    private final String fileName;

    public PassStore(String fileName) {
        this.fileName = fileName;
    }

    // Add a bus pass to the text file
    public void add(String passId, String regNo,
                    String name, String route, double amount) {

        String record = String.join(",",
                passId, regNo, name, route,
                String.valueOf(amount));

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName, true))) {

            writer.write(record);
            writer.newLine();

            System.out.println("Pass added successfully.");

        } catch (IOException e) {
            System.out.println("Error adding pass: " + e.getMessage());
        }
    }

    // Find a pass using its pass ID
    public String find(String searchId) {

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Pass file does not exist.");
            return null;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(",", -1);

                // Skip damaged records
                if (fields.length != 5) {
                    continue;
                }

                try {
                    Double.parseDouble(fields[4]);

                    if (fields[0].equals(searchId)) {
                        return line;
                    }

                } catch (NumberFormatException e) {
                    // Ignore records with invalid amounts
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return null;
    }

    // Display all valid bus passes
    public void list() {

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Pass file does not exist.");
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(",", -1);

                if (fields.length != 5) {
                    continue;
                }

                try {
                    Double.parseDouble(fields[4]);
                    System.out.println(line);

                } catch (NumberFormatException e) {
                    // Skip invalid amount
                }
            }

        } catch (IOException e) {
            System.out.println("Error listing passes: " + e.getMessage());
        }
    }
}
