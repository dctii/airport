
/*
    INSERT AND UPDATE DATA
*/


INSERT INTO countries (ISO3166_a2_code, ISO3166_a3_code, country_name)
VALUES
    ('US', 'USA', 'United States of America'),
    ('FR', 'FRA', 'France'),
    ('BY', 'BLR', 'Belarus');

INSERT INTO timezones (timezone_name)
VALUES
    ('America/Los_Angeles'),
    ('America/New_York'),
    ('Europe/Minsk');

INSERT INTO contact_info (phone_number, email)
VALUES
    ('+13105550123', 'john_angeles@gmail.com'),
    ('+13104449012', 'blart_security@lax.com'),
    ('+17145550123', 'johnson_pilot@delta.com'),
    ('+375175550123', 'zhanna_minsk@gmail.com'),
    ('+18665292542', 'support@kayak.com'),
    ('+18554635252', NULL), -- LAX
    ('+375172791300', NULL); -- MSQ

INSERT INTO addresses (street, city_subdivision, city, city_superdivision, country, postal_code, timezone, country_id)
VALUES
    ('1234 Sunset Boulevard', NULL, 'Los Angeles', 'California', 'United States of America', '90026', 'America/Los_Angeles', 'US'),
    ('1235 Sunset Boulevard', NULL, 'Los Angeles', 'California', 'United States of America', '90026', 'America/Los_Angeles', 'US'),
    ('1236 Pacific Coast Highway', NULL, 'Huntington Beach', 'California', 'United States of America', '92646', 'America/Los_Angeles', 'US'),
    ('Ulitsa Lenina 12', NULL, 'Minsk', NULL, 'Belarus', '220030', 'Europe/Minsk', 'BY'),
    ('7 Market Street', NULL, 'Stamford', 'Conneticut', 'United States of America', '90026', 'America/New_York', 'US'),
    ('1 World Way', NULL, 'Los Angeles', 'California', 'United States of America', '90045', 'America/Los_Angeles', 'US'),
    ('Minsk National Airport', NULL, 'Minsk', NULL, 'Belarus', '220054', 'Europe/Minsk', 'BY');

INSERT INTO person_info (surname, given_name, middle_name, birthdate, sex, address_id, contact_info_id)
VALUES
    -- passengers
    (
        'Doe', 'John', NULL, '1975-12-25', 'M',
        (SELECT address_id FROM addresses WHERE street = '1234 Sunset Boulevard' AND postal_code = '90026'),
        (SELECT contact_info_id FROM contact_info WHERE phone_number = '+13105550123')
    ),
    (
        'Doroshevich', 'Zhanna', NULL, '1985-11-10', 'F',
        (SELECT address_id FROM addresses WHERE street = '1234 Sunset Boulevard' AND postal_code = '90026'),
        (SELECT contact_info_id FROM contact_info WHERE phone_number = '+375175550123')
     ),
     -- check-in staff
     ('Doe', 'Jane', NULL, '1985-11-25', 'F', NULL, NULL),
     -- security
     ('Blart', 'Paul', NULL, '1985-11-25', 'M', NULL, NULL),
     -- flight crew members
     ('Johnson', 'Alice', NULL, '1985-11-25', 'F', NULL, NULL),
     -- OTHER
     ('Ericson', 'Alexa', NULL, '1985-11-25', 'F', NULL, NULL),
     ('Nguyen', 'Joy', NULL, '1985-11-25', 'F', NULL, NULL);


UPDATE person_info
    SET address_id = (SELECT address_id FROM addresses WHERE street = '1235 Sunset Boulevard' AND postal_code = '90026')
    WHERE surname = 'Blart' AND given_name = 'Paul';

UPDATE person_info
    SET address_id = (SELECT address_id FROM addresses WHERE street = '1236 Pacific Coast Highway' AND postal_code = '92646')
    WHERE surname = 'Johnson' AND given_name = 'Alice';

UPDATE person_info
    SET address_id = (SELECT address_id FROM addresses WHERE street = 'Ulitsa Lenina 12' AND postal_code = '220030')
    WHERE surname = 'Doroshevich' AND given_name = 'Zhanna';

-- delete, but roll back
START TRANSACTION;
DELETE FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa';
DELETE FROM person_info WHERE surname = 'Nguyen' AND given_name = 'Joy';
DELETE FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul';
DELETE FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice';
DELETE FROM person_info WHERE surname = 'Doroshevich' AND given_name = 'Zhanna';
DELETE FROM person_info;
DELETE FROM addresses;
DELETE FROM contact_info;
DELETE FROM timezones;
DELETE FROM countries;
ROLLBACK;





INSERT INTO passports (passport_number, issue_date, expiry_date, person_info_id)
VALUES
    (
        'KJ5423678', '2022-01-15', '2032-01-15',
        (SELECT person_info_id FROM person_info
            WHERE contact_info_id = (SELECT contact_info_id FROM contact_info WHERE phone_number = '+13105550123')
            )
    ),
    (
        'ZH8392015', '2023-03-10', '2033-03-10',
        (SELECT person_info_id FROM person_info
         WHERE contact_info_id = (SELECT contact_info_id FROM contact_info WHERE phone_number = '+375175550123')
            )
    );

