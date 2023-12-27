package etf.iot.cloud.platform.services.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class representing authorization rule for access control.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rule {
    /**
     * List of allowed HTTP methods.
     */
    private List<String> methods;
    /**
     * URL pattern
     */
    private String pattern;
    /**
     * List of allowed user roles
     */
    private List<String> roles;
}