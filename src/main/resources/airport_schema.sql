/*

    Schema Creation
*/

CREATE TABLE
    countries (
        ISO3166_a2_code VARCHAR(2) NOT NULL UNIQUE
            CHECK(
                CHAR_LENGTH(ISO3166_a2_code) = 2
                AND ISO3166_a2_code REGEXP '^[A-Z]+$'
            ),
        ISO3166_a3_code VARCHAR(3) UNIQUE
            CHECK(
                CHAR_LENGTH(ISO3166_a3_code) = 3
                AND ISO3166_a3_code REGEXP '^[A-Z]+$'
            ),
        country_name VARCHAR(75) NOT NULL UNIQUE,
        PRIMARY KEY(ISO3166_a2_code)
    );

CREATE TABLE
    timezones (
        timezone_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        timezone_name VARCHAR(45) NOT NULL UNIQUE,
        PRIMARY KEY(timezone_id)
    );

CREATE TABLE
    contact_info (
        contact_info_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        phone_number VARCHAR(45)
            CHECK (
                LEFT(phone_number, 1) = '+'
                AND SUBSTRING(phone_number, 2) REGEXP '^[0-9]+$'
            ),
        email VARCHAR(45)
            CHECK(
                LOCATE('@', email) > 0
                AND LOCATE('@', email) > 1
                AND LOCATE('@', email) < CHAR_LENGTH(email)
            ),
        PRIMARY KEY(contact_info_id)
    );

CREATE TABLE
    addresses (
        address_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        street VARCHAR(45) NOT NULL,
        city_subdivision VARCHAR(45),
        city VARCHAR(45) NOT NULL,
        city_superdivision VARCHAR(45),
        country VARCHAR(75) NOT NULL,
        postal_code VARCHAR(20)
            CHECK (
                postal_code REGEXP '^[A-Z0-9]+$'
            ),
        timezone VARCHAR(45) NOT NULL,
        country_id VARCHAR(2) NOT NULL,
        PRIMARY KEY(address_id)
    );

CREATE TABLE
    person_info (
        person_info_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        surname VARCHAR(45) NOT NULL,
        given_name VARCHAR(45) NOT NULL,
        middle_name VARCHAR(45),
        birthdate DATE NOT NULL,
        sex VARCHAR(1) NOT NULL
            CHECK (
                sex IN ('M', 'F')
            ),
        address_id INT UNSIGNED,
        contact_info_id INT UNSIGNED,
        PRIMARY KEY(person_info_id)
    );

CREATE TABLE
    passports (
        passport_number VARCHAR(45),
        issue_date DATE,
        expiry_date DATE,
        person_info_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(passport_number)
    );

CREATE TABLE
    government_ids (
        gov_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        id_type VARCHAR(45),
        id_number VARCHAR(45),
        issue_date DATE,
        expiry_date DATE,
        person_info_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(gov_id)
    );

CREATE TABLE
    booking_agencies (
        booking_agency_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        agency_name VARCHAR(45) NOT NULL,
        address_id INT UNSIGNED NOT NULL,
        contact_info_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(booking_agency_id)
    );

CREATE TABLE
    passengers (
        passenger_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        passport_id VARCHAR(45),
        government_id INT UNSIGNED,
        booking_id INT UNSIGNED,
        PRIMARY KEY(passenger_id)
    );

CREATE TABLE
    bookings (
        booking_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        booking_number VARCHAR(45),
        purchase_datetime DATETIME NOT NULL,
        seat_class VARCHAR(45) NOT NULL
            CHECK (
                seat_class IN (
                    'economy',
                    'premium',
                    'business',
                    'first')
                ),
        seat_number VARCHAR(10),
        booking_status VARCHAR(45) NOT NULL
            CHECK (
                booking_status IN (
                    'confirmed',
                    'checked-in',
                    'boarded',
                    'no show',
                    'cancelled',
                    'pending',
                    'waitlisted'
                )
            ),
        price DECIMAL(10, 2)
            CHECK (price >= 0.00),
        passenger_id INT UNSIGNED NOT NULL,
        flight_id VARCHAR(10),
        booking_agency_id INT UNSIGNED,
        PRIMARY KEY(booking_id)
    );

CREATE TABLE
    check_ins (
        check_in_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        check_in_method VARCHAR(10)
            CHECK (
                check_in_method IN ('self', 'staff')
            ),
        pass_issued BOOL NOT NULL DEFAULT 0,
        airport_staff_id INT UNSIGNED,
        booking_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(check_in_id)
    );

