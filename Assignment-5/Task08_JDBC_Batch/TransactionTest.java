
import java.sql.*;

public class TransactionTest {

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

            ps.setString(1, "P206");
            ps.setString(2, "REG206");
            ps.setString(3, "Student F");
            ps.setString(4, "R20");
            ps.setDouble(5, 2100.00);

            ps.executeUpdate();

            System.out.println("Record inserted temporarily.");

            con.rollback();

            System.out.println("Transaction rolled back.");

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                     "SELECT COUNT(*) FROM bus_passes WHERE pass_id='P206'")) {

                if (rs.next()) {
                    System.out.println(
                        "P206 records after rollback: " + rs.getInt(1)
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

