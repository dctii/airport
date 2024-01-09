package com.solvd.airport.util;

import com.solvd.airport.domain.Gate;
import com.solvd.airport.domain.GateCollection;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.Optional;


public class JaxbUtils {

    public static Optional<Gate> getGateByCodes(String airportCode, String terminalCode, String gateCode) {
        return getGateByCodes(FilepathConstants.GATES_XML, airportCode, terminalCode, gateCode);
    }

    public static Optional<Gate> getGateByCodes(String filepath, String airportCode, String terminalCode, String gateCode) {
        GateCollection gateCollection = extractGates(filepath);

        return gateCollection.getGates().stream()
                .filter(gate ->
                        airportCode.equals(gate.getAirportCode())
                                && terminalCode.equals(gate.getTerminalCode())
                                && gateCode.equals(gate.getGateCode()))
                .findFirst();
    }

    public static GateCollection extractGates() {
        return extractGates(FilepathConstants.GATES_XML);
    }

    public static GateCollection extractGates(String filepath) {
        try {
            InputStream inputStream = JaxbUtils.class
                    .getClassLoader()
                    .getResourceAsStream(filepath);


            JAXBContext context = JAXBContext.newInstance(GateCollection.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            GateCollection gateCollection = (GateCollection) unmarshaller.unmarshal(inputStream);

            return gateCollection;

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private JaxbUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }


}
