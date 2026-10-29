package com.project.flightbookingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.flightbookingsystem.entity.Flight;
import com.project.flightbookingsystem.entity.Booking;
import com.project.flightbookingsystem.entity.User;
import com.project.flightbookingsystem.service.FlightService;
import com.project.flightbookingsystem.service.BookingService;
import com.project.flightbookingsystem.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FlightService flightService;
    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public AdminController(FlightService flightService, BookingService bookingService, UserService userService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    // ==========================================================================
    // 1. Admin Security & Session Mappings
    // ==========================================================================
    
    @GetMapping
    public String showAdminLogin() {
        return "admin-login"; 
    }

    @PostMapping("/login")
    public String processAdminLogin(@RequestParam String username,
                                    @RequestParam String password,
                                    Model model) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin-login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/admin";
    }

    // ==========================================================================
    // 2. Primary Layout Framework Mappings (Tabs Viewports)
    // ==========================================================================

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model) {
        populateDashboardMetrics(model);
        model.addAttribute("currentTab", "overview");
        return "admin"; 
    }

    @GetMapping("/flights")
    public String viewFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        
        populateDashboardMetrics(model);
        model.addAttribute("currentTab", "flights");
        return "admin"; 
    }

    @GetMapping("/bookings")
    public String viewBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        
        populateDashboardMetrics(model);
        model.addAttribute("currentTab", "bookings");
        return "admin"; 
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<User> users = userService.getAllUsers().size() > 0 ? userService.getAllUsers() : List.of();
        model.addAttribute("users", users);
        
        populateDashboardMetrics(model);
        model.addAttribute("currentTab", "users");
        return "admin"; 
    }

    // ==========================================================================
    // 3. Flight Management Sub-Operations (Integrated Panel Views)
    // ==========================================================================

    @GetMapping("/flights/add")
    public String showAddFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        
        populateDashboardMetrics(model);
        model.addAttribute("currentTab", "addFlight"); 
        return "admin"; 
    }

    @GetMapping("/flights/edit/{id}")
    public String showEditFlightForm(@PathVariable("id") Integer id, Model model) {
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isEmpty()) {
            return "redirect:/admin/flights?error=notfound";
        }
        model.addAttribute("flight", flightOpt.get());
        
        populateDashboardMetrics(model);
        model.addAttribute("currentTab", "editFlight"); 
        return "admin"; 
    }

    @PostMapping("/flights/save")
    public String saveFlight(@ModelAttribute("flight") Flight flight, 
                             org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        if (flight.getId() != 0) { 
            flightService.updateFlight(flight.getId(), flight);
            redirectAttributes.addFlashAttribute("successMessage", "Flight parameters updated successfully!");
        } else {  
            flightService.addFlight(flight);
            redirectAttributes.addFlashAttribute("successMessage", "New flight entry registered successfully!");
        }
        return "redirect:/admin/flights";
    }
    
    @GetMapping("/flights/delete/{id}")
    public String deleteFlight(@PathVariable("id") Integer id) {
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isPresent()) {
            flightService.deleteFlight(id);
        }
        return "redirect:/admin/flights";
    }

    // ==========================================================================
    // 4. Ticket Booking Management Operations
    // ==========================================================================

    @GetMapping("/bookings/edit/{id}")
    public String toggleBookingConfirmation(@PathVariable("id") Integer id) {
        Optional<Booking> bookingOpt = bookingService.getBookingById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if ("Confirmed".equals(booking.getConfirmation())) {
                booking.setConfirmation("Pending");
            } else {
                booking.setConfirmation("Confirmed");
            }
            bookingService.updateBooking(booking);
        }
        return "redirect:/admin/bookings";
    }

    @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable("id") Integer id) {
        Optional<Booking> bookingOpt = bookingService.getBookingById(id);
        if (bookingOpt.isPresent()) {
            bookingService.deleteBooking(id);
        }
        return "redirect:/admin/bookings";
    }

    // ==========================================================================
    // Private Framework Utilities
    // ==========================================================================
    
    private void populateDashboardMetrics(Model model) {
        model.addAttribute("totalFlights", flightService.getAllFlights().size());
        model.addAttribute("totalBookings", bookingService.getAllBookings().size());
        model.addAttribute("totalUsers", userService.getAllUsers().size());
    }
}