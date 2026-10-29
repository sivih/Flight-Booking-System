package com.project.flightbookingsystem;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.project.flightbookingsystem.entity.Flight;
import com.project.flightbookingsystem.repository.FlightRepository;

@SpringBootApplication
public class FlightbookingsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightbookingsystemApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner testData(FlightRepository flightsRepository) {
        return args -> {
            List<Flight> flights = flightsRepository.findAll();
            flights.forEach(flight -> {
                System.out.println("Flight name: " + flight.getFlightName());
                System.out.println("Departure date: " + flight.getDateDeparture());
                System.out.println("Return date: " + flight.getReturnDate()); // FIXED
                System.out.println("Passenger count: " + flight.getPassengerCount());
            });
        };
    }
}