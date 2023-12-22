package com.solvd.airport.util;

public final class SQLDataTypes {
    public static final String BIGINT = "BIGINT";
    public static final String BINARY = "BINARY";
    public static final String BIT = "BIT";
    public static final String BLOB = "BLOB";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String CHAR = "CHAR";
    public static final String DATE = "DATE";
    public static final String DATETIME = "DATETIME";
    public static final String DECIMAL = "DECIMAL";
    public static final String DOUBLE = "DOUBLE";
    public static final String ENUM = "ENUM";
    public static final String FLOAT = "FLOAT";
    public static final String INT = "INT";
    public static final String JSON = "JSON";
    public static final String MEDIUMBLOB = "MEDIUMBLOB";
    public static final String MEDIUMINT = "MEDIUMINT";
    public static final String MEDIUMTEXT = "MEDIUMTEXT";
    public static final String SMALLINT = "SMALLINT";
    public static final String TEXT = "TEXT";
    public static final String TIME = "TIME";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String TINYINT = "TINYINT";
    public static final String UUID = "UUID";
    public static final String VARBINARY = "VARBINARY";
    public static final String VARCHAR = "VARCHAR";

    private SQLDataTypes() {
        ExceptionUtils.preventConstantsInstantiation();
    }
}
