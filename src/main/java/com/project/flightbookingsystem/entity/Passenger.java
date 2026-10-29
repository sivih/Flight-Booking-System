package com.project.flightbookingsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name") 
    private String fullName;

    private String phone;

    @Column(name = "id_number") 
    private String idNumber;

    
    @ManyToOne
    @JoinColumn(name = "booking_id")  
    private Booking booking;

    public Passenger() {}

    public Passenger(Integer id, String fullName, String phone, String idNumber, Booking booking) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.idNumber = idNumber;
        this.booking = booking;
    }

  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Passenger{" +
               "id=" + id +
               ", fullName='" + fullName + '\'' +
               ", phone='" + phone + '\'' +
               ", idNumber='" + idNumber + '\'' +
               '}';
    }
}
