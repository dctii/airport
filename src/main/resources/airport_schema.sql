USE `airport`;

-- -----------------------------------------------------
-- Table `airport`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`addresses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`addresses` (
  `address_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(45) NOT NULL,
  `city_subdivision` VARCHAR(45) NULL COMMENT 'District, neighborhood, etc.; child relationship to the city',
  `city` VARCHAR(45) NOT NULL,
  `city_superdivision` VARCHAR(45) NULL COMMENT 'State, province, prefecture, etc.; parent relationship to the city',
  `country_code` VARCHAR(2) NOT NULL COMMENT 'ISO 3166-2 country code',
  `postal_code` VARCHAR(20) NULL,
  `timezone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`address_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`airlines`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`airlines` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`airlines` (
  `airline_code` VARCHAR(2) NOT NULL COMMENT 'IATA airline code',
  `airline_name` VARCHAR(45) NOT NULL COMMENT 'Full name of the airline, e.g., “American Airlines” or “Delta Airlines”',
  `address_id` INT UNSIGNED NULL,
  PRIMARY KEY (`airline_code`),
  UNIQUE INDEX `airline_code_UNIQUE` (`airline_code` ASC) ,
  UNIQUE INDEX `airline_name_UNIQUE` (`airline_name` ASC)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`airline_staff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`airline_staff` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`airline_staff` (
  `airline_staff_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_role` VARCHAR(45) NOT NULL COMMENT 'title, position of employee',
  `person_info_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`airline_staff_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`airports`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`airports` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`airports` (
  `airport_code` VARCHAR(3) NOT NULL COMMENT 'International Airport code. \n\n\nhttps://en.wikipedia.org/wiki/IATA_airport_code',
  `airport_name` VARCHAR(100) NULL,
  `address_id` INT UNSIGNED NULL,
  PRIMARY KEY (`airport_code`),
  UNIQUE INDEX `airport_code_UNIQUE` (`airport_code` ASC) ,
  UNIQUE INDEX `airport_name_UNIQUE` (`airport_name` ASC)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`baggage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`baggage` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`baggage` (
  `baggage_code` VARCHAR(45) NOT NULL COMMENT '{{airline_code}}-{{baggage_tag_issue_code}}-{{serial_number}}\n\nhttps://www.iata.org/en/services/certification/operations-safety-security/baggage-tracking/\n\nhttps://en.wikipedia.org/wiki/Bag_tag',
  `weight` DECIMAL(5,2) NULL,
  `price` DECIMAL(10,2) NULL,
  `check_in_id` INT UNSIGNED NULL,
  PRIMARY KEY (`baggage_code`),
  UNIQUE INDEX `baggage_code_UNIQUE` (`baggage_code` ASC)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`boarding_passes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`boarding_passes` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`boarding_passes` (
  `boarding_pass_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `is_boarded` BOOLEAN NULL,
  `boarding_time` DATETIME NULL,
  `boarding_group` VARCHAR(25) NULL COMMENT 'The boarding group or boarding number since sometimes different groups board.',
  `check_in_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`boarding_pass_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`bookings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`bookings` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`bookings` (
  `booking_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `booking_number` VARCHAR(45) NOT NULL COMMENT 'Reference or confirmation number for booking',
  `purchase_datetime` DATETIME NOT NULL,
  `seat_class` VARCHAR(45) NOT NULL COMMENT 'economy, business, first class, etc.',
  `seat_number` VARCHAR(10) NULL,
  `status` VARCHAR(45) NOT NULL COMMENT '‘Confirmed’, ‘pending’, or some other condition',
  `price` DECIMAL(10,2) NOT NULL,
  `agency` VARCHAR(45) NULL,
  `passport_number` VARCHAR(45) NOT NULL,
  `flight_code` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`booking_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`check_ins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`check_ins` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`check_ins` (
  `check_in_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `check_in_method` VARCHAR(10) NULL COMMENT '‘staff’ or ‘self’',
  `pass_issued` BOOLEAN NOT NULL DEFAULT 0 COMMENT '‘1’ means boarding pass has been issued, ‘0’ means one has not been issued',
  `airline_staff_id` INT UNSIGNED NOT NULL COMMENT 'If method is ‘staff’, then the ID',
  `booking_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`check_in_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`countries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`countries` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`countries` (
  `country_code` VARCHAR(2) NOT NULL COMMENT 'ISO 3166-1 alpha-2 code, a unique, official code for a country that is two alpha characters long, e.g., ‘BY’ for Belarus. For air flights\n\nhttps://en.wikipedia.org/wiki/ISO_3166-1_alpha-2',
  `country_name` VARCHAR(75) NOT NULL COMMENT 'The country name, e.g. “Belarus”, that may contrast with the official state name, e.g., “Republic of Belarus (the)”.',
  PRIMARY KEY (`country_code`),
  UNIQUE INDEX `country_code_UNIQUE` (`country_code` ASC) ,
  UNIQUE INDEX `country_name_UNIQUE` (`country_name` ASC)
  )
ENGINE = InnoDB
COMMENT = 'The geographical region where the airports are located. Along with the ID, includes the ISO 3166-1 codes and names corresponding to the country.';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`email_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`email_addresses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`email_addresses` (
  `email_address_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email_address` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`email_address_id`),
  UNIQUE INDEX `email_address_UNIQUE` (`email_address` ASC)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`flight_crew`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`flight_crew` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`flight_crew` (
  `flight_crew_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `flight_code` VARCHAR(10) NULL,
  PRIMARY KEY (`flight_crew_id`)
  )
ENGINE = InnoDB
COMMENT = 'The group of members for a flight.';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`flights`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`flights` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`flights` (
  `flight_code` VARCHAR(10) NOT NULL COMMENT 'DL123 for ‘Delta Air Lines Flight 123’, ',
  `departure_time` DATETIME NULL COMMENT 'Time the flight leaves.',
  `arrival_time` DATETIME NULL COMMENT 'Time the flight will come.',
  `destination` VARCHAR(3) NULL COMMENT 'Airport origin\n\nIATA airport codes, 3 chars',
  `airline_code` VARCHAR(2) NULL,
  `gate_id` INT UNSIGNED NULL COMMENT 'Departure gate, linked to gate_id',
  `aircraft_model` VARCHAR(45) NULL,
  `passenger_capacity` INT NULL,
  `tail_number` VARCHAR(45) NULL COMMENT 'https://en.wikipedia.org/wiki/Aircraft_registration',
  PRIMARY KEY (`flight_code`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`flight_staff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`flight_staff` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`flight_staff` (
  `flight_staff_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `flight_crew_id` INT UNSIGNED NULL COMMENT 'The flight crew that the staff member is assigned to.',
  `airline_staff_id` INT UNSIGNED NULL,
  PRIMARY KEY (`flight_staff_id`)
  )
ENGINE = InnoDB
COMMENT = 'The individual members of a flight crew for airlines.';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`gates`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`gates` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`gates` (
  `gate_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `gate_code` VARCHAR(10) NOT NULL COMMENT 'Gate code, e.g., ‘A3’, ‘C7’',
  `airport_code` VARCHAR(3) NOT NULL,
  `terminal_code` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`gate_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`passports`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`passports` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`passports` (
  `passport_number` VARCHAR(45) NOT NULL,
  `issue_date` DATE NOT NULL COMMENT 'Date issued',
  `expiry_date` DATE NOT NULL COMMENT 'Expiration date',
  `person_info_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`passport_number`),
  UNIQUE INDEX `passport_number_UNIQUE` (`passport_number` ASC)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`person_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`person_addresses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`person_addresses` (
  `person_info_id` INT UNSIGNED NOT NULL,
  `address_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`person_info_id`, `address_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`person_email_addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`person_email_addresses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`person_email_addresses` (
  `person_info_id` INT UNSIGNED NOT NULL,
  `email_address_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`person_info_id`, `email_address_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`person_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`person_info` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`person_info` (
  `person_info_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'The biological sex of the passenger. For example, “M” for “male”, “F” for female, etc.',
  `surname` VARCHAR(45) NOT NULL COMMENT 'Family/last name',
  `given_name` VARCHAR(45) NOT NULL COMMENT 'First name',
  `middle_name` VARCHAR(45) NULL COMMENT 'Optional middle name',
  `birthdate` DATE NOT NULL COMMENT 'Date of birth',
  `sex` VARCHAR(1) NOT NULL COMMENT 'The biological sex of the passenger. For example, “M” for “male”, “F” for female, etc.',
  PRIMARY KEY (`person_info_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`person_phone_numbers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`person_phone_numbers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`person_phone_numbers` (
  `person_info_id` INT UNSIGNED NOT NULL,
  `phone_number_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`person_info_id`, `phone_number_id`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`phone_numbers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`phone_numbers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`phone_numbers` (
  `phone_number_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone_number` VARCHAR(50) NOT NULL COMMENT 'Phone number, including country code. For example, “+15555555555” as an US example for “+1 555-555-5555” or “+8655555555555” as a China example for “+86 555-5555-5555”.',
  `extension` VARCHAR(45) NULL,
  PRIMARY KEY (`phone_number_id`),
  UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`terminals`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`terminals` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`terminals` (
  `airport_code` VARCHAR(3) NOT NULL COMMENT 'International Airport code. \n\n\nhttps://en.wikipedia.org/wiki/IATA_airport_code',
  `terminal_code` VARCHAR(10) NOT NULL COMMENT 'The technical, alphanumeric code of the terminal.\n\nExamples:\n- “B” for The Los Angeles International Airport’s “Tom Bradley International Terminal”, whose full technical name would be “Terminal B”. (https://www.flylax.com/terminals/terminal-b)\n- “T3E” for “Terminal 3 Boarding Area E',
  `terminal_name` VARCHAR(100) NULL COMMENT 'The common name of the terminal.\n\n“Tom Bradley International” for The Los Angeles International Airport’s “Tom Bradley International Terminal”. (https://www.flylax.com/terminals/terminal-b)',
  `is_international` BOOLEAN NOT NULL COMMENT '`1` if international, `0` if not.',
  `is_domestic` BOOLEAN NOT NULL COMMENT '`1` if domestic, `0` if not.',
  PRIMARY KEY (`airport_code`, `terminal_code`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `airport`.`timezones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `airport`.`timezones` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `airport`.`timezones` (
  `timezone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`timezone`)
  )
ENGINE = InnoDB;

SHOW WARNINGS;

/*
    Set Foreign Keys
*/


ALTER TABLE `airport`.`addresses`
    ADD
        FOREIGN KEY(`country_code`) REFERENCES `airport`.`countries`(`country_code`),
    ADD
        FOREIGN KEY(`timezone`) REFERENCES `airport`.`timezones`(`timezone`)
        ;



ALTER TABLE `airport`.`person_addresses`
    ADD
        FOREIGN KEY(`person_info_id`) REFERENCES `airport`.`person_info`(`person_info_id`),
    ADD
        FOREIGN KEY(`address_id`) REFERENCES `airport`.`addresses`(`address_id`)
    ;

ALTER TABLE `airport`.`person_phone_numbers`
    ADD
        FOREIGN KEY(`person_info_id`) REFERENCES `airport`.`person_info`(`person_info_id`),
    ADD
        FOREIGN KEY(`phone_number_id`) REFERENCES `airport`.`phone_numbers`(`phone_number_id`)
    ;


ALTER TABLE `airport`.`person_email_addresses`
    ADD
        FOREIGN KEY(`person_info_id`) REFERENCES `airport`.`person_info`(`person_info_id`),
    ADD
        FOREIGN KEY(`email_address_id`) REFERENCES `airport`.`email_addresses`(`email_address_id`)
    ;


ALTER TABLE `airport`.`passports`
    ADD
        FOREIGN KEY(`person_info_id`) REFERENCES `airport`.`person_info`(`person_info_id`)
    ;


ALTER TABLE `airport`.`airports`
    ADD
        FOREIGN KEY(`address_id`) REFERENCES `airport`.`addresses`(`address_id`)
    ;

ALTER TABLE `airport`.`terminals`
    ADD
        FOREIGN KEY(`airport_code`) REFERENCES `airport`.`airports`(`airport_code`)
    ;

ALTER TABLE `airport`.`gates`
    ADD
        FOREIGN KEY(`airport_code`, `terminal_code`) REFERENCES `airport`.`terminals`(`airport_code`, `terminal_code`)
    ;

ALTER TABLE `airport`.`airlines`
    ADD
        FOREIGN KEY(`address_id`) REFERENCES `airport`.`addresses`(`address_id`)
    ;

ALTER TABLE `airport`.`airline_staff`
    ADD
        FOREIGN KEY(`person_info_id`) REFERENCES `airport`.`person_info`(`person_info_id`)
    ;

ALTER TABLE `airport`.`flight_staff`
    ADD
        FOREIGN KEY(`airline_staff_id`) REFERENCES `airport`.`airline_staff`(`airline_staff_id`),
    ADD
        FOREIGN KEY(`flight_crew_id`) REFERENCES `airport`.`flight_crew`(`flight_crew_id`)
    ;

ALTER TABLE `airport`.`flight_crew`
    ADD
        FOREIGN KEY(`flight_code`) REFERENCES `airport`.`flights`(`flight_code`)
    ;


ALTER TABLE `airport`.`flights`
    ADD
        FOREIGN KEY(`airline_code`) REFERENCES `airport`.`airlines`(`airline_code`),
    ADD
        FOREIGN KEY(`gate_id`) REFERENCES `airport`.`gates`(`gate_id`)
    ;

ALTER TABLE `airport`.`bookings`
    ADD
        FOREIGN KEY(`passport_number`) REFERENCES `airport`.`passports`(`passport_number`),
    ADD
        FOREIGN KEY(`flight_code`) REFERENCES `airport`.`flights`(`flight_code`)
    ;

ALTER TABLE `airport`.`check_ins`
    ADD
        FOREIGN KEY(`airline_staff_id`) REFERENCES `airport`.`airline_staff`(`airline_staff_id`),
    ADD
        FOREIGN KEY(`booking_id`) REFERENCES `airport`.`bookings`(`booking_id`)
    ;

ALTER TABLE `airport`.`boarding_passes`
    ADD
        FOREIGN KEY(`check_in_id`) REFERENCES `airport`.`check_ins`(`check_in_id`)
    ;

ALTER TABLE `airport`.`baggage`
    ADD
        FOREIGN KEY(`check_in_id`) REFERENCES `airport`.`check_ins`(`check_in_id`)
    ;
