package com.example.tripplanner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users") // explicit table name (good practice)
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {

    // 📧 Used as username during login
    @Column(nullable = false, unique = true)
    private String email;

    // 🔐 BCrypt-encrypted password
    @Column(nullable = false)
    private String password;

    // 👤 USER / ADMIN (WITHOUT ROLE_ prefix in DB)
    @Column(nullable = false)
    private String role;


    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    /**
     * Converts role to Spring Security authority.
     * Spring expects roles in the format: ROLE_<ROLE_NAME>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    /**
     * Spring Security uses this as the "username".
     * We use email instead of a separate username field.
     */
    @Override
    public String getUsername() {
        return email;
    }

    // --- Account state flags ---
    // For now all are enabled (simple learning setup)

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
