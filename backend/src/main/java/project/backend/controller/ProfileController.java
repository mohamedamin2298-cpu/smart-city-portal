package project.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.backend.dto.ProfileResponseDTO;
import project.backend.service.UserService;
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<ProfileResponseDTO> getProfile() {
        return ResponseEntity.ok(userService.profile());
    }
}