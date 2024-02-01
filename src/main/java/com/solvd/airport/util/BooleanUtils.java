package com.solvd.airport.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;

public class BooleanUtils {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BOOLEAN_UTILS);

    // check if array is empty or null, or if an array full of null items
    public static boolean isEmptyOrNullArray(Object[] array) {
        return array == null
                || array.length == 0
                || Arrays.stream(array).allMatch(Objects::isNull);
    }

    public static boolean isNotEmptyOrNullArray(Object[] array) {
        return !isEmptyOrNullArray(array);
    }

    public static boolean isBlankOrEmptyString(String string) {
        return StringUtils.isBlank(string) || StringUtils.isEmpty(string);
    }

    public static boolean isNotBlankOrEmptyString(String string) {
        return !isBlankOrEmptyString(string);
    }


    public static boolean isSameDate(LocalDate comparans, LocalDate comparandum) {
        return comparans.isEqual(comparandum);
    }

    public static boolean isSameDate(ZonedDateTime comparans, ZonedDateTime comparandum) {
        return comparans.isEqual(comparandum);
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        final DayOfWeek[] weekends = {
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
        };

        return Arrays
                .asList(weekends)
                .contains(dayOfWeek);
    }

    public static boolean isValidEmail(String email) {
        boolean hasOneAtSign = email.split(StringConstants.AT_SIGN, -1).length == 2;
        int atSignPosition = email.indexOf(StringConstants.AT_SIGN);
        int dotPosition = email.lastIndexOf(StringConstants.FULL_STOP);

        return hasOneAtSign
                && atSignPosition > 0
                && dotPosition > atSignPosition + 1
                && dotPosition < email.length() - 1;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean isValidPhoneNumber = true;

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            com.google.i18n.phonenumbers.Phonenumber.PhoneNumber numberProto =
                    phoneUtil.parse(phoneNumber, null);
            if (!phoneUtil.isValidNumber(numberProto)) {
                isValidPhoneNumber = false;
            }
        } catch (NumberParseException e) {
            isValidPhoneNumber = false;
        }

        return isValidPhoneNumber;
    }


    private BooleanUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
