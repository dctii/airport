package com.solvd.airport.util;

import org.apache.commons.lang3.StringUtils;

public class SQLConstants {

    /*
        SQL Data Types
    */
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

     /*
        "SQL Keywords Reference"
        https://www.w3schools.com/sql/sql_ref_keywords.asp
    */

    public static final String DATABASE = "DATABASE";
    public static final String COLUMN = "COLUMN";
    public static final String SET = "SET";
    public static final String UNIQUE = "UNIQUE";
    public static final String INDEX = "INDEX";
    public static final String TABLE = "TABLE";
    public static final String SELECT = "SELECT";
    public static final String VIEW = "VIEW";
    public static final String TOP = "TOP";
    public static final String CREATE = "CREATE";
    public static final String ADD = "ADD";
    public static final String ADD_CONSTRAINT =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, ADD, "CONSTRAINT");
    public static final String ALL = "ALL";
    public static final String ALTER = "ALTER";
    public static final String ALTER_COLUMN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, ALTER, COLUMN);
    public static final String ALTER_TABLE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, ALTER, TABLE);
    public static final String ANY = "ANY";
    public static final String AS = "AS";
    public static final String ASC = "ASC";
    public static final String BACKUP_DATABASE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "BACKUP", DATABASE);
    public static final String CASE = "CASE";
    public static final String CHECK = "CHECK";
    public static final String PROCEDURE = "PROCEDURE";
    public static final String CREATE_DATABASE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, CREATE, DATABASE);
    public static final String CREATE_INDEX =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, CREATE, INDEX);
    public static final String CREATE_TABLE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, CREATE, TABLE);
    public static final String CREATE_PROCEDURE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, CREATE, PROCEDURE);
    public static final String CREATE_UNIQUE_INDEX =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, CREATE, UNIQUE, INDEX);
    public static final String CREATE_VIEW =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, CREATE, VIEW);
    public static final String DEFAULT = "DEFAULT";
    public static final String DELETE = "DELETE";
    public static final String DESC = "DESC";
    public static final String DISTINCT = "DISTINCT";
    public static final String DROP = "DROP";

    public static final String DROP_COLUMN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, COLUMN);
    public static final String DROP_CONSTRAINT =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, "CONSTRAINT");
    public static final String DROP_DATABASE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, DATABASE);
    public static final String DROP_DEFAULT =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, DEFAULT);
    public static final String DROP_INDEX =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, INDEX);
    public static final String DROP_TABLE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, TABLE);
    public static final String DROP_VIEW =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, DROP, VIEW);

    public static final String JOIN = "JOIN";
    public static final String EXEC = "EXEC";
    public static final String EXISTS = "EXISTS";
    public static final String FOREIGN_KEY =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "FOREIGN", "KEY");

    public static final String FROM = "FROM";
    public static final String FULL_OUTER_JOIN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "FULL", "OUTER", JOIN);
    public static final String GROUP_BY =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "GROUP", "BY");
    public static final String HAVING = "HAVING";
    public static final String IN = "IN";
    public static final String INSERT_INTO =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "INSERT", "INTO");
    public static final String INSERT_INTO_SELECT =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, INSERT_INTO, SELECT);
    public static final String NOT = "NOT";
    public static final String NOT_NULL =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, NOT, StringConstants.NULL_STRING.toUpperCase());
    public static final String INNER_JOIN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "INNER", JOIN);
    public static final String LEFT_JOIN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "LEFT", JOIN);
    public static final String LIMIT = "LIMIT";

    public static final String ORDER_BY =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "ORDER", "BY");
    public static final String OUTER_JOIN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "OUTER", JOIN);
    public static final String PRIMARY_KEY =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "PRIMARY", "KEY");
    public static final String RIGHT_JOIN =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "RIGHT", JOIN);
    public static final String ROWNUM = "ROWNUM";
    public static final String SELECT_DISTINCT =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, SELECT, DISTINCT);
    public static final String SELECT_INTO =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, SELECT, "INTO");
    public static final String SELECT_TOP =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, SELECT, TOP);
    public static final String TRUNCATE_TABLE =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "TRUNCATE", TABLE);
    public static final String UNION = "UNION";
    public static final String UNION_ALL =
            StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, UNION, ALL);

    public static final String UPDATE = "UPDATE";
    public static final String VALUES = "VALUES";
    public static final String WHERE = "WHERE";

    // SQL Operators
    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN = ">";
    public static final String EQUALS = "=";
    public static final String NOT_EQUALS =
            StringUtils.join(LESS_THAN, GREATER_THAN);
    public static final String LESS_THAN_OR_EQUAL =
            StringUtils.join(LESS_THAN, EQUALS);
    public static final String GREATER_THAN_OR_EQUAL =
            StringUtils.join(GREATER_THAN, EQUALS);
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String MODULO = "%";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String LIKE = "LIKE";
    public static final String BETWEEN = "BETWEEN";
    public static final String IS_NULL = StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "IS", StringConstants.NULL_STRING.toUpperCase());
    public static final String IS_NOT_NULL = StringUtils.joinWith(StringConstants.SINGLE_WHITESPACE, "IS", NOT_NULL);

    /*
        "MySQL Functions" (Reference List)
        https://www.w3schools.com/sql/sql_ref_mysql.asp
    */

    public static final String ASCII = "ASCII";
    public static final String CHAR_LENGTH = "CHAR_LENGTH";
    public static final String CHARACTER_LENGTH = "CHARACTER_LENGTH";
    public static final String CONCAT_WS = "CONCAT_WS";
    public static final String FIELD = "FIELD";
    public static final String FIND_IN_SET = "FIND_IN_SET";
    public static final String FORMAT = "FORMAT";
    public static final String INSERT = "INSERT";
    public static final String INSTR = "INSTR";
    public static final String LCASE = "LCASE";
    public static final String LEFT = "LEFT";
    public static final String LENGTH = "LENGTH";
    public static final String LOCATE = "LOCATE";
    public static final String LOWER = "LOWER";
    public static final String LPAD = "LPAD";
    public static final String LTRIM = "LTRIM";
    public static final String MID = "MID";
    public static final String POSITION = "POSITION";
    public static final String REPEAT = "REPEAT";
    public static final String REPLACE = "REPLACE";
    public static final String REVERSE = "REVERSE";
    public static final String RIGHT = "RIGHT";
    public static final String RPAD = "RPAD";
    public static final String RTRIM = "RTRIM";
    public static final String SPACE = "SPACE";
    public static final String STRCMP = "STRCMP";
    public static final String SUBSTR = "SUBSTR";
    public static final String SUBSTRING = "SUBSTRING";
    public static final String SUBSTRING_INDEX = "SUBSTRING_INDEX";
    public static final String TRIM = "TRIM";
    public static final String UCASE = "UCASE";
    public static final String UPPER = "UPPER";
}
