
/*
    Load base data
*/

INSERT INTO countries (country_code, country_name)
VALUES
    ('US', 'United States of America'),
    ('CN', 'China'),
    ('JP', 'Japan'),
    ('GB', 'United Kingdom of Great Britain and Northern Ireland'),
    ('BY', 'Belarus')
    ;

SHOW WARNINGS;

INSERT INTO timezones (timezone)
VALUES
    ('America/Los_Angeles'),
    ('America/New_York'),
    ('Asia/Tokyo'),
    ('Europe/London'),
    ('Europe/Minsk')
    ;

SHOW WARNINGS;

INSERT INTO email_addresses (email_address)
VALUES
    ('john_doe@gmail.com'),
    ('zhanna_do@yandex.com'),
    ('jane_doe@delta.com'),
    ('paul_blart@delta.com'),
    ('alice_johnson@delta.com'),
    ('alexa_ericson@delta.com'),
    ('joy_kurosawa@air-japan.co.jp'),
    ('hiro_tanaka@air-japan.co.jp'),
    ('emi_sato@air-japan.co.jp')
;

SHOW WARNINGS;

INSERT INTO phone_numbers (phone_number)
VALUES
    ('+13105550123'),
    ('+375292001122'),
    ('+13105550124'),
    ('+13105550125'),
    ('+13105550126'),
    ('+12035550126'),
    ('+81312345678'),
    ('+81312344321'),
    ('+81312349999')
;

SHOW WARNINGS;

INSERT INTO addresses (street, city_subdivision, city, city_superdivision, country_code, postal_code, timezone)
VALUES
    -- John Doe, passenger
    ('1234 Sunset Boulevard', NULL, 'Los Angeles', 'California', 'US', '90026', 'America/Los_Angeles'),
    -- Zhanna Doroshevich, passenger
    ('Ulitsa Lenina 12', NULL, 'Minsk', NULL, 'BY', '220030', 'Europe/Minsk'),
    -- Paul Blart & Jane Doe
    ('1235 Sunset Boulevard', NULL, 'Los Angeles', 'California', 'US', '90026', 'America/Los_Angeles'),
    -- Alice Johnson
    ('1236 Pacific Coast Highway', NULL, 'Huntington Beach', 'California', 'US', '92646', 'America/Los_Angeles'),
    -- Alexa Ericson
    ('7 Market Street', NULL, 'Stamford', 'Connecticut', 'US', '90026', 'America/New_York'),
    -- Joy Kurosawa
    ('1-4-20 Roppongi', 'Minato City', 'Tokyo', 'Tokyo', 'JP', '106-0032', 'Asia/Tokyo'),
    -- Hiro Tanaka & Emi Sato
    ('2-5-8 Shinjuku', 'Shinjuku City', 'Tokyo', 'Tokyo', 'JP', '160-0022', 'Asia/Tokyo'),
    -- LAX
    ('1 World Way', NULL, 'Los Angeles', 'California', 'US', '90045', 'America/Los_Angeles'),
    -- JFK
    ('JFK Access Road', 'Queens', 'New York', 'New York', 'US', '11430', 'America/New_York'),
    -- HND
    ('3-3-2 Haneda-Kuko', 'Ota-ku', 'Tokyo', 'Tokyo', 'JP', '144-0041', 'Asia/Tokyo'),
    -- LHR
    ('Nelson Road', 'Hounslow', 'London', 'England', 'GB', 'TW6 2GW', 'Europe/London'),
    -- MSQ
    ('Minsk National Airport', 'Petrovichskiy', 'Minsk', 'Minsk Region', 'BY', '222224', 'Europe/Minsk'),
    -- Delta Airlines
    ('1030 Delta Boulevard', 'Atlanta', 'Atlanta', 'Georgia', 'US', '30354', 'America/New_York'),
    -- Belavia Belarusian Airlines
    ('14A Nemiga Street', 'Minsk', 'Minsk', 'Minsk Region', 'BY', '220004', 'Europe/Minsk'),
    -- Air Japan
    ('ANA House', 'Minato', 'Tokyo', 'Tokyo', 'JP', '105-7133', 'Asia/Tokyo'),
    -- British Airways
    ('Waterside, PO Box 365', 'Harmondsworth', 'London', 'Greater London', 'GB', 'UB7 0GB', 'Europe/London')
    ;

SHOW WARNINGS;

