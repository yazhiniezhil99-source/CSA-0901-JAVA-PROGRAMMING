# TASK A3 – DISCOVER THE DATA-ACCESS DESIGN PATTERN

## A3.1 – Identify the Common Pattern

The three classes StudentDB, CourseDB and MarksDB perform similar database operations.

### Repeated Steps

1. Open a database connection.
2. Construct an SQL query.
3. Execute the SQL statement.
4. Read the ResultSet.
5. Convert database rows into Java objects.
6. Close the ResultSet, Statement and Connection.

### Parts That Differ

- Table names.
- Column names.
- Java model classes.
- SQL parameters.
- Object mapping logic.

The repeated operations indicate that a common Data Access Object (DAO) pattern can be used.

## A3.2 – Design a Generic Data-Access Interface

The DAO pattern separates database operations from business logic. A generic interface allows different entity classes to use the same database operation structure.

### Generic DAO Interface

```java
import java.util.List;
import java.util.Optional;

public interface Dao<T, K> {

    void insert(T entity);

    Optional<T> findById(K id);

    List<T> findAll();

    void update(T entity);

    void delete(K id);
}
```

Here:

- T represents the entity type.
- K represents the primary key type.
- Optional is used to handle records that may not exist.

### Student Class

```java
public class Student {

    private int studentId;
    private String name;
    private String email;

    public Student(int studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

### StudentDao Implementation

```java
import java.sql.*;
import java.util.*;

public class StudentDao implements Dao<Student, Integer> {

    private final Connection con;

    public StudentDao(Connection con) {
        this.con = con;
    }

    @Override
    public void insert(Student s) {
        String sql = "INSERT INTO student VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        String sql = "SELECT * FROM student WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    @Override
    public void update(Student s) {
        String sql = "UPDATE student SET name=?, email=? WHERE student_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setInt(3, s.getStudentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM student WHERE student_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```

## A3.3 – Map Objects to Tables

The Student class represents one row in the student database table.

| Student Class Field | Database Column | Data Type | Constraint |
|---|---|---|---|
| studentId | student_id | INT | Primary Key |
| name | name | VARCHAR(100) | NOT NULL |
| email | email | VARCHAR(100) | UNIQUE |

### SQL Table

```sql
CREATE TABLE student (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
);
```

The studentId field is mapped to the student_id primary key. Each Student object corresponds to one row in the table.

## Alternative Concept and Complexity Analysis

### SQL Directly Inside Service or GUI Classes

An alternative is to write SQL queries directly inside service or GUI classes rather than using a generic DAO interface.

For example:

```java
String sql = "SELECT * FROM student WHERE student_id = ?";
PreparedStatement ps = con.prepareStatement(sql);
ps.setInt(1, studentId);
ResultSet rs = ps.executeQuery();
```

This approach mixes database access with application logic.

### Comparison

| Factor | Generic DAO | Direct SQL |
|---|---|---|
| Code organization | Centralized | Scattered |
| Reusability | High | Low |
| Maintenance | Easier | Difficult |
| Testing | Easier | More difficult |
| Database changes | Mostly DAO changes | Multiple classes may change |

### Time Complexity

For n database records:

- Find by primary key with an appropriate database index: O(log n) for a typical B-tree index.
- Find all records: O(n).
- Insert, update and delete: Depend on database indexes and constraints.

Both approaches execute essentially the same SQL operations. Therefore, their database operation time complexities are generally the same.

### Space Complexity

Both approaches use O(1) auxiliary space for a single-record lookup, excluding database-driver and result-object overhead.

For findAll(), storing all returned objects requires O(n) space.

### Evaluation

The DAO pattern is preferable because database operations are separated from business logic. If a table column is renamed, the DAO implementation can be updated centrally instead of modifying SQL statements scattered throughout multiple classes.

### Conclusion

The DAO pattern improves maintainability, reusability and separation of concerns without significantly changing the database operation's time and space complexity.