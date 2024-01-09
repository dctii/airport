package com.solvd.airport.util;

public final class  FilepathConstants {
    /*
        From `resources/`
    */
    public static final String MYBATIS_XML_CONFIG_FILEPATH = "mybatis-config.xml";
    public static final String OUR_AIRPORTS_CSV_DATA_FILEPATH = "data/ourairports-airports-data.csv";

    // use `gates.xml` with JAXB
    public static final String GATES_XML = "data/gates.xml";

    // use `terminals.xml` with StAX
    public static final String TERMINALS_XML = "data/terminals.xml";

    // use `airlines.json` with Jackson
    public static final String AIRLINES_JSON = "data/airlines.json";
    private FilepathConstants() {
        ExceptionUtils.preventConstantsInstantiation();
    }
}
