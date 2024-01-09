package com.solvd.airport.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.airport.domain.Airline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class JacksonUtils {
    private static final Logger LOGGER = LogManager.getLogger(MenuUtils.class);

    public static List<Airline> extractAirlines(String resourcePath) {
        return parseJson(resourcePath, Airline.class);
    }
    public static <T> List<T> parseJson(String resourcePath, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, clazz);

        try (
                InputStream inputStream = JacksonUtils.class
                        .getClassLoader()
                        .getResourceAsStream(resourcePath)
        ) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return mapper.readValue(inputStream, type);
        } catch (IOException e) {
            LOGGER.error("Error reading resource file: " + resourcePath, e);
            throw new RuntimeException("Error reading resource file: " + resourcePath, e);
        }
    }

    public static Optional<Airline> getAirlineByCode(String airlineCode) {
        return getAirlineByCode(FilepathConstants.AIRLINES_JSON, airlineCode);
    }

    public static Optional<Airline> getAirlineByCode(String filepath, String airlineCode) {
        List<Airline> airlines = extractAirlines(filepath);
        return airlines.stream()
                .filter(airline -> airlineCode.equals(airline.getAirlineCode()))
                .findFirst();
    }

    public static Optional<Airline> getAirlineByName(String airlineName) {
        return getAirlineByName(FilepathConstants.AIRLINES_JSON, airlineName);
    }

    public static Optional<Airline> getAirlineByName(String filepath, String airlineName) {
        List<Airline> airlines = extractAirlines(filepath);
        return airlines.stream()
                .filter(airline -> airlineName.equals(airline.getAirlineName()))
                .findFirst();
    }

    private JacksonUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
