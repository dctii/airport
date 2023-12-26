package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Flight;
import com.solvd.airport.persistence.mappers.FlightDAO;

import java.sql.*;

public class FlightDAOImpl implements FlightDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_FLIGHT_SQL =
            "INSERT INTO flights (flight_code, departure_time, arrival_time, destination, " +
                    "airline_code, gate_id, aircraft_model, passenger_capacity, tail_number) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_CODE_SQL =
            "SELECT * FROM flights WHERE flight_code = ?";
    private static final String UPDATE_FLIGHT_SQL =
            "UPDATE flights SET departure_time = ?, arrival_time = ?, destination = ?, airline_code = ?, " +
                    "gate_id = ?, aircraft_model = ?, passenger_capacity = ?, tail_number = ? " +
                    "WHERE flight_code = ?";
    private static final String DELETE_FLIGHT_SQL =
            "DELETE FROM flights WHERE flight_code = ?";

    @Override
    public void createFlight(Flight flight) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_FLIGHT_SQL)) {
            ps.setString(1, flight.getFlightCode());
            ps.setTimestamp(2, flight.getDepartureTime());
            ps.setTimestamp(3, flight.getArrivalTime());
            ps.setString(4, flight.getDestination());
            ps.setString(5, flight.getAirlineCode());
            ps.setInt(6, flight.getGateId());
            ps.setString(7, flight.getAircraftModel());
            ps.setInt(8, flight.getPassengerCapacity());
            ps.setString(9, flight.getTailNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flight getFlightByCode(String flightCode) {
        Flight flight = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_CODE_SQL)) {
            ps.setString(1, flightCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flight = new Flight();
                    flight.setFlightCode(rs.getString("flight_code"));
                    flight.setDepartureTime(rs.getTimestamp("departure_time"));
                    flight.setArrivalTime(rs.getTimestamp("arrival_time"));
                    flight.setDestination(rs.getString("destination"));
                    flight.setAirlineCode(rs.getString("airline_code"));
                    flight.setGateId(rs.getInt("gate_id"));
                    flight.setAircraftModel(rs.getString("aircraft_model"));
                    flight.setPassengerCapacity(rs.getInt("passenger_capacity"));
                    flight.setTailNumber(rs.getString("tail_number"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    @Override
    public void updateFlight(Flight flight) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_FLIGHT_SQL)) {
            ps.setTimestamp(1, flight.getDepartureTime());
            ps.setTimestamp(2, flight.getArrivalTime());
            ps.setString(3, flight.getDestination());
            ps.setString(4, flight.getAirlineCode());
            ps.setInt(5, flight.getGateId());
            ps.setString(6, flight.getAircraftModel());
            ps.setInt(7, flight.getPassengerCapacity());
            ps.setString(8, flight.getTailNumber());
            ps.setString(9, flight.getFlightCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFlight(String flightCode) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_FLIGHT_SQL)) {
            ps.setString(1, flightCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
