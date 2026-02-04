package com.example.tripplanner.controller;

import com.example.tripplanner.dto.ChangePasswordRequest;
import com.example.tripplanner.dto.UserProfileResponse;
import com.example.tripplanner.model.User;
import com.example.tripplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Accessible by USER & ADMIN
     */
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserProfileResponse profile() {

        // 1️⃣ Get authentication from SecurityContext
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        // 2️⃣ Email was set as principal in JwtAuthFilter
        String email = auth.getName();

        // 3️⃣ Load user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4️⃣ Map entity → DTO
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

//      * Change password for logged-in user
//     * Accessible by USER and ADMIN
@PostMapping("/change-password")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String changePassword(
            @RequestBody ChangePasswordRequest request
            ){
        // 1️ Get authentication object from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2️ Extract email (we set email as principal in JwtAuthFilter)
        String email = authentication.getName();

        // 3️ Load user from database
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        // 4️ Verify old password using BCrypt
        boolean isOldPasswordCorrect =
                passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPassword()
                );
        if (!isOldPasswordCorrect){
            throw new RuntimeException("Old Password is Incorrect");
        }

        // 5️ Encode and set new password
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        // 6️ Save updated user
        userRepository.save(user);

        return "Password changed successfully";

    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String logout(){
        return "Logged out successfully. Delete token on client.";
    }
}
