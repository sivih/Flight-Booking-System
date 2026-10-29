package com.project.flightbookingsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String flightName;
    private String source;
    private String destination;
    private String dateDeparture; 

    @Column(name = "dateReturn") 
    private String returnDate; 
    
    private String time;
    private Integer passengerCount;
    private double price;

    public Flight() {}

    public Flight(String flightName, String source, String destination,
                  String dateDeparture, String returnDate, String time,
                  Integer passengerCount, double price) {
        this.flightName = flightName;
        this.source = source;
        this.destination = destination;
        this.dateDeparture = dateDeparture;
        this.returnDate = returnDate;
        this.time = time;
        this.passengerCount = passengerCount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    @Transient 
    public String getDepartureDate() {
        return this.dateDeparture;
    }

    public void setDepartureDate(String departureDate) {
        this.dateDeparture = departureDate;
    }

    
    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}