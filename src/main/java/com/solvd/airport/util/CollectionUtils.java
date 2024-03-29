package com.solvd.airport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class CollectionUtils {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.COLLECTION_UTILS);

    public static <T> Set<T> setToNullIfEmpty(Set<T> collection) {
        if (setIsNullOrEmpty(collection)) {
            collection = null;
        }
        return collection;
    }

    public static <T> boolean setIsNullOrEmpty(Set<T> collection) {
        return collection == null
                || collection.isEmpty();
    }

    private CollectionUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
