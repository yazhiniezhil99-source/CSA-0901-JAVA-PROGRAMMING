package model;

import java.time.LocalDateTime;

public class Registration {

    private int registrationId;
    private String studentId;
    private String studentName;
    private String email;
    private int eventId;
    private LocalDateTime registrationDate;

    public Registration(int registrationId, String studentId,
                        String studentName, String email,
                        int eventId, LocalDateTime registrationDate) {

        this.registrationId = registrationId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public int getEventId() {
        return eventId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}