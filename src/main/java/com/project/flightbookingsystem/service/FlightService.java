package com.project.flightbookingsystem.service;

import com.project.flightbookingsystem.entity.Flight;
import com.project.flightbookingsystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(int id) {
        return flightRepository.findById(id);
    }

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight updateFlight(int id, Flight updatedFlight) {
        return flightRepository.findById(id).map(flight -> {
            flight.setFlightName(updatedFlight.getFlightName());
            flight.setSource(updatedFlight.getSource());
            flight.setDestination(updatedFlight.getDestination());
            flight.setDateDeparture(updatedFlight.getDateDeparture());
            flight.setReturnDate(updatedFlight.getReturnDate()); // FIXED
            flight.setTime(updatedFlight.getTime());
            flight.setPassengerCount(updatedFlight.getPassengerCount());
            flight.setPrice(updatedFlight.getPrice());
            return flightRepository.save(flight);
        }).orElseGet(() -> flightRepository.save(updatedFlight));
    }

    public void deleteFlight(int id) {
        flightRepository.deleteById(id);
    }

    public List<String> getDistinctSources() {
        return flightRepository.findDistinctSources();
    }

    public List<String> getDistinctDestinations() {
        return flightRepository.findDistinctDestinations();
    }

    public Optional<Flight> getFlightBySourceAndDestination(String source, String destination) {
        return flightRepository.findFirstBySourceAndDestination(source, destination);
    }

    public Optional<Flight> findFlightByRouteAndDate(String source, String destination, String dateDeparture) {
        return flightRepository.findBySourceAndDestinationAndDateDeparture(source, destination, dateDeparture);
    }
}