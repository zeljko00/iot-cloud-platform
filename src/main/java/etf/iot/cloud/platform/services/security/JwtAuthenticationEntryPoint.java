package etf.iot.cloud.platform.services.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

//something like global exception handler, can be used for setting response headers before response is sent to client
//e.g. if client does not provide credentials, authentication will be requested by sending HTTP 401 status code
@Component
/**
 * Class representing kind of global exception handler.
 *
 * Catches exceptions propagated to servlet and returns HTTP unauthorized stats code
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    /**
     * Handles caught exception
     *
     * @param request received HTTP request
     * @param response HTTP response
     * @param authException caught exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}