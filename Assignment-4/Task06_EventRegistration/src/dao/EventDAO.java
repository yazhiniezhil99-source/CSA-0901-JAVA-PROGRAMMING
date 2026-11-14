package dao;

import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public List<Event> getAllEvents() throws SQLException {

        List<Event> events = new ArrayList<>();

        String sql = "SELECT * FROM events ORDER BY event_date";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getDate("event_date").toLocalDate(),
                    rs.getString("venue"),
                    rs.getInt("capacity"),
                    rs.getInt("registered_count")
                );

                events.add(event);
            }
        }

        return events;
    }
}