INSERT INTO person_info (surname, given_name, middle_name, birthdate, sex)
VALUES
    -- passengers
    ('Doe', 'John', NULL, '1975-12-25', 'M'),
    ('Doroshevich', 'Zhanna', NULL, '1985-11-10', 'F'),

     -- flight crew members
    ('Doe', 'Jane', NULL, '1985-11-25', 'F'),
    ('Blart', 'Paul', NULL, '1985-11-25', 'M'),
    ('Johnson', 'Alice', 'Katelyn', '1985-11-25', 'F'),
     ('Ericson', 'Alexa', 'Corinna', '1985-11-25', 'F'),
     ('Kurosawa', 'Joy', NULL, '1985-11-25', 'F'),
     ('Tanaka', 'Hiro', NULL, '1990-06-15', 'M'),
     ('Sato', 'Emi', NULL, '1992-09-30', 'F')
    ;

SHOW WARNINGS;

-- Inserting phone numbers
INSERT INTO person_phone_numbers (
    person_info_id,
    phone_number_id
)
VALUES
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'John'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+13105550123')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doroshevich' AND given_name = 'Zhanna'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+375292001122')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'Jane'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+13105550126')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+13105550124')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+13105550125')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+12035550126')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Kurosawa' AND given_name = 'Joy'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+81312345678')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Tanaka' AND given_name = 'Hiro'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+81312344321')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Sato' AND given_name = 'Emi'),
        (SELECT phone_number_id FROM phone_numbers WHERE phone_number = '+81312349999')
    )
    ;

SHOW WARNINGS;


-- Inserting email addresses
INSERT INTO person_email_addresses (
    person_info_id,
    email_address_id
)
VALUES
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'John'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'john_doe@gmail.com')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doroshevich' AND given_name = 'Zhanna'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'zhanna_do@yandex.com')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'Jane'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'jane_doe@delta.com')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'paul_blart@delta.com')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'alice_johnson@delta.com')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'alexa_ericson@delta.com')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Kurosawa' AND given_name = 'Joy'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'joy_kurosawa@air-japan.co.jp')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Tanaka' AND given_name = 'Hiro'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'hiro_tanaka@air-japan.co.jp')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Sato' AND given_name = 'Emi'),
        (SELECT email_address_id FROM email_addresses WHERE email_address = 'emi_sato@air-japan.co.jp')
    )
    ;

SHOW WARNINGS;

-- Inserting addresses
INSERT INTO person_addresses (
    person_info_id,
    address_id
)
VALUES
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'John'),
        (SELECT address_id FROM addresses WHERE street = '1234 Sunset Boulevard' AND city = 'Los Angeles')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doroshevich' AND given_name = 'Zhanna'),
        (SELECT address_id FROM addresses WHERE street = 'Ulitsa Lenina 12' AND city = 'Minsk')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'Jane'),
        (SELECT address_id FROM addresses WHERE street = '1235 Sunset Boulevard' AND city = 'Los Angeles')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul'),
        (SELECT address_id FROM addresses WHERE street = '1235 Sunset Boulevard' AND city = 'Los Angeles')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice'),
        (SELECT address_id FROM addresses WHERE street = '1236 Pacific Coast Highway' AND city = 'Huntington Beach')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa'),
        (SELECT address_id FROM addresses WHERE street = '7 Market Street' AND city = 'Stamford')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Kurosawa' AND given_name = 'Joy'),
        (SELECT address_id FROM addresses WHERE street = '1-4-20 Roppongi' AND city = 'Tokyo')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Tanaka' AND given_name = 'Hiro'),
        (SELECT address_id FROM addresses WHERE street = '2-5-8 Shinjuku' AND city = 'Tokyo')
    ),
    (
        (SELECT person_info_id FROM person_info WHERE surname = 'Sato' AND given_name = 'Emi'),
        (SELECT address_id FROM addresses WHERE street = '2-5-8 Shinjuku' AND city = 'Tokyo')
    );

SHOW WARNINGS;

INSERT INTO airports (
    airport_code, airport_name,
    address_id
)
VALUES
    (
        'LAX', 'Los Angeles International Airport',
        (SELECT address_id FROM addresses WHERE street = '1 World Way' AND postal_code = '90045')
    ),
    (
        'JFK', 'John F. Kennedy International Airport',
        (SELECT address_id FROM addresses WHERE street = 'JFK Access Road' AND postal_code = '11430')
    ),
    (
        'HND', 'Tokyo International Airport',
        (SELECT address_id FROM addresses WHERE street = '3-3-2 Haneda-Kuko' AND postal_code = '144-0041')
    ),
    (
        'LHR', 'London Heathrow',
        (SELECT address_id FROM addresses WHERE street = 'Nelson Road' AND postal_code = 'TW6 2GW')
    ),
    (
        'MSQ', 'Minsk National Airport',
        (SELECT address_id FROM addresses WHERE street = 'Minsk National Airport' AND postal_code = '222224')
    )
    ;

