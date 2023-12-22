package com.solvd.airport.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadProperties(Class<?> clazz, String pathToResourceFile) {
        Properties properties = new Properties();
        try (InputStream input =
                     clazz.getClassLoader().getResourceAsStream(pathToResourceFile)
        ) {

            if (input == null) {
                final String UNABLE_TO_FIND_FILE_MSG =
                        "Unable to find "
                                + StringFormatters.nestInSingleQuotations(pathToResourceFile);
                throw new IllegalStateException(UNABLE_TO_FIND_FILE_MSG);
            }
            properties.load(input);

        } catch (Exception e) {

            final String UNABLE_TO_LOAD_PROPERTIES_FILE_MSG =
                    "Failed to load properties file "
                            + StringFormatters.nestInSingleQuotations(pathToResourceFile);
            throw new RuntimeException(UNABLE_TO_LOAD_PROPERTIES_FILE_MSG, e);
        }
        return properties;
    }

    private ConfigLoader() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
