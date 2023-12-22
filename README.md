# airport

# Building Company

Uses:

- MySQL 8.0.34
- MySQL Workbench 8.0.34
- IntelliJ IDEA 2023.2.3 (Community Edition)
- openjdk 11.0.21 2023-10-17
- mac os x aarch64
- mac os x x86_64
- Apache Maven 3.9.6

Dependencies:

- org.apache.logging.log4j:log4j-api v2.21.1
- org.apache.logging.log4j:log4j-core v2.21.1
- org.apache.commons:commons-lang3 v3.12.0
- commons-io:commons-io v2.15.0

Plugins:

- org.apache.maven.plugins:maven-compiler-plugin v3.11.0
- org.codehaus.mojo:exec-maven-plugin v3.1.0

## How to Run

### Run `Main.class`

```shell
mvn clean install
mvn exec:java
```

## Assignments

<hr />

### Assignment 3: JDBC. DAO classes.

<hr />

#### Requirements

##### Comments and Required changes to consider from previous assignment:

- N/A

##### Requirements for Assignment 3

1. Create new GitHub repo
2. Use `maven` as build tool and `log4j2` as logger
3. Create project structure with `bin/` folder, `dao/` folder and `service/` folder
4. Create connection pool
5. Create `Main` class where you will use only service layer to perform core logic of application
6. DAO layer should work only with DB
7. Service layer should have more business logic, all assertions and more difficult actions not
   related to database requests should be there

##### Expected Run Thus Far:

```shell
mvn exec:java
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------------< com.solvd.airport:airport >----------------------
[INFO] Building airport 1.0.0
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- exec:3.1.0:java (default-cli) @ airport ---
01:25:27.408 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.427 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.439 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.451 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.469 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.483 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.497 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.509 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.531 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
01:25:27.544 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.db.AuthConnection - Connected to the data base successfully.
=== Airport Check-In System ===
1. Perform Check-In
2. Exit
Enter your choice:
1
Enter Booking Number:
A002
Enter Flight Code:
DL123
01:25:33.512 [com.solvd.airport.Main.main()] INFO  com.solvd.airport.util.SQLUtils -
=== Boarding Information ===
Boarding Group: DL123-A002-K12-20241220
, Boarding Time: Group 3
, Flight: 2024-12-20T08:00:00.000-0800
, Gate: DL123
, Baggage Code: 148

Check-In completed successfully.

```


<hr />

### Assignment 2: Database CRUD operations. SQLs, DDL

<hr />

#### Requirements

##### Comments and Required changes to consider from previous assignment:

- N/A

##### Requirements for Assignment 2

1. Create 1 sql script file which should contain full data base creation based on schema(manually)
   This file for your data base additionally should include:
    1. 10 statements for insertion. (`INSERT INTO`)
    2. 10 statements for updating. (`UPDATE`)
    3. 10 statements for deletions. (`DELETE`)
    4. 5 `ALTER TABLE`.
    5. 1 big statement to join all tables in the database.
    6. 5 statements with `LEFT JOIN`, `RIGHT JOIN`, `INNER JOIN`, _outer joins_.
    7. 7 statements with aggregate functions and `GROUP BY` and without `HAVING`.
    8. 7 statements with aggregate functions and `GROUP BY` and with `HAVING`.

<hr />

### Assignment 1: Databases. Schemas. MySQL

<hr />

#### Requirements

##### Comments and Required changes to consider from previous assignment:

- N/A

##### Requirements for Assignment 1

1. Install MySQL Workbench
2. Using assigned topic create schema with 15 tables with all types of relations used
3. save `.mwb` file of schema and upload it as result of your homework on this assignment
4. Topics:
   ...
   **Dana Tolman** - Airport (simulate process of traveling from one country to another)
   ...

<hr />
