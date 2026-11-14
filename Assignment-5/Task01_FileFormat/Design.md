# TASK A1 – DESIGN A FILE FORMAT

## A1.1 – Represent the Record

The transport office maintains approximately 8,000 bus-pass records every year. Each record contains seven fields.

### (a) Delimited Text File Format

File name: `bus_passes.csv`

**Field order:**

| Position | Field | Data Type |
|---|---|---|
| 1 | Pass ID | String |
| 2 | Register Number | String |
| 3 | Student Name | String |
| 4 | Route Number | Integer |
| 5 | Boarding Stop | String |
| 6 | Validity Date | Date (YYYY-MM-DD) |
| 7 | Amount Paid | Double |

**Delimiter:** Comma (,)

Example record:

`P1001,192311001,Arun,12,Central Stop,2027-03-31,4500.00`

If a student name contains a comma, it will be enclosed in double quotes. Embedded double quotes will be escaped by doubling them. A CSV parser will be used to handle quoted fields correctly.

### (b) Binary File Format using DataOutputStream

File name: `bus_passes.dat`

Fields will be written in the following order:

| Field | Binary Representation |
|---|---|
| Pass ID | UTF string |
| Register Number | UTF string |
| Student Name | UTF string |
| Route Number | 4-byte integer |
| Boarding Stop | UTF string |
| Validity Date | UTF string (YYYY-MM-DD) |
| Amount Paid | 8-byte double |

`DataOutputStream` writes primitive values and UTF strings in binary format. `DataInputStream` reads the values in the same order.

Unlike a delimited text file, binary records do not require delimiter handling.

## A1.3 – Design for Damaged Data

### 1. Damaged Line
Each text record will be checked for the correct number of fields and valid data types. Records containing missing fields, invalid numbers or invalid dates will be skipped and reported in an error log. Valid records will continue to be processed.

### 2. Missing File
Before reading, the program will check whether the file exists using the Java `File` class. If the file is missing, the program will display an appropriate message. When adding a new pass, the file will be created automatically.

### 3. Incomplete Write Due to Power Failure
The program will write new records to a temporary file first and then replace the original file after a successful write. It will flush buffered data and close the stream properly. A backup copy can be maintained to recover the previous valid data if a failure occurs during replacement.

An incomplete final record can also be detected during reading and reported without stopping the processing of other valid records.

## Alternative Concept and Complexity Analysis

### RandomAccessFile with Fixed-Length Records

Instead of a delimited text file, the records can be stored using `RandomAccessFile`. Every record will have a fixed record size. Variable-length fields such as names and boarding stops will be allocated fixed character spaces and padded when necessary.

The record at index `i` can be accessed using:

`seek(i * recordSize)`

**Time Complexity:**

- Sequential search in a text file: O(n)
- Direct access using record index: O(1)

However, finding a pass by ID still requires O(n) time unless an index mapping pass IDs to record positions is maintained.

**Space Complexity:**

- Text file: Storage depends on the actual field lengths.
- Fixed-length binary file: O(n × recordSize).

Fixed-length storage may waste space because shorter names and stops occupy the same allocated space as longer values.

### Conclusion

A delimited text file is suitable for the transport office because approximately 8,000 records can be stored and processed easily. It is readable and simple to maintain. RandomAccessFile is useful when direct record access is required, but fixed-length fields may waste storage space.