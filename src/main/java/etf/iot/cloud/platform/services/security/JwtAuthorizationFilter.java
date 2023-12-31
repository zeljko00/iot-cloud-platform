package etf.iot.cloud.platform.services.security;

import etf.iot.cloud.platform.services.services.DeviceService;
import etf.iot.cloud.platform.services.util.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Class representing jwt filter that check whether incoming requests contain valid JWT
 */
@Component
// Filters are executed before request are forwarded to servlet
// OncePerRequest filters are executed only once per each request
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    /**
     * Class implementing DeviceService interface that has logic for accessing iot gateway app/device account data
     */
    private final DeviceService deviceService;
    /**
     * Used for generating new JWTs
     */
    private JwtUtil jwtUtil;

    /**
     * Class constructor.
     * @param deviceService DeviceService implementation
     * @param jwtUtil JwtUtil object for JWT generation
     */
    public JwtAuthorizationFilter(DeviceService deviceService, JwtUtil jwtUtil) {
        this.deviceService = deviceService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Checks whether incoming HTTP requests contain valid JWT. If they do, account data is fetched from database
     *
     * @param request received HTTP request
     * @param response HTTP response
     * @param filterChain chain of implicit and user defined filters
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extracting authorization header from request
        String tokenHeader = request.getHeader("Authorization");

        //checks whether request contains jwt
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            //request contains token
            System.out.println(Constants.ANSI_BLUE+"Received JWT token!"+ Constants.ANSI_RESET);
            //getting rid of "Bearer " prefix
            String token = tokenHeader.substring(7);
            try {
                String username = jwtUtil.getUsernameFromToken(token);
                if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //gathering information about user
                    UserDetails userDetails = deviceService.loadUserByUsername(username);
                    // checking token validity
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        System.out.println(Constants.ANSI_GREEN+"Received valid token!"+Constants.ANSI_RESET);
                    }
                    else
                        System.out.println(Constants.ANSI_RED+"Received invalid token"+Constants.ANSI_RESET);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        else
            System.out.println(Constants.ANSI_BLUE+"Request does not contain JWT token!"+Constants.ANSI_RESET);

        filterChain.doFilter(request, response);
    }
}