INSERT INTO booking_agencies (agency_name, address_id, contact_info_id)
VALUES
    (
        'Kayak.com',
        (SELECT address_id FROM addresses WHERE street = '7 Market Street' AND postal_code = '90026'),
        (SELECT contact_info_id FROM contact_info WHERE phone_number = '+18665292542')
    );

INSERT INTO passengers (passport_id, government_id)
VALUES
    ('KJ5423678', NULL),
    ('ZH8392015', NULL);

INSERT INTO bookings (
    booking_number, purchase_datetime, seat_class, seat_number, booking_status,
    price, passenger_id, booking_agency_id
    )
VALUES
    (
        'A001', NOW(), 'economy', 'G12', 'confirmed',
        1500.00,
        (SELECT passenger_id FROM passengers
            WHERE passport_id = 'KJ5423678'
        ),
        (SELECT booking_agency_id FROM booking_agencies WHERE agency_name = 'Kayak.com')
    ),
    (
        'A002', NOW(), 'economy', 'F12', 'confirmed',
        1600.00,
        (SELECT passenger_id FROM passengers
            WHERE passport_id = 'ZH8392015'
        ),
        (SELECT booking_agency_id FROM booking_agencies WHERE agency_name = 'Kayak.com')
    );

---- after booking, assign the booking ID to the passengers
UPDATE passengers
SET booking_id = (
    CASE
        WHEN passport_id = 'KJ5423678' THEN
            (
                SELECT booking_id FROM bookings
                WHERE passenger_id = (
                    SELECT passenger_id FROM (
                        SELECT passenger_id FROM passengers
                        WHERE passport_id = 'KJ5423678'
                    ) AS temp
                )
            )
        WHEN passport_id = 'ZH8392015' THEN
            (
                SELECT booking_id FROM bookings
                WHERE passenger_id = (
                    SELECT passenger_id FROM (
                        SELECT passenger_id FROM passengers
                        WHERE passport_id = 'ZH8392015'
                    ) AS temp
                )
            )
    END
)
WHERE passport_id IN ('KJ5423678', 'ZH8392015');


---- set up airports
INSERT INTO airports (
    IATA_airport_code, airport_name, timezone_name, address_id
)
VALUES
    (
        'LAX', 'Los Angeles International Airport', 'America/Los_Angeles',
        (SELECT address_id FROM addresses WHERE street = '1 World Way' AND postal_code = '90045')
    ),
    (
        'MSQ', 'Minsk National Airport', 'Europe/Minsk',
        (SELECT address_id FROM addresses WHERE street = 'Minsk National Airport' AND postal_code = '220054')
    );
--
INSERT INTO terminals (
    terminal_code, terminal_name, is_international, is_domestic, is_private, airport_id
)
VALUES
    ('TBIT', 'Tom Bradley International Terminal', 1, 0, 0, 'LAX'),
    ('T1', 'Southwest Airlines Terminal', 0, 1, 0, 'LAX'),
    ('T2', 'International Terminal', 1, 1, 0, 'LAX'),
    ('T3', 'Delta Airlines Terminal', 0, 1, 0, 'LAX'),
    ('T1', 'Terminal 1', 1, 0, 0, 'MSQ'),
    ('T2', 'Terminal 2', 0, 1, 0, 'MSQ');

