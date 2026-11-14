package model;

import java.time.LocalDateTime;

public class RegistrationRecord {

    private int registrationId;
    private String studentId;
    private String studentName;
    private String email;
    private String eventName;
    private LocalDateTime registrationDate;

    public RegistrationRecord(int registrationId, String studentId,
                              String studentName, String email,
                              String eventName,
                              LocalDateTime registrationDate) {

        this.registrationId = registrationId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.eventName = eventName;
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

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}