# B1 – EXPERIMENTAL RESULTS

## Experiment 1: File Reading Performance

The BufferedLineCounter program was tested with three different file sizes.

| File Size | Bytes | Lines | Reading Time |
|---|---:|---:|---:|
| 1 MB | 1,048,576 | 1,024 | 32.1042 ms |
| 10 MB | 10,485,760 | 10,240 | 226.4294 ms |
| 50 MB | 52,428,800 | 51,200 | 1128.7316 ms |

### Observation

As file size increases, the reading time also increases. Buffered input reduces frequent low-level file access by reading data in blocks.

## Experiment 2: Search Performance

The PassStore program was tested with different numbers of records. The search was performed for the last record, and the average time was calculated over five searches.

| Number of Records | Search ID | Average Search Time |
|---:|---|---:|
| 1,000 | P1000 | 3.00224 ms |
| 10,000 | P10000 | 11.45882 ms |
| 100,000 | P100000 | 45.45874 ms |

### Observation

Search time increases as the number of records increases because the program scans the file sequentially. The search operation has O(n) time complexity.

## Conclusion

The experiments demonstrate that buffered file reading can handle large files, while sequential searching becomes slower as the number of records increases. For larger datasets, database indexing can provide more efficient searching.