package com.project.flightbookingsystem.controller;

import com.project.flightbookingsystem.entity.User;
import com.project.flightbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/idByUsername")
    public ResponseEntity<UserIdResponse> getUserIdByUsername(@RequestParam String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        return userOpt
                .map(user -> ResponseEntity.ok(new UserIdResponse(user.getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

   
    public static class UserIdResponse {
        private Long userId;

        public UserIdResponse(int i) {
            this.userId = (long) i;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}

