package ui;

import dao.DBConnection;
import java.sql.Connection;

public class ConnectionTest {

    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.println("MySQL connection successful!");
            System.out.println("Database: " +
                    con.getCatalog());
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}