package com.solvd.airport.util;

public class SQLOps {
    // SQL Operators
    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN = ">";
    public static final String EQUALS = "=";
    public static final String NOT_EQUALS = LESS_THAN + GREATER_THAN;
    public static final String LESS_THAN_OR_EQUAL = LESS_THAN + EQUALS;
    public static final String GREATER_THAN_OR_EQUAL = GREATER_THAN + EQUALS;
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String MODULO = "%";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "NOT";
    public static final String LIKE = "LIKE";
    public static final String IN = "IN";
    public static final String BETWEEN = "BETWEEN";
    public static final String IS_NULL = "IS" + StringConstants.SINGLE_WHITESPACE + "NULL";
    public static final String IS_NOT_NULL = "IS"
            + StringConstants.SINGLE_WHITESPACE + NOT
            + StringConstants.SINGLE_WHITESPACE + "NULL";

    private SQLOps() {
        ExceptionUtils.preventConstantsInstantiation();
    }
}
