# TASK A4 – SERIALIZATION AND OBJECT PERSISTENCE

## A4.1 – Serialization Design

Serialization is the process of converting a Java object into a byte stream so that it can be stored in a file or transferred. Deserialization converts the stored byte stream back into an object.

Java provides the Serializable interface for this purpose.

### Example: Resident Class

```java
import java.io.Serializable;

public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    private int residentId;
    private String name;
    private String roomNumber;

    private transient String password;

    public Resident(int residentId, String name,
                    String roomNumber, String password) {
        this.residentId = residentId;
        this.name = name;
        this.roomNumber = roomNumber;
        this.password = password;
    }
}
```

### Serialization

```java
import java.io.*;

public class SaveResident {

    public static void main(String[] args) {

        Resident resident =
            new Resident(101, "Resident A", "R12", "secret");

        try (ObjectOutputStream out =
                 new ObjectOutputStream(
                     new FileOutputStream("resident.dat"))) {

            out.writeObject(resident);
            System.out.println("Object serialized successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Deserialization

```java
import java.io.*;

public class ReadResident {

    public static void main(String[] args) {

        try (ObjectInputStream in =
                 new ObjectInputStream(
                     new FileInputStream("resident.dat"))) {

            Resident resident = (Resident) in.readObject();

            System.out.println("Object deserialized successfully.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

## A4.2 – Handling Transient and Sensitive Fields

The transient keyword prevents a field from being included in the default Java serialization process.

In the Resident class, the password field is declared transient because sensitive credentials should not be stored in a serialized object file.

After deserialization, a transient String field receives its default value, which is null.

### Why Use transient?

- Prevents sensitive fields from being serialized.
- Avoids storing temporary or runtime-specific information.
- Helps prevent unnecessary object data from being saved.

Passwords should be stored separately using secure password hashing rather than plain text.

### Handling Database Connections

A database Connection should not be serialized because it represents a live resource connected to a database.

If a class contains a Connection field, it should be declared transient. The application should establish a new database connection when required after deserialization.

## A4.3 – Object Graph and Compatibility

An object graph is a group of objects connected through references.

For example, a Room object may contain a list of Resident objects. Serializing the Room object can also serialize its referenced Resident objects if they are serializable.

### Example Object Structure

```text
Room
 |
 |--- roomNumber
 |
 |--- List<Resident>
          |
          |--- Resident 1
          |--- Resident 2
          |--- Resident 3
```

All objects that need to be serialized through this object graph must implement Serializable.

### Role of serialVersionUID

The serialVersionUID identifies the version of a serializable class.

```java
private static final long serialVersionUID = 1L;
```

It helps Java check compatibility between the class used to serialize an object and the class used to deserialize it.

If the class changes incompatibly and its serialVersionUID does not match, Java may throw an InvalidClassException.

### Serialization Limitations

- Changes to class structure may affect compatibility.
- Large object graphs may consume considerable storage.
- Sensitive data must be excluded or protected.
- Serialized files should only be read from trusted sources because Java deserialization can create security risks.

## Alternative: JSON Persistence

Instead of Java serialization, an application can store objects in JSON format using a JSON library.

| Factor | Java Serialization | JSON |
|---|---|---|
| Format | Binary | Text |
| Human readability | Not easily readable | Easily readable |
| Java-specific | Yes | No |
| Cross-language support | Limited | High |
| Version compatibility | Requires care | Can be managed through optional fields and schema rules |
| Storage | Often compact | Can be larger |

### Evaluation

Java serialization is convenient when saving and restoring Java object graphs. JSON is preferable when data must be readable, exchanged with other applications or supported across programming languages.

### Conclusion

Serialization provides a way to persist Java objects. Using transient for sensitive or runtime-specific fields, maintaining serialVersionUID and validating serialized data helps improve security and compatibility.