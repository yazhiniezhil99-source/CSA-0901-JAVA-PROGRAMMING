CREATE DATABASE IF NOT EXISTS event_registration_db;

USE event_registration_db;

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    venue VARCHAR(150) NOT NULL,
    capacity INT NOT NULL,
    registered_count INT DEFAULT 0
);

CREATE TABLE registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(30) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    event_id INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
        REFERENCES events(event_id),

    CONSTRAINT unique_student_event
        UNIQUE (student_id, event_id)
);

INSERT INTO events (event_name, event_date, venue, capacity)
VALUES
('Java Programming Workshop', '2026-12-10', 'Computer Lab 1', 50),
('Web Development Seminar', '2026-12-12', 'Seminar Hall', 100),
('AI and Machine Learning', '2026-12-15', 'Auditorium', 75),
('Cyber Security Awareness', '2026-12-18', 'Computer Lab 2', 60);