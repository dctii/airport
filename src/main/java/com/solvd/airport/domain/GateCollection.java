package com.solvd.airport.domain;

import jakarta.xml.bind.annotation.*;

import java.util.List;

// TODO: JAXB
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

}