CREATE TABLE
    boarding_passes (
        boarding_pass_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        boarding_time DATETIME,
        boarding_group VARCHAR(25),
        check_in_id INT UNSIGNED NOT NULL,
        flight_id VARCHAR(10),
        baggage_id VARCHAR(45),
        PRIMARY KEY(boarding_pass_id)
    );

CREATE TABLE
    baggage (
        baggage_code VARCHAR(45) NOT NULL UNIQUE,
        baggage_weight DECIMAL(5, 2)
            CHECK (
                baggage_weight >= 0.00
            ),
        baggage_price DECIMAL(10, 2)
            CHECK (
                baggage_price >= 0.00
            ),
        boarding_pass_id INT UNSIGNED,
        PRIMARY KEY(baggage_code)
    );

CREATE TABLE
    airports (
        IATA_airport_code VARCHAR(3) NOT NULL UNIQUE
            CHECK(
                CHAR_LENGTH(IATA_airport_code) = 3
                AND IATA_airport_code REGEXP '^[A-Z]+$'

            ),
        ICAO_airport_code VARCHAR(4) UNIQUE
            CHECK(
                CHAR_LENGTH(ICAO_airport_code) = 4
                AND ICAO_airport_code REGEXP '^[A-Z]+$'
            ),
        airport_name VARCHAR(100) UNIQUE,
        timezone_name VARCHAR(45),
        address_id INT UNSIGNED,
        PRIMARY KEY(IATA_airport_code)
    );

CREATE TABLE
    airport_staff (
        airport_staff_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        member_role VARCHAR(45) NOT NULL,
        person_info_id INT UNSIGNED NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        airport_team_id INT UNSIGNED,
        PRIMARY KEY(airport_staff_id)
    );

CREATE TABLE
    terminals (
        terminal_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        terminal_code VARCHAR(10) NOT NULL,
        terminal_name VARCHAR(100),
        is_international BOOL NOT NULL,
        is_domestic BOOL NOT NULL,
        is_private BOOL NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        PRIMARY KEY(terminal_id)
    );

CREATE TABLE
    security_checkpoints (
        security_checkpoint_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        station_code VARCHAR(45) NOT NULL UNIQUE,
        terminal_id INT UNSIGNED NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        airport_team_id INT UNSIGNED,
        PRIMARY KEY(security_checkpoint_id)
    );

CREATE TABLE
    gates (
        gate_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        gate_code VARCHAR(10) NOT NULL,
        terminal_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(gate_id)
    );

CREATE TABLE
    flights (
        flight_code VARCHAR(10) NOT NULL,
        departure_time DATETIME,
        arrival_time DATETIME,
        airline_id VARCHAR(2),
        departure_gate_id INT UNSIGNED,
        arrival_gate_id INT UNSIGNED,
        flight_crew_id INT UNSIGNED,
        aircraft_registration_id VARCHAR(45),
        aircraft_country_id VARCHAR(2),
        PRIMARY KEY(flight_code)
    );

CREATE TABLE
    flight_crew (
        flight_crew_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        airline_id VARCHAR(2),
        flight_id VARCHAR(10),
        PRIMARY KEY(flight_crew_id)
    );

CREATE TABLE
    aircrafts (
        aircraft_registration_code VARCHAR(45) NOT NULL,
        country_id VARCHAR(2),
        aircraft_model VARCHAR(45),
        passenger_capacity SMALLINT UNSIGNED,
        flight_id VARCHAR(10),
        PRIMARY KEY(aircraft_registration_code, country_id)
    );

CREATE TABLE
    airlines (
        IATA_airline_code VARCHAR(2) NOT NULL UNIQUE
            CHECK(
                CHAR_LENGTH(IATA_airline_code) = 2
                AND IATA_airline_code REGEXP '^[A-Z0-9]+$'
            ),
        ICAO_airline_code VARCHAR(3) UNIQUE
            CHECK(
                    CHAR_LENGTH(ICAO_airline_code) = 3
                    AND ICAO_airline_code REGEXP '^[A-Z]+$'
                ),
        airline_name VARCHAR(45) NOT NULL UNIQUE,
        PRIMARY KEY(IATA_airline_code)
    );

CREATE TABLE
    airport_teams (
        airport_team_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        station_code VARCHAR(45) NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        terminal_id INT UNSIGNED,
        PRIMARY KEY(airport_team_id)
    );

CREATE TABLE
    flight_staff (
        flight_staff_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        member_role VARCHAR(45) NOT NULL,
        person_info_id INT UNSIGNED NOT NULL,
        airline_id VARCHAR(2) NOT NULL,
        flight_crew_id INT UNSIGNED,
        PRIMARY KEY(flight_staff_id)
    );

