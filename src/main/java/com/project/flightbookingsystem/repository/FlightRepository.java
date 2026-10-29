package com.project.flightbookingsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.flightbookingsystem.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    
    @Query("SELECT DISTINCT f.source FROM Flight f")
    List<String> findDistinctSources();

    @Query("SELECT DISTINCT f.destination FROM Flight f")
    List<String> findDistinctDestinations();
    
    Optional<Flight> findFirstBySourceAndDestination(String source, String destination);
 
    Optional<Flight> findBySourceAndDestinationAndDateDeparture(String source, String destination, String dateDeparture);
}