package com.solvd.airport.util;

public final class SQLKeys {
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
    public static final String ADD_CONSTRAINT = ADD + StringConstants.SINGLE_WHITESPACE + "CONSTRAINT";
    public static final String ALL = "ALL";
    public static final String ALTER = "ALTER";
    public static final String ALTER_COLUMN = ALTER + StringConstants.SINGLE_WHITESPACE + COLUMN;
    public static final String ALTER_TABLE = ALTER + StringConstants.SINGLE_WHITESPACE + TABLE;
    public static final String AND = SQLOps.AND;
    public static final String ANY = "ANY";
    public static final String AS = "AS";
    public static final String ASC = "ASC";
    public static final String BACKUP_DATABASE = "BACKUP" + StringConstants.SINGLE_WHITESPACE + DATABASE;
    public static final String BETWEEN = SQLOps.BETWEEN;
    public static final String CASE = "CASE";
    public static final String CHECK = "CHECK";
    public static final String CREATE_DATABASE = CREATE + StringConstants.SINGLE_WHITESPACE + DATABASE;
    public static final String CREATE_INDEX = CREATE + StringConstants.SINGLE_WHITESPACE + INDEX;
    public static final String CREATE_TABLE = CREATE + StringConstants.SINGLE_WHITESPACE + TABLE;
    public static final String CREATE_PROCEDURE = CREATE + " PROCEDURE";
    public static final String CREATE_UNIQUE_INDEX = CREATE + StringConstants.SINGLE_WHITESPACE + UNIQUE + StringConstants.SINGLE_WHITESPACE + INDEX;
    public static final String CREATE_VIEW = CREATE + StringConstants.SINGLE_WHITESPACE + VIEW;
    public static final String DEFAULT = "DEFAULT";
    public static final String DELETE = "DELETE";
    public static final String DESC = "DESC";
    public static final String DISTINCT = "DISTINCT";
    public static final String DROP = "DROP";
    public static final String DROP_COLUMN = DROP + StringConstants.SINGLE_WHITESPACE + COLUMN;
    public static final String DROP_CONSTRAINT = DROP + StringConstants.SINGLE_WHITESPACE + "CONSTRAINT";
    public static final String DROP_DATABASE = DROP + StringConstants.SINGLE_WHITESPACE + DATABASE;
    public static final String DROP_DEFAULT = DROP + StringConstants.SINGLE_WHITESPACE + DEFAULT;
    public static final String DROP_INDEX = DROP + StringConstants.SINGLE_WHITESPACE + INDEX;
    public static final String DROP_TABLE = DROP + StringConstants.SINGLE_WHITESPACE + TABLE;
    public static final String DROP_VIEW = DROP + StringConstants.SINGLE_WHITESPACE + VIEW;
    public static final String JOIN = "JOIN";
    public static final String EXEC = "EXEC";
    public static final String EXISTS = "EXISTS";
    public static final String FOREIGN_KEY = "FOREIGN" + StringConstants.SINGLE_WHITESPACE + "KEY";
    public static final String FROM = "FROM";
    public static final String FULL_OUTER_JOIN = "FULL" + StringConstants.SINGLE_WHITESPACE + "OUTER" + StringConstants.SINGLE_WHITESPACE + JOIN;
    public static final String GROUP_BY = "GROUP" + StringConstants.SINGLE_WHITESPACE + "BY";
    public static final String HAVING = "HAVING";
    public static final String IN = "IN";
    public static final String INSERT_INTO = "INSERT" + StringConstants.SINGLE_WHITESPACE + "INTO";
    public static final String INSERT_INTO_SELECT = INSERT_INTO + StringConstants.SINGLE_WHITESPACE + SELECT;
    public static final String NOT = SQLOps.NOT;
    public static final String NOT_NULL = SQLOps.NOT + "NULL";
    public static final String IS_NULL = SQLOps.IS_NULL;
    public static final String IS_NOT_NULL = SQLOps.IS_NOT_NULL;
    public static final String INNER_JOIN = "INNER" + StringConstants.SINGLE_WHITESPACE + JOIN;
    public static final String LEFT_JOIN = "LEFT" + StringConstants.SINGLE_WHITESPACE + JOIN;
    public static final String LIKE = SQLOps.LIKE;
    public static final String LIMIT = "LIMIT";

    public static final String OR = SQLOps.OR;
    public static final String ORDER_BY = "ORDER" + StringConstants.SINGLE_WHITESPACE + "BY";
    public static final String OUTER_JOIN = "OUTER" + StringConstants.SINGLE_WHITESPACE + JOIN;
    public static final String PRIMARY_KEY = "PRIMARY" + StringConstants.SINGLE_WHITESPACE + "KEY";
    public static final String PROCEDURE = "PROCEDURE";
    public static final String RIGHT_JOIN = "RIGHT" + StringConstants.SINGLE_WHITESPACE + JOIN;
    public static final String ROWNUM = "ROWNUM";
    public static final String SELECT_DISTINCT = SELECT + StringConstants.SINGLE_WHITESPACE + DISTINCT;
    public static final String SELECT_INTO = SELECT + StringConstants.SINGLE_WHITESPACE + "INTO";
    public static final String SELECT_TOP = SELECT + StringConstants.SINGLE_WHITESPACE + TOP;
    public static final String TRUNCATE_TABLE = "TRUNCATE" + StringConstants.SINGLE_WHITESPACE + TABLE;
    public static final String UNION = "UNION";
    public static final String UNION_ALL = UNION + StringConstants.SINGLE_WHITESPACE + ALL;
    public static final String UPDATE = "UPDATE";
    public static final String VALUES = "VALUES";
    public static final String WHERE = "WHERE";

    private SQLKeys() {
        ExceptionUtils.preventConstantsInstantiation();
    }
}
