# B4 – FINAL STORAGE STRATEGY AND MIGRATION DECISION

## 1. Storage Alternatives

| Storage Method | Advantages | Limitations |
|---|---|---|
| CSV/Text Files | Simple, readable and easy to edit | Slow searching and limited concurrency |
| Binary Files | Compact storage and faster structured access | Difficult to inspect and maintain |
| Relational Database | Supports indexing, transactions, security and concurrent access | Requires database setup and management |

## 2. Selected Storage Strategy

A relational database using MySQL is selected as the primary storage method for the bus-pass management system.

The bus_passes table stores pass ID, student ID, student name, route and amount.

PreparedStatement is used for database operations to prevent SQL injection. Transactions are used to maintain consistency, and batch processing supports multiple record insertions.

## 3. Migration Plan

The existing text-based bus-pass records can be migrated to MySQL using the following steps:

1. Read the existing passes.txt file.
2. Parse each record into its individual fields.
3. Validate that every record contains five fields.
4. Check that the amount is a valid numeric value.
5. Skip incomplete or damaged records.
6. Insert valid records into the bus_passes table using PreparedStatement.
7. Use a database transaction to commit valid records.
8. Verify the number of successfully migrated records.

## 4. Data Integrity and Recovery

- Primary key constraints prevent duplicate pass IDs.
- NOT NULL constraints prevent missing required values.
- PreparedStatement protects SQL operations from injection.
- Transactions allow failed migrations to be rolled back.
- Original files should be retained until migration is verified.
- Database backups should be maintained for recovery.

## 5. Final Decision

The file-based approach is suitable for small applications and simple storage requirements. However, as the number of bus-pass records and users increases, file searching, concurrent updates and data consistency become difficult to manage.

Therefore, MySQL is selected as the primary storage solution. Text files may still be used for importing, exporting and backup purposes.

## Conclusion

The experiments demonstrate the advantages and limitations of file handling and database persistence. A database-based approach provides better data integrity, secure query execution, transaction management and scalability for the bus-pass management system.