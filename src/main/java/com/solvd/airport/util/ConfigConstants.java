package com.solvd.airport.util;

public final class ConfigConstants {

    public static final String CONFIG_PROPS_FILE_NAME = "config.properties";
    public static final String JDBC_URL_KEY = "jdbc.url";
    public static final String JDBC_USERNAME_KEY = "jdbc.username";
    public static final String JDBC_PASSWORD_KEY = "jdbc.password";
    public static final String DATABASE_IMPLEMENTATION = "database.implementation";
    public static final String DATABASE_IMPLEMENTATION_VAL_MYBATIS = "mybatis";
    public static final String MYBATIS_XML_CONFIG_FILEPATH = FilepathConstants.MYBATIS_XML_CONFIG_FILEPATH;

    private ConfigConstants() {
        ExceptionUtils.preventConstantsInstantiation();
    }
}
