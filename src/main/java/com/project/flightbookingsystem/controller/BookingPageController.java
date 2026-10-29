package com.project.flightbookingsystem.controller;

import com.project.flightbookingsystem.entity.Booking;
import com.project.flightbookingsystem.entity.Flight;
import com.project.flightbookingsystem.service.BookingService;
import com.project.flightbookingsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

@Controller
public class BookingPageController {

    private final FlightService flightService;
    private final BookingService bookingService;

    @Autowired
    public BookingPageController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @GetMapping("/bookingform")
    public String showBookingForm(HttpSession session, Model model) {
        String currentUsername = (String) session.getAttribute("loggedInUser");
        
        if (currentUsername == null) {
            return "redirect:/users/login?redirect=/bookingform";
        }

        model.addAttribute("booking", new Booking());
        populateFormFields(model);
        return "bookingform";
    }

    @PostMapping("/bookingform")
    public String submitBookingForm(@ModelAttribute("booking") Booking booking, HttpSession session, Model model) {
        String currentUsername = (String) session.getAttribute("loggedInUser");
        if (currentUsername == null) {
            return "redirect:/users/login?redirect=/bookingform";
        }

        try {
            
            String strDepartureDate = "";
            if (booking.getDepartureDate() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                strDepartureDate = formatter.format(booking.getDepartureDate());
            }

            
            Optional<Flight> flightOpt = flightService.findFlightByRouteAndDate(
                booking.getSource(), 
                booking.getDestination(), 
                strDepartureDate
            );

            
            if (flightOpt.isEmpty()) {
                model.addAttribute("errorMessage", "No active flights found matching your selected route and departure date. Please choose a valid schedule.");
                populateFormFields(model);
                return "bookingform";
            }

            

            bookingService.saveBooking(booking);
            model.addAttribute("successMessage", "Booking successful!");
            
            model.addAttribute("booking", new Booking());
            populateFormFields(model);
            return "bookingform";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            populateFormFields(model);
            return "bookingform";
        }
    }

    private void populateFormFields(Model model) {
        List<String> sources = flightService.getDistinctSources();
        List<String> destinations = flightService.getDistinctDestinations();
        model.addAttribute("sources", sources);
        model.addAttribute("destinations", destinations);
    }
}