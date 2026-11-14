# TASK A5 – TRANSACTIONS AND SQL INJECTION

## A5.1 – Wallet Transfer Transaction

A wallet transfer involves deducting money from one account and adding it to another account.

Both operations must succeed together. If either operation fails, the entire transaction must be rolled back.

### Transaction Steps

1. Start a database transaction.
2. Check whether the sender has sufficient balance.
3. Deduct the transfer amount from the sender.
4. Add the amount to the receiver.
5. Commit the transaction if both updates succeed.
6. Roll back if any operation fails.

### Java JDBC Example

```java
import java.sql.*;

public class WalletTransfer {

    public static void transfer(
            Connection con,
            int senderId,
            int receiverId,
            double amount) throws SQLException {

        if (amount <= 0 || senderId == receiverId) {
            throw new IllegalArgumentException("Invalid transfer");
        }

        boolean oldAutoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            String debit =
                "UPDATE wallet SET balance = balance - ? " +
                "WHERE wallet_id = ? AND balance >= ?";

            try (PreparedStatement ps =
                     con.prepareStatement(debit)) {

                ps.setDouble(1, amount);
                ps.setInt(2, senderId);
                ps.setDouble(3, amount);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException(
                        "Insufficient balance or invalid sender");
                }
            }

            String credit =
                "UPDATE wallet SET balance = balance + ? " +
                "WHERE wallet_id = ?";

            try (PreparedStatement ps =
                     con.prepareStatement(credit)) {

                ps.setDouble(1, amount);
                ps.setInt(2, receiverId);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Invalid receiver");
                }
            }

            con.commit();

        } catch (SQLException | RuntimeException e) {

            try {
                con.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            throw e;

        } finally {
            try {
                con.setAutoCommit(oldAutoCommit);
            } catch (SQLException e) {
                // Connection cleanup should be handled by the caller.
                throw e;
            }
        }
    }
}
```

### Why Transactions Are Required

Without a transaction, the sender's money could be deducted even if the receiver's account update fails.

Transactions follow the ACID properties:

| Property | Meaning |
|---|---|
| Atomicity | Both updates succeed or neither succeeds |
| Consistency | Database rules remain valid |
| Isolation | Concurrent transactions do not interfere incorrectly |
| Durability | Committed changes remain saved |

## A5.2 – SQL Injection Vulnerability

SQL injection occurs when user input is directly combined with an SQL query. An attacker may enter SQL commands instead of normal input.

### Vulnerable Login Query

```java
String sql =
    "SELECT * FROM users WHERE username = '" +
    username + "' AND password = '" + password + "'";

Statement st = con.createStatement();
ResultSet rs = st.executeQuery(sql);
```

The query is unsafe because user input becomes part of the SQL command.

### Example Attack Input

Username:

```text
' OR '1'='1' --
```

The attacker attempts to change the meaning of the WHERE condition and bypass authentication.

The exact result depends on the database query and SQL syntax.

### Secure Login Using PreparedStatement

```java
String sql =
    "SELECT * FROM users WHERE username = ? AND password_hash = ?";

try (PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setString(1, username);
    ps.setString(2, passwordHash);

    try (ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            System.out.println("User authenticated");
        } else {
            System.out.println("Invalid login");
        }
    }
}
```

In a real application, the supplied password must be checked against a securely stored password hash using a suitable password-hashing algorithm. It should not be compared as plain text.

### Why PreparedStatement Is Safer

- SQL structure is defined separately from user input.
- Parameters are treated as data rather than SQL commands.
- It reduces the risk of SQL injection.
- It supports reusable parameterized queries.

## A5.3 – Comparison of Approaches

| Factor | Statement | PreparedStatement |
|---|---|---|
| Query construction | SQL string | SQL with placeholders |
| User input | May be concatenated | Bound as parameters |
| SQL injection | Vulnerable if input is concatenated | Strong protection for parameter values |
| Reusability | Less convenient | Convenient |
| Performance | Suitable for simple one-time SQL | Can reuse prepared queries |

### Alternative: Input Validation and Escaping

Input validation can reject unexpected characters or enforce formats. Escaping attempts to neutralize special characters.

However, validation and escaping alone are not reliable replacements for parameterized queries. PreparedStatement should be the primary protection.

### Conclusion

Transactions protect database consistency during multi-step operations such as wallet transfers. PreparedStatement prevents user input from being interpreted as SQL syntax. Combining transaction management, parameterized queries and secure password handling improves application reliability and security.