# TASK A1.2 – DESIGN THE OPERATIONS

## 1. Add a New Bus Pass

**Algorithm: ADD_BUS_PASS**

**Input:** Pass ID, Register Number, Name, Route Number, Boarding Stop, Validity Date, Amount Paid.

**Output:** Confirmation message.

1. START
2. RECEIVE all bus-pass details.
3. VALIDATE the input fields.
4. OPEN the file using FileWriter in append mode.
5. WRAP FileWriter with BufferedWriter.
6. FORMAT the fields into one CSV record.
7. WRITE the record into the file.
8. WRITE a newline after the record.
9. CLOSE the BufferedWriter.
10. DISPLAY "Bus pass added successfully".
11. STOP.

## 2. Find a Bus Pass by Pass ID

**Algorithm: FIND_BUS_PASS**

**Input:** Pass ID to search.

**Output:** Matching bus-pass record or not-found message.

1. START
2. CHECK whether the file exists.
3. IF the file does not exist THEN
   - DISPLAY "File not found".
   - STOP.
4. OPEN the file using FileReader.
5. WRAP FileReader with BufferedReader.
6. READ each line until the end of the file.
7. SPLIT the line using a CSV parser.
8. IF the record is damaged THEN
   - SKIP the record.
   - CONTINUE reading.
9. IF the first field matches the requested Pass ID THEN
   - DISPLAY the matching record.
   - CLOSE the BufferedReader.
   - STOP.
10. CLOSE the BufferedReader.
11. DISPLAY "Pass ID not found".
12. STOP.

## 3. List All Passes on a Given Route

**Algorithm: LIST_PASSES_BY_ROUTE**

**Input:** Route Number.

**Output:** All valid bus-pass records belonging to the specified route.

1. START
2. CHECK whether the file exists.
3. IF the file does not exist THEN
   - DISPLAY "File not found".
   - STOP.
4. OPEN the file using FileReader.
5. WRAP FileReader with BufferedReader.
6. SET found = FALSE.
7. READ each line until the end of the file.
8. PARSE the current line using a CSV parser.
9. IF the record is damaged THEN
   - SKIP the record.
   - CONTINUE reading.
10. IF the route number matches the requested route THEN
    - DISPLAY the bus-pass record.
    - SET found = TRUE.
11. CLOSE the BufferedReader.
12. IF found = FALSE THEN
    - DISPLAY "No passes found for this route".
13. STOP.

## Stream Selection and Justification

| Stream Class | Purpose |
|---|---|
| FileReader | Reads character-based text data. |
| FileWriter | Writes character-based text data. |
| BufferedReader | Reads text efficiently, line by line. |
| BufferedWriter | Buffers output and reduces frequent write operations. |
| FileInputStream | Reads raw binary data. |
| FileOutputStream | Writes raw binary data. |

BufferedReader and BufferedWriter are selected for the text-file design because they improve efficiency by reducing frequent low-level I/O operations. FileInputStream and FileOutputStream are appropriate for binary files, not for directly processing CSV text.

## Complexity Analysis

Let n represent the total number of bus-pass records.

| Operation | Time Complexity | Reason |
|---|---|---|
| Add a new pass | O(1) amortized | Appends one record. |
| Find by Pass ID | O(n) | May scan all records. |
| List by Route | O(n) | Checks every record. |

The text-file design uses O(n) storage for all records. Finding and listing require sequential scanning.