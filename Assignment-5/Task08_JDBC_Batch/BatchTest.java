
import java.sql.*;

public class BatchTest {

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

            con.setAutoCommit(false);

            ps.setString(1, "P203");
            ps.setString(2, "REG203");
            ps.setString(3, "Student C");
            ps.setString(4, "R20");
            ps.setDouble(5, 2000.00);
            ps.addBatch();

            ps.setString(1, "P204");
            ps.setString(2, "REG204");
            ps.setString(3, "Student D");
            ps.setString(4, "R12");
            ps.setDouble(5, 1600.00);
            ps.addBatch();

            ps.setString(1, "P205");
            ps.setString(2, "REG205");
            ps.setString(3, "Student E");
            ps.setString(4, "R15");
            ps.setDouble(5, 1900.00);
            ps.addBatch();

            int[] results = ps.executeBatch();
            con.commit();

            System.out.println("Batch insertion completed.");
            System.out.println("Records processed: " + results.length);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
