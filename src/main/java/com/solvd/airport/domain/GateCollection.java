package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.StringFormatters;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

// JAXB
@XmlRootElement(name = "gates")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class GateCollection {
    private List<Gate> gates;

    public GateCollection() {
    }

    public GateCollection(List<Gate> gates) {
        this.gates = gates;
    }


    @XmlElement(name = "gate")
    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.GATE_COLLECTION;
        String[] fieldNames = {
                "gates"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }

}
