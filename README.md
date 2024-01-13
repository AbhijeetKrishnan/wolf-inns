# Wolf Inns

A hotel management system built using Java and MySQL (MariaDB) for the CSC 540 Database
Management Systems course taught in Spring 2018 by [Dr. Rada Chirkova](https://www.csc.ncsu.edu/people/rychirko).

## Compiling

```bash
javac -cp :lib/*: -Xlint:unchecked *.java
```

## Running

```bash
java -cp :lib/*: WolfInns
```

Run tests with the command -
```bash
java -cp :lib/*: TestRunner
```

We run the tests using JUnit 4.12