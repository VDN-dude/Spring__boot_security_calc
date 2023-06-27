package com.example.spring__boot_security_calc.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "calc_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "([A-Za-z])*", message = "This name contains invalid characters")
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\w*", message = "This username contains invalid characters")
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole();
    }

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

