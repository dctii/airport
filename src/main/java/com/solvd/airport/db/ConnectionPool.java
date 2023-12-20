package com.solvd.airport.db;

import com.solvd.airport.utilities.AnsiCodes;
import com.solvd.airport.utilities.StringConstants;
import com.solvd.airport.utilities.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

// TODO: modify this for database

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool connectionPool = null;
    private static final int MAX_CONNECTIONS = 5;

    private final Queue<Connection> connections = new LinkedList<>();

    private ConnectionPool() {
        IntStream.range(0, MAX_CONNECTIONS).forEach(i -> {
            String connectionName =
                    Connection.class.getSimpleName() + StringConstants.SINGLE_WHITESPACE + (i + 1);
            Connection connection = new Connection(connectionName);
            connections.add(connection);
        });
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection getConnection() {
        while (connections.isEmpty()) {
            try {
                /*
                    Thread: currentThread
                    https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#currentThread--

                    Thread: getName
                    https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#getName--
                */
                String currentThreadName = StringFormatters.nestInSingleQuotations(
                        Thread.currentThread().getName()
                );

                LOGGER.info(
                        "{}{} is waiting for a connection.{}",
                        AnsiCodes.YELLOW,
                        currentThreadName,
                        AnsiCodes.RESET_ALL
                );
                wait();
            } catch (InterruptedException e) {
                /*
                    Thread: interrupt
                    https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#interrupt--
                */
                Thread.currentThread().interrupt();
            }
        }
        /*
            "Queue<E>: poll"
            https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html#poll--
         */
        return connections.poll();
    }

    public synchronized void releaseConnection(Connection connection) {
        connections.offer(connection);

        String currentThreadName = StringFormatters.nestInSingleQuotations(
                Thread.currentThread().getName()
        );
        String connectionName = StringFormatters.nestInSingleQuotations(
                connection.getName()
        );

        LOGGER.info(
                "{}{} released {}{}",
                AnsiCodes.BLUE,
                currentThreadName,
                connectionName,
                AnsiCodes.RESET_ALL
        );

        notifyAll();
    }


    public String toString() {
        Class<?> currClass = ConnectionPool.class;
        String[] fieldNames = {
                "connections"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
