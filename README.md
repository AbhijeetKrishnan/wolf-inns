# Wolf Inns

A hotel management system built using Java and MySQL (MariaDB) for the CSC 540 Database
Management Systems course taught in Spring 2018 by [Dr. Rada Chirkova](https://www.csc.ncsu.edu/people/rychirko).

Migrated to a Gradle v8.5 build system. Tested using a local MariaDB v10.11.6 database server.

## Compiling

```bash
./gradlew build
```

## Database Setup

Assuming a local MariaDB server is running on port 3306, with user `akrish13`
and blank password, create the database and set it as active. Ensure that the
provided `database_refresh.sql` file is in the current directory.

```bash
mariadb -u akrish13 -p
Enter password:
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 9
Server version: 10.11.6-MariaDB-log MariaDB Server

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]> CREATE DATABASE test;
Query OK, 1 row affected (0.001 sec)

MariaDB [(none)]> USE test;
Database changed
MariaDB [wolf_inns]> SOURCE database_refresh.sql;
```

## Running

```bash
./gradlew run
```

Run tests with the command -
```bash
./gradlew test
```

There are still failing tests.