/*
    ALTER TABLES
*/

ALTER TABLE addresses
    ADD
        FOREIGN KEY(country_id) REFERENCES countries(ISO3166_a2_code),
    ADD
        FOREIGN KEY(country) REFERENCES countries(country_name),
    ADD
        FOREIGN KEY(timezone) REFERENCES timezones(timezone_name);

ALTER TABLE person_info
    ADD
        FOREIGN KEY(address_id) REFERENCES addresses(address_id),
    ADD
        FOREIGN KEY(contact_info_id) REFERENCES contact_info(contact_info_id);

ALTER TABLE passports
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id);

ALTER TABLE government_ids
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id);

ALTER TABLE booking_agencies
    ADD
        FOREIGN KEY(address_id) references addresses(address_id),
    ADD
        FOREIGN KEY(contact_info_id) references contact_info(contact_info_id);

ALTER TABLE passengers
    ADD
        FOREIGN KEY(passport_id) REFERENCES passports(passport_number),
    ADD
        FOREIGN KEY(government_id) REFERENCES government_ids(gov_id),
    ADD
        FOREIGN KEY(booking_id) REFERENCES bookings(booking_id);

ALTER TABLE bookings
    ADD
        FOREIGN KEY(passenger_id) REFERENCES passengers(passenger_id),
    ADD
        FOREIGN KEY(flight_id) REFERENCES flights(flight_code),
    ADD
        FOREIGN KEY(booking_agency_id) REFERENCES booking_agencies(booking_agency_id);

ALTER TABLE check_ins
    ADD
        FOREIGN KEY(airport_staff_id) REFERENCES airport_staff(airport_staff_id),
    ADD
        FOREIGN KEY(booking_id) REFERENCES bookings(booking_id);

ALTER TABLE boarding_passes
    ADD
        FOREIGN KEY (check_in_id) REFERENCES check_ins(check_in_id),
    ADD
        FOREIGN KEY (flight_id) REFERENCES flights(flight_code),
    ADD
        FOREIGN KEY (baggage_id) REFERENCES baggage(baggage_code);

ALTER TABLE baggage
    ADD
        FOREIGN KEY(boarding_pass_id) REFERENCES boarding_passes(boarding_pass_id);

ALTER TABLE airports
    ADD
        FOREIGN KEY(address_id) REFERENCES addresses(address_id),
    ADD
        FOREIGN KEY(timezone_name) REFERENCES timezones(timezone_name);

ALTER TABLE airport_staff
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id),
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code);

ALTER TABLE terminals
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code);

ALTER TABLE security_checkpoints
    ADD
        FOREIGN KEY(airport_team_id) REFERENCES airport_teams(airport_team_id),
    ADD
        FOREIGN KEY(terminal_id) REFERENCES terminals(terminal_id),
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code);

ALTER TABLE gates
    ADD
        FOREIGN KEY(terminal_id) REFERENCES terminals(terminal_id);

ALTER TABLE flights
    ADD
        FOREIGN KEY(airline_id) REFERENCES airlines(IATA_airline_code),
    ADD
        FOREIGN KEY(arrival_gate_id) REFERENCES gates(gate_id),
    ADD
        FOREIGN KEY(departure_gate_id) REFERENCES gates(gate_id),
    ADD
        FOREIGN KEY(flight_crew_id) REFERENCES flight_crew(flight_crew_id),
    ADD
        FOREIGN KEY(aircraft_registration_id, aircraft_country_id)
            REFERENCES aircrafts(aircraft_registration_code, country_id);

ALTER TABLE flight_crew
    ADD
        FOREIGN KEY(airline_id) REFERENCES airlines(IATA_airline_code),
    ADD
        FOREIGN KEY(flight_id) REFERENCES flights(flight_code);

ALTER TABLE aircrafts
    ADD
        FOREIGN KEY(flight_id) REFERENCES flights(flight_code),
    ADD
        FOREIGN KEY(country_id) REFERENCES countries(ISO3166_a2_code);

ALTER TABLE flight_staff
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id),
    ADD
        FOREIGN KEY(airline_id) REFERENCES airlines(IATA_airline_code),
    ADD
        FOREIGN KEY(flight_crew_id) REFERENCES flight_crew(flight_crew_id);

ALTER TABLE airport_teams
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code),
    ADD
        FOREIGN KEY(terminal_id) REFERENCES terminals(terminal_id);
