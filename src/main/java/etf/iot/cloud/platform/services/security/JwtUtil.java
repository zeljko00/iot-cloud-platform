package etf.iot.cloud.platform.services.security;

import etf.iot.cloud.platform.services.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains logic for manipulating with JWTs
 */
@Component
public class JwtUtil {
    /**
     * JWT validity duration
     */
    public static final long JWT_VALIDITY = 5 * 60 * 60;    //validity period for jwt
    /**
     * Secret key for signing JWTs
     */
    @Value("${jwt.secretKey}")
    private String secretKey; // reading secret key needed for digital signature of jwt, from application.properties file

    /**
     * Enables parsing username from JWT
     *
     * @param token JWT
     * @return username
     */
    public String getUsernameFromToken(String token) {      //retrieve username from jwt
        return getAllClaimsFromToken(token).getSubject();
    }
    /**
     * Enables parsing user's role from JWT
     *
     * @param token JWT
     * @return role
     */
    public String getRoleFromToken(String token){return getAllClaimsFromToken(token).get("role",String.class);}
    /**
     * Enables parsing expiration date from JWT
     *
     * @param token JWT
     * @return expiration date
     */
    public Date getExpirationDateFromToken(String token) {       //retrieve expiration date from jwt
        return getAllClaimsFromToken(token).getExpiration();
    }
    /**
     * Enables parsing data from JWT
     *
     * @param token JWT
     * @return collections of data contained in JWT
     */
    //retrieve all claims from jwt body ( claim = property of JSON object representing jwt body),
    // for retrieving any information from token secret key is required
    // exception will be thrown if token is changed, signed with different secret key...
    private Claims getAllClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            System.out.println(Constants.ANSI_RED+"JWT changed or signed with different key!"+ Constants.ANSI_RESET);
            throw e;
        }
    }
    /**
     * Checking whether JWT has expired
     *
     * @param token JWT
     * @return True if it is not expired
     */
    private Boolean isTokenExpired(String token) {       //check if the token has expired
        boolean expired=getExpirationDateFromToken(token).before(new Date());
        if(expired)
            System.out.println(Constants.ANSI_RED+"Token expired!"+Constants.ANSI_RESET);
        return  expired;
    }

    /**
     * Generates JWT based on provided user data
     *
     * @param userDetails User data
     * @return JWT
     */

    //generating token for authenticated user
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    public String generateToken(UserDetails userDetails) {
        //adding user role to token claims
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * Check whether JWT is valid
     *
     * @param token JWT
     * @param userDetails User data
     * @return True if JWT is valid
     */
    //token is valid if it has valid username and it is not expired
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && userDetails.isAccountNonLocked());
    }
}
