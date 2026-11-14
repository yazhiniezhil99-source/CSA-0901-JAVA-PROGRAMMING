# STUDENT DETAILS

**Name:** YAZHINI.E.M

**Register Number:** 192511165

**Course Name:** Java Programming

**Course Code:** CSA0901

---

# Java Persistence Engineering – Assignment 5

## Project Overview

This project demonstrates Java data persistence techniques using file handling, serialization, DAO architecture, JDBC and MySQL database operations.

The project uses a bus-pass management system as the practical example for file storage and database persistence.

## Technologies Used

- Java 21
- MySQL 8.0.44
- JDBC
- MySQL Connector/J 9.7.0
- VS Code
- PowerShell

## Project Structure

```text
Java-Persistence-Assignment5/
│
├── Task01_FileFormat/
├── Task02_BufferedIO/
├── Task03_DAO/
├── Task04_Serialization/
├── Task05_Transactions/
├── Task06_FileStore/
├── Task07_SQLInjection/
├── Task08_JDBC_Batch/
├── Task09_FinalDecision/
│
├── lib/
│   └── mysql-connector-j-9.7.0.jar
│
├── screenshots/
│
└── README.md
```

## Part A – Design and Concepts

| Task | Topic |
|---|---|
| A1 | File Format Design |
| A2 | Buffered I/O and Performance |
| A3 | Data Access Object Pattern |
| A4 | Serialization and Deserialization |
| A5 | Transactions and SQL Injection |

## Part B – Implementation and Experiments

| Task | Topic |
|---|---|
| B1 | File Storage and Performance Testing |
| B2 | SQL Injection and Damaged File Testing |
| B3 | JDBC Database Operations |
| B4 | Final Storage Strategy and Migration Decision |

## Database Setup

Database name: `persistence_db`

Tables:

- `users` – Used for SQL injection demonstration.
- `bus_passes` – Stores bus-pass records.

The `bus_passes` table contains:

- `pass_id`
- `student_id`
- `student_name`
- `route`
- `amount`

## Experiments Performed

1. File reading with 1 MB, 10 MB and 50 MB files.
2. Sequential searching with 1,000, 10,000 and 100,000 records.
3. Detection of incomplete and damaged file records.
4. Comparison of Statement and PreparedStatement.
5. JDBC single-record insertion.
6. JDBC batch insertion.
7. Transaction rollback testing.
8. Database record verification.

## Final Conclusion

This assignment demonstrates the differences between file-based persistence and database-based persistence. File handling is suitable for simple storage requirements, while MySQL provides better support for structured data, concurrent access, indexing, security and transaction management.

The experiments demonstrate the importance of selecting an appropriate persistence method based on application requirements.