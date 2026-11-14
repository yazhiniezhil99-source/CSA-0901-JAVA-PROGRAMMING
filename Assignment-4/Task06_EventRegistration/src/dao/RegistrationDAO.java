package dao;

import model.RegistrationRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public String registerStudent(String studentId,
                                  String studentName,
                                  String email,
                                  int eventId) {

        String checkSql = "SELECT capacity, registered_count FROM events WHERE event_id = ? FOR UPDATE";
        String insertSql = "INSERT INTO registrations (student_id, student_name, email, event_id) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE events SET registered_count = registered_count + 1 WHERE event_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement check = con.prepareStatement(checkSql)) {
                    check.setInt(1, eventId);

                    try (ResultSet rs = check.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return "Event not found.";
                        }

                        if (rs.getInt("registered_count") >= rs.getInt("capacity")) {
                            con.rollback();
                            return "Sorry, this event is full.";
                        }
                    }
                }

                try (PreparedStatement insert = con.prepareStatement(insertSql)) {
                    insert.setString(1, studentId);
                    insert.setString(2, studentName);
                    insert.setString(3, email);
                    insert.setInt(4, eventId);
                    insert.executeUpdate();
                }

                try (PreparedStatement update = con.prepareStatement(updateSql)) {
                    update.setInt(1, eventId);
                    update.executeUpdate();
                }

                con.commit();
                return "Registration successful!";

            } catch (SQLException e) {
                con.rollback();

                if (e.getErrorCode() == 1062) {
                    return "You have already registered for this event.";
                }

                throw e;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }

    // View and search registrations with pagination
    public List<RegistrationRecord> getRegistrations(
            String keyword, int limit, int offset) throws SQLException {

        List<RegistrationRecord> records = new ArrayList<>();

        String sql = """
                SELECT r.registration_id, r.student_id,
                       r.student_name, r.email,
                       e.event_name, r.registration_date
                FROM registrations r
                JOIN events e ON r.event_id = e.event_id
                WHERE r.student_id LIKE ?
                   OR r.student_name LIKE ?
                   OR r.email LIKE ?
                   OR e.event_name LIKE ?
                ORDER BY r.registration_id DESC
                LIMIT ? OFFSET ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setInt(5, limit);
            ps.setInt(6, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(new RegistrationRecord(
                            rs.getInt("registration_id"),
                            rs.getString("student_id"),
                            rs.getString("student_name"),
                            rs.getString("email"),
                            rs.getString("event_name"),
                            rs.getTimestamp("registration_date").toLocalDateTime()
                    ));
                }
            }
        }

        return records;
    }

    // Count registrations for pagination
    public int getTotalRegistrations(String keyword) throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM registrations r
                JOIN events e ON r.event_id = e.event_id
                WHERE r.student_id LIKE ?
                   OR r.student_name LIKE ?
                   OR r.email LIKE ?
                   OR e.event_name LIKE ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            for (int i = 1; i <= 4; i++) {
                ps.setString(i, search);
            }

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }
}