package com.project.flightbookingsystem.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.flightbookingsystem.entity.Booking;
import com.project.flightbookingsystem.entity.User;
import com.project.flightbookingsystem.service.BookingService;
import com.project.flightbookingsystem.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        LocalDate maxDate = LocalDate.now().minusYears(18);
        model.addAttribute("maxDate", maxDate);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        try {
            userService.registerUser(user);
            session.setAttribute("loggedInUser", user.getUsername());
            return "redirect:/users/profile";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            LocalDate maxDate = LocalDate.now().minusYears(18);
            model.addAttribute("maxDate", maxDate);
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              Model model,
                              HttpSession session) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("loggedInUser", username);
            return "redirect:/users/profile";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    
    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        String currentUsername = (String) session.getAttribute("loggedInUser");

        if (currentUsername == null) {
            return "redirect:/users/login";
        }

        Optional<User> userOpt = userService.findByUsername(currentUsername);
        if (userOpt.isEmpty()) {
            return "redirect:/users/login";
        }

        User user = userOpt.get();
        model.addAttribute("user", user);

        
        session.setAttribute("userId", user.getId());

        
        try {
            List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());
            model.addAttribute("bookings", bookings);
        } catch (Exception e) {
            model.addAttribute("bookings", List.of());
        }

        
        model.addAttribute("savedTrips", List.of());

        return "profile";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login?logout";
    }

    @GetMapping("/idByUsername")
    @ResponseBody
    public ResponseEntity<Integer> getUserIdByUsername(@RequestParam String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        return userOpt
                .map(user -> ResponseEntity.ok(user.getId()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/all")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-registrations";
    }
}