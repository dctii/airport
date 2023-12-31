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

### Set up Database

```shell
# 1. Drop `airport` database in case it exists. Will be dropped if it exists.
mysql -u {{username}} -p{{password}} < src/resources/sql/airport_database_drop.sql

# 2. Create `airport` database.
mysql -u {{username}} -p{{password}} < src/resources/sql/airport_database_creation.sql

# 3. Create `airport` schema.
mysql -u {{username}} -p{{password}} -D airport < src/resources/sql/airport_schema.sql

# 4. Load preset `airport` data.
mysql -u {{username}} -p{{password}} -D airport < src/resources/sql/load_base_data.sql

mvn clean install
mvn exec:java
```

### Run `BaseDataLoader.class`

```shell
# Clean install dependencies
mvn clean install

# Run the `base-data-loader` profile to load all of the data
mvn exec:java -P base-data-loader
```

#### Preview of `BaseDataLoader` options:

```shell
=== Airport Base Data Loading Tools: ===
[0] Exit
[1] Execute all 'Load' options
[2] Load Country ISO Codes and Country Names Data
[3] Load Timezones Data
[4] Load Airports Data
[5] Load Airlines Data
[6] Load Terminals Data
[7] Load Gates Data

Enter your choice:
...
```

### Run `Main.class`

```shell
# Run `clean install` if you didn't load the additional data through BaseDataLoader
mvn clean install

# Run the the primary profile to run the services
mvn exec:java

```

#### Preview of `Main.class` Services:

```shell
=== Airport Check-in System: ===
[0] Exit
[1] Register Passport Holder
[2] Perform Check-In
[3] Board Passengers

Enter your choice:
1


=== Register Passport Holder: ===
Enter Surname:
Doe
Enter Given Name:
John
Enter Middle Name (or type 'none' if not applicable):
none
Enter Birthdate (YYYY-MM-DD):
1990-12-12
Enter Sex (M/F):
m
Enter Passport Number:
USA312414
Enter Passport Issue Date (YYYY-MM-DD):
2023-12-12
Enter Passport Expiry Date (YYYY-MM-DD):
2033-12-12
Enter Street Address:
123 good street
Enter City:
lovelytown
Enter Postal Code
849516
Enter Country Code (e.g., US, JP):
US
Enter Phone Number (include country code, e.g., +15551234567):
+13105558888
Enter Email Address:
john_doe89462@gmail.com
Registering passport holder info


=== Registered Person Information: ===
Name: JOHN DOE
Birthdate: 1990-12-12
Sex: M


= Passport Information =
Passport Number: USA312414
Issue Date: 2023-12-12
Expiration Date: 2033-12-12


= Address =
Street: 123 GOOD STREET
City: LOVELYTOWN
Postal Code: 849516
Country: US


= Phone Number =
Phone Number: +13105558888


= Email Address =
Email Address: john_doe89462@gmail.com


Registration completed successfully.



=== Airport Check-in System: ===
[0] Exit
[1] Register Passport Holder
[2] Perform Check-In
[3] Board Passengers

Enter your choice:
2


Enter Airline Staff Email:
emi_sato@air-japan.co.jp
Enter Booking Number:
KAYAK654321
Does the passenger have baggage? Y/N
y
How much does the baggage weigh? Type in the weight (e.g., 23 or 23.00)
23.00

=== Boarding Information ===
Boarding Group: Group B
Boarding Time: 2024-12-24T10:30:00.000-0800
Flight Code: NQ456
Gate: 149
Baggage Code: NQ456-HND-KAYAK654321

Check-In completed successfully.



=== Airport Check-in System: ===
[0] Exit
[1] Register Passport Holder
[2] Perform Check-In
[3] Board Passengers

Enter your choice:
3


=== Board Passenger onto Plane ===
Enter Booking Number for Boarding:
KAYAK654321

Boarding Successful for Booking Number: KAYAK654321
Flight Code: NQ456
Status: Boarded
Boarded: Yes
Boarding completed successfully.



=== Airport Check-in System: ===
[0] Exit
[1] Register Passport Holder
[2] Perform Check-In
[3] Board Passengers

Enter your choice:
0


Exiting...

```


## Assignments

<hr />

### Assignment 5: DOM, SAX, StAX, JAXB, Jackson

<hr />

#### Requirements

##### Comments and Required changes to consider from previous assignment:

- N/A

##### Requirements for Assignment 5

1. Use 2 `xml` files to represent 2 tables _which have not been used_ from your database and are not used their  2 java representation.
2. Create DOM or SAX or STAX parser for the 1st `xml` file and include this into your project logic to get and record data from the 1st `xml`.
3. Create a `JAXB` utility for the 2nd `xml` file to get and record data from xml file
4. For `JAXB` also use `xml` schema validation
5. Represent 1 other table which was not used in the project and corresponding Java `class` with a `json` file
6. Create a utility with _Jackson_ to read and record data from and to the `json` file by java `class`.

<hr />

### Assignment 4: JDBC. DAO classes.

<hr />

#### Requirements

##### Comments and Required changes to consider from previous assignment:

- Database Schema:
    - Remove redundant relationships in schema and remove or collapse tables into others where
      possible
    - Simplify relationships between tables, thereby reducing unnecessary complexity. A closed table
      relationship path should exist.
        - For example, one person can have many addresses, one person can have many email addresses,
          phone numbers, etc.
    - Resolve circular relationships. A strategy, for example, is to create another table that sets
      a relationship between.
    - Try not to have more than two relationships per table.

##### Requirements for Assignment 4

1. Automate same business logic but using MyBatis and provide ability to switch between
   implementations from properties file

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
