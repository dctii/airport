package com.solvd.airport.util;

import org.jooq.impl.DSL;

import java.util.function.IntFunction;

public class SQLConstants {

    public static final String PLACEHOLDER = StringConstants.QUESTION_MARK;
    public static final String WILDCARD = StringConstants.ASTERISK_SIGN;

    public static final IntFunction<?> VALUE_PLACEHOLDERS = i -> DSL.val(PLACEHOLDER);
}
