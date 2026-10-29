package com.project.flightbookingsystem.controller;

import com.project.flightbookingsystem.entity.Booking;
import com.project.flightbookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/book-flight")
    public ModelAndView showBookFlightPage() {
        return new ModelAndView("book-flight");  
    }
    
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Integer id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.saveBooking(booking);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Integer id, @RequestBody Booking bookingDetails) {
        return bookingService.getBookingById(id).map(booking -> {
            booking.setUserId(bookingDetails.getUserId());
            booking.setUsername(bookingDetails.getUsername());
            booking.setSource(bookingDetails.getSource());
            booking.setDestination(bookingDetails.getDestination());
            booking.setDepartureDate(bookingDetails.getDepartureDate());
            booking.setReturnDate(bookingDetails.getReturnDate()); // FIXED
            booking.setFlightClass(bookingDetails.getFlightClass());
            booking.setTickets(bookingDetails.getTickets());
            booking.setTotalPrice(bookingDetails.getTotalPrice());
            booking.setPaymentMethod(bookingDetails.getPaymentMethod());
            booking.setConfirmation(bookingDetails.getConfirmation());
            Booking updatedBooking = bookingService.updateBooking(booking);
            return ResponseEntity.ok(updatedBooking);
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Integer id) {
        if (bookingService.getBookingById(id).isPresent()) {
            bookingService.deleteBooking(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}