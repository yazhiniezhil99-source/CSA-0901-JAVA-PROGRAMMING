# B3 – JDBC DATABASE OPERATIONS

## 1. Statement Insertion

A bus-pass record was inserted using the Statement interface.

**Result:** One record inserted successfully.

## 2. PreparedStatement Insertion

A record was inserted using parameterized SQL through PreparedStatement.

**Result:** One record inserted successfully.

## 3. Batch Insertion

Three bus-pass records were inserted using addBatch() and executeBatch().

**Result:** Three records processed successfully.

## 4. Database Verification

The bus_passes table was queried to verify the inserted records.

**Result:** Five records were displayed successfully.

## 5. Transaction Rollback

A temporary record P206 was inserted inside a transaction. The rollback() method was executed.

**Result:** P206 was not stored in the database after rollback. The verification query returned zero records.

## Conclusion

JDBC provides database connectivity through Java. PreparedStatement improves SQL security, batch processing supports multiple insertions efficiently, and transactions maintain data consistency through commit and rollback operations.