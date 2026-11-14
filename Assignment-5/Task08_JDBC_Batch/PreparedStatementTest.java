
import java.sql.*;

public class PreparedStatementTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/persistence_db";
        String user = "root";
        String password = "yal@2025";

        String sql = "INSERT INTO bus_passes " +
                "(pass_id, student_id, student_name, route, amount) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "P202");
            ps.setString(2, "REG202");
            ps.setString(3, "Student B");
            ps.setString(4, "R15");
            ps.setDouble(5, 1800.00);

            int rows = ps.executeUpdate();

            System.out.println("PreparedStatement insertion completed.");
            System.out.println("Rows inserted: " + rows);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
