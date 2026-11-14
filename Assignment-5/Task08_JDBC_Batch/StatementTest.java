
import java.sql.*;

public class StatementTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/persistence_db";
        String user = "root";
        String password = "yal@2025";

        String sql = "INSERT INTO bus_passes VALUES " +
                "('P201', 'REG201', 'Student A', 'R12', 1500.00)";

        try (Connection con =
                     DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {

            int rows = st.executeUpdate(sql);

            System.out.println("Statement insertion completed.");
            System.out.println("Rows inserted: " + rows);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}