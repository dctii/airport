package com.solvd.airport;

import com.solvd.airport.utilities.AnsiCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: create driver pool

/*
    TODO:
     - only works with the `service/` layer
     - should be able to create object of the mapper and do higher level business logic
     - perform some business flow
*/

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info(
                "\n {}{}Hello World{} \n",
                AnsiCodes.YELLOW, AnsiCodes.BOLD, AnsiCodes.RESET_ALL
        );
    }
}
