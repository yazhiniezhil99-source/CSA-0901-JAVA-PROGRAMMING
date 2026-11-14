
import java.sql.*;

public class SQLInjectionTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/persistence_db";
        String user = "root";
        String password = "yal@2025";

        String username = "' OR '1'='1' -- ";
        String inputPassword = "anything";

        try (Connection con =
                 DriverManager.getConnection(url, user, password)) {

            System.out.println("Database connected successfully.");

            String unsafeQuery =
                "SELECT * FROM users WHERE username = '"
                + username + "' AND password = '"
                + inputPassword + "'";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(unsafeQuery)) {

                if (rs.next()) {
                    System.out.println(
                        "Statement: Login successful (vulnerable)"
                    );
                } else {
                    System.out.println("Statement: Login failed");
                }
            }

            String safeQuery =
                "SELECT * FROM users WHERE username = ? AND password = ?";

            try (PreparedStatement ps =
                     con.prepareStatement(safeQuery)) {

                ps.setString(1, username);
                ps.setString(2, inputPassword);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                            "PreparedStatement: Login successful"
                        );
                    } else {
                        System.out.println(
                            "PreparedStatement: Login failed (injection prevented)"
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
