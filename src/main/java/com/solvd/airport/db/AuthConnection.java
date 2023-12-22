package com.solvd.airport.db;

import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.ConfigConstants;
import com.solvd.airport.util.ConfigLoader;
import com.solvd.airport.util.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AuthConnection {
    private static final Logger LOGGER = LogManager.getLogger(AuthConnection.class);
    private java.sql.Connection authConnection;


    public AuthConnection() {

        try {
            GetAuthConnection();

        } catch (SQLException e) {

            final String SQL_FAILED_DB_CONNECTION_MSG =
                    "Failed to create the database connection.";
            LOGGER.error(SQL_FAILED_DB_CONNECTION_MSG);
            e.printStackTrace();

        }
    }

    private void GetAuthConnection() throws SQLException {
        Properties properties = ConfigLoader.loadProperties(
                AuthConnection.class,
                ConfigConstants.CONFIG_PROPS_FILE_NAME
        );

        String url = properties.getProperty(ConfigConstants.JDBC_URL_KEY);
        String user = properties.getProperty(ConfigConstants.JDBC_USERNAME_KEY);
        String password = properties.getProperty(ConfigConstants.JDBC_PASSWORD_KEY);

        this.authConnection = DriverManager.getConnection(url, user, password);

        final String SUCCESSFUL_CONNECTION_MSG =
                "Connected to the data base successfully.";
        LOGGER.info(
                "{}" + SUCCESSFUL_CONNECTION_MSG + "{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public java.sql.Connection getConnection() {
        return this.authConnection;
    }

    @Override
    public String toString() {
        Class<?> currClass = AuthConnection.class;
        String[] fieldNames = {};

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
