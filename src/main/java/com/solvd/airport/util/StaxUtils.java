package com.solvd.airport.util;

import com.solvd.airport.domain.Terminal;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaxUtils {

    public static Optional<Terminal> getTerminalByCodes(String airportCode, String terminalCode) {
        List<Terminal> terminals = extractTerminals();
        return terminals.stream()
                .filter(terminal ->
                        airportCode.equals(terminal.getAirportCode())
                                && terminalCode.equals(terminal.getTerminalCode()))
                .findFirst();
    }

    public static Optional<Terminal> getTerminalByCodes(String filepath, String airportCode, String terminalCode) {
        List<Terminal> terminals = extractTerminals(filepath);
        return terminals.stream()
                .filter(terminal ->
                        airportCode.equals(terminal.getAirportCode())
                                && terminalCode.equals(terminal.getTerminalCode()))
                .findFirst();
    }
    public static Optional<Terminal> getTerminalByAirportAndName(String airportCode, String terminalName) {
        List<Terminal> terminals = extractTerminals();
        return terminals.stream()
                .filter(terminal -> airportCode.equals(terminal.getAirportCode())
                        && terminalName.equals(terminal.getTerminalName()))
                .findFirst();
    }

    public static Optional<Terminal> getTerminalByAirportAndName(String filepath, String airportCode, String terminalName) {
        List<Terminal> terminals = extractTerminals(filepath);
        return terminals.stream()
                .filter(terminal ->
                        airportCode.equals(terminal.getAirportCode())
                                && terminalName.equals(terminal.getTerminalName()))
                .findFirst();
    }

    public static List<Terminal> extractTerminals() {
        return extractTerminals(FilepathConstants.TERMINALS_XML);
    }

    public static List<Terminal> extractTerminals(String filepath) {
        List<Terminal> terminals = new ArrayList<>();

        try (
                InputStream inputStream = StaxUtils.class.getClassLoader().getResourceAsStream(filepath)
        ) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + filepath);
            }

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(inputStream);

            Terminal terminal = null;
            String elementName = StringConstants.EMPTY_STRING;

            final String elementNameString = "terminal";
            final String airportCodeFieldString = "airportCode";
            final String terminalCodeFieldString = "terminalCode";
            final String terminalNameFieldString = "terminalName";
            final String isInternationalFieldString = "isInternational";
            final String isDomesticFieldString = "isDomestic";

            while (reader.hasNext()) {
                int eventType = reader.next();

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementNameString.equals(elementName)) {
                            terminal = new Terminal();
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        String text = reader.getText().trim();
                        if (!text.isEmpty()) {
                            switch (elementName) {
                                case airportCodeFieldString:
                                    terminal.setAirportCode(text);
                                    break;
                                case terminalCodeFieldString:
                                    terminal.setTerminalCode(text);
                                    break;
                                case terminalNameFieldString:
                                    terminal.setTerminalName(text);
                                    break;
                                case isInternationalFieldString:
                                    terminal.setInternational(Boolean.parseBoolean(text));
                                    break;
                                case isDomesticFieldString:
                                    terminal.setDomestic(Boolean.parseBoolean(text));
                                    break;
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if (elementNameString.equals(reader.getLocalName())) {
                            terminals.add(terminal);
                        }
                        break;
                }
            }

        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        }

        return terminals;
    }

    private StaxUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
