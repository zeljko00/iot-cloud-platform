package etf.iot.cloud.platform.services.security;

import lombok.Data;

import java.util.List;

/**
 * Class representing authorization rules for controlling access rights.
 */
@Data
public class AuthorizationRules {
    /**
     * List of rules
     */
    List<Rule> rules;
}