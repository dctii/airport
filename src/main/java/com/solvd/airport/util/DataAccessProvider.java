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
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new AddressMyBatisImpl() : new AddressDAOImpl();
    }

    public static AirlineStaffMemberDAO getAirlineStaffMemberDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new AirlineStaffMemberMyBatisImpl() : new AirlineStaffMemberDAOImpl();
    }

    public static BaggageDAO getBaggageDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new BaggageMyBatisImpl() : new BaggageDAOImpl();
    }

    public static BoardingPassDAO getBoardingPassDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new BoardingPassMyBatisImpl() : new BoardingPassDAOImpl();
    }

    public static BookingDAO getBookingDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new BookingMyBatisImpl() : new BookingDAOImpl();
    }

    public static CheckInDAO getCheckInDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new CheckInMyBatisImpl() : new CheckInDAOImpl();
    }

    public static EmailAddressDAO getEmailAddressDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new EmailAddressMyBatisImpl() : new EmailAddressDAOImpl();
    }

    public static FlightDAO getFlightDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new FlightMyBatisImpl() : new FlightDAOImpl();
    }

    public static PassportDAO getPassportDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PassportMyBatisImpl() : new PassportDAOImpl();
    }

    public static CountryDAO getCountryDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new CountryMyBatisImpl() : new CountryDAOImpl();
    }

    public static PersonAddressDAO getPersonAddressDao() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonAddressMyBatisImpl() : new PersonAddressDAOImpl();
    }

    public static PersonEmailAddressDAO getPersonEmailAddressDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonEmailAddressMyBatisImpl() : new PersonEmailAddressDAOImpl();
    }

    public static PersonInfoDAO getPersonInfoDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonInfoMyBatisImpl() : new PersonInfoDAOImpl();
    }

    public static PersonPhoneNumberDAO getPersonPhoneNumberDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PersonPhoneNumberMyBatisImpl() : new PersonPhoneNumberDAOImpl();
    }

    public static PhoneNumberDAO getPhoneNumberDAO() {
        return ConfigConstants.DATABASE_IMPLEMENTATION_VAL_MYBATIS.equals(databaseImpl) ? new PhoneNumberMyBatisImpl() : new PhoneNumberDAOImpl();
    }
}
