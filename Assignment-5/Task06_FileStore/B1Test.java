
public class B1Test {

    public static void main(String[] args) {

        PassStore store = new PassStore("passes.txt");

        store.add("P101", "REG101", "Student A", "R12", 1500);
        store.add("P102", "REG102", "Student B", "R15", 1800);
        store.add("P103", "REG103", "Student C", "R20", 2000);

        System.out.println("\nAll Bus Passes:");
        store.list();

        System.out.println("\nSearching for P102:");

        String result = store.find("P102");

        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Pass not found.");
        }

        System.out.println("\nSearching for P999:");

        result = store.find("P999");

        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Pass not found.");
        }
    }
}
