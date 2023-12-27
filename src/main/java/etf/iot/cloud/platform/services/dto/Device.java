package etf.iot.cloud.platform.services.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Class representing iot gateway app/device account data
 *
 * Implements UserDetails service required by AuthenticationManager
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device implements UserDetails {
    /**
     * IoT gateway id
     */
    private Long id;
    /**
     * IoT gateway username
     */
    private String username;
    /**
     * IoT gateway password
     */
    private String password;
    /**
     * IoT gateway time format
     */
    private String timeFormat;

    /**
     * Accessing user account authorities.
     *
     * @return Collection of user authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Check whether account is expired.
     * @return False if it is expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Check whether account is locked.
     * @return False if it is locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Check whether credentials are expired.
     * @return False if they are.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Check whether account is enabled.
     * @return False if it is.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