SHOW WARNINGS;

INSERT INTO terminals (airport_code, terminal_code, terminal_name, is_international, is_domestic)
VALUES
    ('LAX', 'TBIT', 'Tom Bradley International Terminal', 1, 0),
    ('LAX', 'T1', 'Southwest Airlines Terminal', 0, 1),
    ('LAX', 'T2', 'International Terminal', 1, 1),
    ('LAX', 'T3', 'Delta Airlines Terminal', 0, 1),
    ('HND', 'T1', 'Terminal 1', 0, 1),
    ('HND', 'T2', 'Terminal 2', 1, 1),
    ('HND', 'T3', 'Terminal 3', 0, 1),
    ('LHR', 'T2', 'Heathrow Terminal 2', 1, 1),
    ('LHR', 'T3', 'Heathrow Terminal 3', 1, 1),
    ('LHR', 'T4', 'Heathrow Terminal 4', 1, 1),
    ('LHR', 'T5', 'Heathrow Terminal 5', 1, 1),
    ('MSQ', 'T1', 'Terminal 1', 1, 1)
    ;

SHOW WARNINGS;

INSERT INTO gates(gate_code, airport_code, terminal_code)
VALUES
        ('148', 'LAX', 'TBIT'),
        ('149', 'LAX', 'TBIT'),

        ('105', 'HND', 'T3'),
        ('106', 'HND', 'T3'),

        ('30', 'LHR', 'T3'),
        ('31', 'LHR', 'T3'),
        ('32', 'LHR', 'T3'),

        ('1', 'MSQ', 'T1'),
        ('2', 'MSQ', 'T1'),
        ('3', 'MSQ', 'T1')
    ;

SHOW WARNINGS;

INSERT INTO airlines (
    airline_code, airline_name,
    address_id
)
VALUES
    (
        'DL', 'Delta Air Lines',
        (SELECT address_id FROM addresses WHERE street = '1030 Delta Boulevard' AND postal_code = '30354')
    ),
    (
        'B2', 'Belavia Belarusian Airlines',
        (SELECT address_id FROM addresses WHERE street = '14A Nemiga Street' AND postal_code = '220004')
    ),
    (
        'NQ', 'Air Japan',
        (SELECT address_id FROM addresses WHERE street = 'ANA House' AND postal_code = '105-7133')
    ),
    (
        'BA', 'British Airways',
        (SELECT address_id FROM addresses WHERE street = 'Waterside, PO Box 365' AND postal_code = 'UB7 0GB')
    )
    ;

SHOW WARNINGS;

INSERT INTO flights (
    flight_code, departure_time, arrival_time, gate_id,
    destination, airline_code, aircraft_model, tail_number, passenger_capacity
)
VALUES
    (
        'DL123', '2025-01-25 10:00:00', '2024-01-24 08:00:00',
        (SELECT gate_id FROM gates WHERE gate_code = '148' AND airport_code = 'LAX'),
        'MSQ', 'DL', 'Boeing 777', 'N123DL', 300
    ),
    (
        'NQ456', '2024-12-24 11:00:00', '2024-12-25 07:00:00',
        (SELECT gate_id FROM gates WHERE gate_code = '149' AND airport_code = 'LAX'),
        'HND', 'NQ', 'Boeing 787', 'N123NQ', 250
    );

SHOW WARNINGS;

