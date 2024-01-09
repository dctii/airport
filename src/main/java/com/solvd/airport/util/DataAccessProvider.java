package com.solvd.airport.util;

import com.solvd.airport.persistence.*;
import com.solvd.airport.persistence.impl.*;
import com.solvd.airport.persistence.mybatis.*;

import java.util.Properties;

public class DataAccessProvider {
    private static String databaseImpl;

    static {
        Properties config = ConfigLoader.loadProperties(
                DataAccessProvider.class,
                ConfigConstants.CONFIG_PROPS_FILE_NAME
        );
        databaseImpl = config.getProperty(ConfigConstants.DATABASE_IMPLEMENTATION);
    }

    public static AddressDAO getAddressDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new AddressMyBatisImpl() : new AddressJDBCImpl();
    }

    public static AirlineDAO getAirlineDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new AirlineMyBatisImpl() : new AirlineJDBCImpl();
    }
    public static AirportDAO getAirportDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new AirportMyBatisImpl() : new AirportJDBCImpl();
    }

    public static AirlineStaffMemberDAO getAirlineStaffMemberDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new AirlineStaffMemberMyBatisImpl() : new AirlineStaffMemberJDBCImpl();
    }

    public static BaggageDAO getBaggageDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new BaggageMyBatisImpl() : new BaggageJDBCImpl();
    }

    public static BoardingPassDAO getBoardingPassDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new BoardingPassMyBatisImpl() : new BoardingPassJDBCImpl();
    }

    public static BookingDAO getBookingDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new BookingMyBatisImpl() : new BookingJDBCImpl();
    }

    public static CheckInDAO getCheckInDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new CheckInMyBatisImpl() : new CheckInJDBCImpl();
    }

    public static EmailAddressDAO getEmailAddressDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new EmailAddressMyBatisImpl() : new EmailAddressJDBCImpl();
    }

    public static FlightDAO getFlightDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new FlightMyBatisImpl() : new FlightJDBCImpl();
    }

    public static GateDAO getGateDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new GateMyBatisImpl() : new GateJDBCImpl();
    }

    public static PassportDAO getPassportDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PassportMyBatisImpl() : new PassportJDBCImpl();
    }

    public static CountryDAO getCountryDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new CountryMyBatisImpl() : new CountryJDBCImpl();
    }

    public static TerminalDAO getTerminalDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new TerminalMyBatisImpl() : new TerminalJDBCImpl();
    }

    public static TimezoneDAO getTimezoneDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new TimezoneMyBatisImpl() : new TimezoneJDBCImpl();
    }

    public static PersonAddressDAO getPersonAddressDao() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonAddressMyBatisImpl() : new PersonAddressJDBCImpl();
    }

    public static PersonEmailAddressDAO getPersonEmailAddressDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonEmailAddressMyBatisImpl() : new PersonEmailAddressJDBCImpl();
    }

    public static PersonInfoDAO getPersonInfoDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonInfoMyBatisImpl() : new PersonInfoJDBCImpl();
    }

    public static PersonPhoneNumberDAO getPersonPhoneNumberDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonPhoneNumberMyBatisImpl() : new PersonPhoneNumberJDBCImpl();
    }

    public static PhoneNumberDAO getPhoneNumberDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PhoneNumberMyBatisImpl() : new PhoneNumberJDBCImpl();
    }
}
