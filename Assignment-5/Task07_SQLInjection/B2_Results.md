# B2 – SQL INJECTION AND DAMAGED FILE TESTING

## 1. SQL Injection Testing

The login program was tested using a crafted SQL input:

`' OR '1'='1' -- `

| Method | Result | Observation |
|---|---|---|
| Statement | Login successful | Vulnerable to SQL injection |
| PreparedStatement | Login failed | Injection prevented |

**Observation:** Statement concatenates user input directly into the SQL query. PreparedStatement uses parameterized queries and treats user input as data rather than executable SQL.

## 2. Damaged File Testing

The pass file was modified to contain incomplete records, invalid amounts and empty lines.

| Record | Condition | Result |
|---|---|---|
| P101 | Valid record | Accepted |
| P102 | Incomplete fields | Skipped |
| P103 | Invalid amount | Skipped |
| Empty line | Empty record | Skipped |
| P104 | Valid record | Accepted |

**Observation:** The PassStore program validates the number of fields and amount before displaying a record. Invalid records are safely skipped.

## Conclusion

The experiments demonstrate that PreparedStatement protects database queries against SQL injection, while file validation prevents damaged records from being processed as valid bus passes.