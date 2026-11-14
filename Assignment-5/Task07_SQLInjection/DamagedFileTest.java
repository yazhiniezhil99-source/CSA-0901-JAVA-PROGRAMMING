
import java.io.*;

public class DamagedFileTest {

    public static void main(String[] args) {

        String fileName = "damaged_passes.txt";

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            writer.write("P101,REG101,Student A,R12,1500.0");
            writer.newLine();

            writer.write("P102,REG102,Student B");
            writer.newLine();

            writer.write("P103,REG103,Student C,R20,invalid");
            writer.newLine();

            writer.newLine();

            writer.write("P104,REG104,Student D,R15,1800.0");
            writer.newLine();

            System.out.println("Damaged test file created.");

        } catch (IOException e) {
            System.out.println("File creation error: " + e.getMessage());
            return;
        }

        PassStore store = new PassStore(fileName);

        System.out.println("\nReading valid records:");
        store.list();

        System.out.println("\nSearching for P103:");
        String result = store.find("P103");

        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Invalid or missing record skipped.");
        }

        System.out.println("\nSearching for P104:");
        result = store.find("P104");

        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Pass not found.");
        }
    }
}
