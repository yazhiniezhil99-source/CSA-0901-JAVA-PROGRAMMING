package service;

import dao.RegistrationDAO;

public class RegistrationService {

    private final RegistrationDAO registrationDAO;

    public RegistrationService() {
        registrationDAO = new RegistrationDAO();
    }

    public String registerStudent(String studentId,
                                  String studentName,
                                  String email,
                                  int eventId) {

        if (studentId == null || studentId.trim().isEmpty()) {
            return "Please enter Student ID.";
        }

        if (studentName == null || studentName.trim().isEmpty()) {
            return "Please enter Student Name.";
        }

        if (email == null || email.trim().isEmpty()) {
            return "Please enter Email Address.";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return "Please enter a valid email address.";
        }

        if (eventId <= 0) {
            return "Please select an event.";
        }

        return registrationDAO.registerStudent(
                studentId.trim(),
                studentName.trim(),
                email.trim(),
                eventId
        );
    }
}