package etf.iot.cloud.platform.services.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rule {
    private List<String> methods;
    private String pattern;
    private List<String> roles;
}