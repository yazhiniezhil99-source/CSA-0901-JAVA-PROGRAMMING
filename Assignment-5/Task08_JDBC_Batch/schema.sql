
USE persistence_db;

CREATE TABLE IF NOT EXISTS bus_passes (
    pass_id VARCHAR(20) PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    route VARCHAR(30) NOT NULL,
    amount DECIMAL(10,2) NOT NULL
);

SELECT * FROM bus_passes;
