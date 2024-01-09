package com.solvd.airport.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

@XmlRootElement(name = "gates")
public class Gates {
    private List<Gate> gates;

    public Gates() {
    }

    public Gates(List<Gate> gates) {
        this.gates = gates;
    }

    @XmlElementWrapper(name = "gates")
    @XmlElement(name = "gate")
    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

}
