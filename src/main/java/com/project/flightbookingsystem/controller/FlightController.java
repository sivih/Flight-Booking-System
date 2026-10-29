package com.project.flightbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.flightbookingsystem.entity.Flight;
import com.project.flightbookingsystem.service.FlightService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable int id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable int id, @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
    }

    
    @GetMapping("/price")
    public ResponseEntity<?> getFlightPrice(@RequestParam String source, @RequestParam String destination) {
        // Uses your existing service method that already works!
        Optional<Flight> flightOpt = flightService.getFlightBySourceAndDestination(source, destination);

        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            
           
            Map<String, Object> response = new HashMap<>();
            response.put("price", flight.getPrice());
            response.put("dateDeparture", flight.getDepartureDate()); // Verified from your console logs that it's departureDate!
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

}