---- set up airport staff
INSERT INTO airport_staff (
    member_role, person_info_id, airport_id
)
VALUES
    (
        'check-in',
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'Jane'),
        'LAX'
    ),
    (
        'security',
        (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul'),
        'LAX'
    );

---- security checkpoint
INSERT INTO security_checkpoints (
    station_code,
    airport_team_id,
    terminal_id,
    airport_id
)
VALUES
    (
        'LAX-TBIT-SC01',
        (SELECT airport_team_id FROM airport_teams WHERE station_code = 'LAX-TBIT-SC01'),
        (SELECT terminal_id FROM terminals WHERE terminal_code = 'TBIT' AND airport_id = 'LAX'),
        'LAX'
    );

-- create team for security checkpoint
INSERT INTO airport_teams (
    station_code, airport_id, terminal_id
)
VALUES
    (
        'LAX-TBIT-SC01',
        'LAX',
        (SELECT terminal_id FROM security_checkpoints WHERE station_code = 'LAX-TBIT-SC01')
    );

-- update staff member by assigning to team
UPDATE airport_staff AS ast
    JOIN
        person_info AS pi ON ast.person_info_id = pi.person_info_id
    JOIN
        airport_teams AS at ON at.station_code = 'LAX-TBIT-SC01'
    SET ast.airport_team_id = at.airport_team_id
    WHERE pi.surname = 'Blart' AND pi.given_name = 'Paul';


-- update and assign team to security checkpoint
UPDATE security_checkpoints
    SET airport_team_id = (SELECT airport_team_id FROM airport_teams WHERE station_code = 'LAX-TBIT-SC01')
    WHERE station_code = 'LAX-TBIT-SC01';

---- gates
INSERT INTO gates (
    gate_code,
    terminal_id
)
VALUES
    (
        '148',
        (SELECT terminal_id FROM terminals WHERE terminal_code = 'TBIT' AND airport_id = 'LAX')
    ),
    (
        '150',
        (SELECT terminal_id FROM terminals WHERE terminal_code = 'TBIT' AND airport_id = 'LAX')
    ),
    (
        'T1-2',
        (SELECT terminal_id FROM terminals WHERE terminal_code = 'T1' AND airport_id = 'MSQ')
    );

---- passengers check in, only work with John and LAX airport
--
---- set up flights
INSERT INTO airlines (IATA_airline_code, ICAO_airline_code, airline_name)
VALUES
    ('DL', 'DAL', 'Delta Air Lines'),
    ('B2', 'BRU', 'Belavia Belarusian Airlines');

INSERT INTO aircrafts (aircraft_registration_code, country_id, aircraft_model, passenger_capacity)
VALUES
    ('N747A1', 'US', 'Boeing 747-200', 400),
    ('F-GXYZ', 'FR', 'Airbus A320-200', 170);


-- create flight staff members
INSERT INTO flight_staff (
    member_role,
    person_info_id,
    airline_id
    )
VALUES
    (
        'pilot',
        (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice'),
        'DL'
    ),
    (
        'flight attendant',
        (SELECT person_info_id FROM person_info WHERE surname = 'Nguyen' AND given_name = 'Joy'),
        'DL'
    );

-- create flight
INSERT INTO flights (
    flight_code,
    departure_time, arrival_time,
    airline_id,
    departure_gate_id,
    arrival_gate_id,
    aircraft_registration_id, aircraft_country_id
)
VALUES
    (
    'DL123',
    '2024-12-20 09:00:00', '2024-12-20 22:35:00',
    'DL',
    (SELECT gate_id FROM gates
        WHERE gate_code = '148'
            AND terminal_id = (SELECT terminal_id FROM terminals WHERE terminal_code = 'TBIT' AND airport_id = 'LAX')
        ),
    (SELECT gate_id FROM gates
        WHERE gate_code = 'T1-2'
            AND terminal_id = (SELECT terminal_id FROM terminals WHERE terminal_code = 'T1' AND airport_id = 'MSQ')
        ),
    'N747A1', 'US'
    );

-- create flight crew
INSERT INTO flight_crew (
    airline_id, flight_id
)
VALUES
    ('DL', 'DL123');

-- update flight staff members so they are assigned to a flight_crew
UPDATE flight_staff
    SET flight_crew_id = (SELECT flight_crew_id FROM flight_crew WHERE flight_id = 'DL123')
    WHERE person_info_id IN (
        SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice'
        UNION
        SELECT person_info_id FROM person_info WHERE surname = 'Nguyen' AND given_name = 'Joy'
    );

-- then, update the flight's flight crew
UPDATE flights
    SET flight_crew_id = (SELECT flight_crew_id FROM flight_crew WHERE flight_id = 'DL123')
    WHERE flight_code= 'DL123';

-- booking in the check in system
INSERT INTO check_ins (
    check_in_method,
    airport_staff_id,
    booking_id
)
VALUES
    (
        'staff',
        (SELECT airport_staff_id FROM airport_staff WHERE person_info_id =  (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'Jane')),
        (SELECT booking_id FROM bookings WHERE booking_number = 'A001')
    );

INSERT INTO baggage (baggage_code, baggage_weight, baggage_price)
VALUES ('DL123-A001-G12-20241220', 25.00, 0.00);

INSERT INTO boarding_passes (
    boarding_time, boarding_group, check_in_id, flight_id, baggage_id
)
VALUES
    (
        '2024-12-20 08:00:00',
        'Group 3',
        (SELECT check_in_id FROM check_ins WHERE booking_id = (SELECT booking_id FROM bookings WHERE booking_number = 'A001')),
        'DL123',
        'DL123-A001-G12-20241220'
    );

-- update baggage to have boarding_pass data
UPDATE baggage
    SET boarding_pass_id = (
        SELECT boarding_pass_id FROM boarding_passes WHERE baggage_id = 'DL123-A001-G12-20241220'
    )
    WHERE baggage_code = 'DL123-A001-G12-20241220';

-- update bookings flight id and booking_status
UPDATE bookings
    SET flight_id = 'DL123',
        booking_status = 'checked-in'
    WHERE booking_number = 'A001';

-- confirm that boarding pass was issued to passenger
UPDATE check_ins
    SET pass_issued = 1
    WHERE booking_id = (
        SELECT booking_id FROM bookings WHERE booking_number = 'A001'
        );

-- passenger boards the plane
UPDATE bookings
    SET booking_status = 'boarded'
    WHERE booking_number = 'A001';