INSERT INTO airline_staff (member_role, person_info_id)
VALUES
    -- DL
    ('Pilot', (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice')),
    ('Flight Attendant', (SELECT person_info_id FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa')),
    ('Check-in Staff', (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul')),
    -- NQ
    ('Pilot', (SELECT person_info_id FROM person_info WHERE surname = 'Tanaka' AND given_name = 'Hiro')),
    ('Flight Attendant', (SELECT person_info_id FROM person_info WHERE surname = 'Kurosawa' AND given_name = 'Joy')),
    ('Check-in Staff', (SELECT person_info_id FROM person_info WHERE surname = 'Sato' AND given_name = 'Emi'));

SHOW WARNINGS;

INSERT INTO flight_staff (flight_crew_id, airline_staff_id)
VALUES
    -- DL123
    (
    (SELECT flight_crew_id FROM flight_crew WHERE flight_code = 'LA123'),
     (SELECT airline_staff_id FROM airline_staff WHERE person_info_id =
      (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice'))), -- Pilot
    (
    (SELECT flight_crew_id FROM flight_crew WHERE flight_code = 'LA123'),
     (SELECT airline_staff_id FROM airline_staff WHERE person_info_id =
      (SELECT person_info_id FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa')
      )
      ), -- Flight Attendant

    -- NQ456
    (
    (SELECT flight_crew_id FROM flight_crew WHERE flight_code = 'LA456'),
     (SELECT airline_staff_id FROM airline_staff WHERE person_info_id =
      (SELECT person_info_id FROM person_info WHERE surname = 'Tanaka' AND given_name = 'Hiro')
      )
      ), -- Pilot
    (
    (SELECT flight_crew_id FROM flight_crew WHERE flight_code = 'LA456'),
     (SELECT airline_staff_id FROM airline_staff WHERE person_info_id =
      (SELECT person_info_id FROM person_info WHERE surname = 'Kurosawa' AND given_name = 'Joy')
      )
      ); -- Flight Attendant

SHOW WARNINGS;

INSERT INTO passports (
    passport_number, issue_date, expiry_date,
    person_info_id
)
VALUES
    ('USA1234567', '2018-01-01', '2028-01-01',
        (SELECT person_info_id FROM person_info WHERE surname = 'Doe' AND given_name = 'John')
        ),
    ('BYR2345678', '2018-01-02', '2028-01-02',
        (SELECT person_info_id FROM person_info WHERE surname = 'Doroshevich' AND given_name = 'Zhanna')
        ),
    ('USA3456789', '2018-01-03', '2028-01-03',
        (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul')
        ),
    ('USA4567890', '2018-01-04', '2028-01-04',
        (SELECT person_info_id FROM person_info WHERE surname = 'Johnson' AND given_name = 'Alice')
        ),
    ('USA5678901', '2018-01-05', '2028-01-05',
        (SELECT person_info_id FROM person_info WHERE surname = 'Ericson' AND given_name = 'Alexa')
        ),
    ('JPN6789012', '2018-01-06', '2028-01-06',
        (SELECT person_info_id FROM person_info WHERE surname = 'Kurosawa' AND given_name = 'Joy')
        ),
    ('JPN7890123', '2018-01-07', '2028-01-07',
        (SELECT person_info_id FROM person_info WHERE surname = 'Tanaka' AND given_name = 'Hiro')
        ),
    ('JPN8901234', '2018-01-08', '2028-01-08',
        (SELECT person_info_id FROM person_info WHERE surname = 'Sato' AND given_name = 'Emi')
        );

SHOW WARNINGS;

INSERT INTO bookings (
    booking_number, purchase_datetime, seat_class, seat_number,
    status, price, agency, passport_number, flight_code
)
VALUES
    (
        'KAYAK123456', CURRENT_DATE, 'Economy', '12A',
        'Confirmed', 1600.00, 'Kayak',
        'BYR2345678',
        'DL123'
    ),
    (
        'KAYAK654321', CURRENT_DATE, 'Business', '2B',
        'Confirmed', 4000.00, 'Kayak',
        'USA1234567',
        'NQ456'
    )
    ;

SHOW WARNINGS;

INSERT INTO check_ins (check_in_method, pass_issued, airline_staff_id, booking_id)
VALUES
    (
        'Staff',
        1,
        (SELECT airline_staff_id FROM airline_staff WHERE person_info_id =
            (SELECT person_info_id FROM person_info WHERE surname = 'Blart' AND given_name = 'Paul')
            ),
        (SELECT booking_id FROM bookings WHERE booking_number = 'KAYAK123456')
    );

UPDATE bookings
    SET status = 'Checked-in'
    WHERE booking_number = 'KAYAK123456';

SHOW WARNINGS;

INSERT INTO boarding_passes (
    is_boarded, boarding_time, boarding_group,
    check_in_id
    )
VALUES
    (
        1, '2024-01-24 09:00:00', 'Group C',
        (SELECT check_in_id FROM check_ins WHERE booking_id =
            (SELECT booking_id FROM bookings WHERE booking_number = 'KAYAK123456')
        )
    );

SHOW WARNINGS;

INSERT INTO baggage (
    baggage_code, weight, price,
    check_in_id
    )
VALUES
    (
        'DL123-HND-KAYAK123456', 23.00, 24.15,
        (SELECT check_in_id FROM check_ins WHERE booking_id =
            (SELECT booking_id FROM bookings WHERE booking_number = 'KAYAK123456')
            )
    )
    ;

UPDATE bookings
    SET status = 'Boarded'
    WHERE booking_number = 'KAYAK123456';

SHOW WARNINGS;
