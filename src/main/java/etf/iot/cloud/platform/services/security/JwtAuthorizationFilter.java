package etf.iot.cloud.platform.services.security;

import etf.iot.cloud.platform.services.services.DeviceService;
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

@Component
// Filters are executed before request are forwarded to servlet
// OncePerRequest filters are executed only once per each request
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final DeviceService deviceService;
    private JwtUtil jwtUtil;

    public JwtAuthorizationFilter(DeviceService deviceService, JwtUtil jwtUtil) {
        this.deviceService = deviceService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extracting authorization header from request
        String tokenHeader = request.getHeader("Authorization");

        //checks whether request contains jwt
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            //request contains token
            System.out.println("Token: "+tokenHeader);
            //getting rid of "Bearer " prefix
            String token = tokenHeader.substring(7);
            try {
                String username = jwtUtil.getUsernameFromToken(token);
                if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //gathering information about user
                    UserDetails userDetails = deviceService.loadUserByUsername(username);
                    System.out.println(userDetails.getAuthorities());
                    System.out.println("Username :"+username);
                    // checking token validity
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        System.out.println("Received valid token!");
                    }
                    else
                        System.out.println("Received invalid token");
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        else
            System.out.println("Request does not contain JWT token!");

        System.out.println("Continue with filter chain!!!!!!!!");
        filterChain.doFilter(request, response);
    }
}
