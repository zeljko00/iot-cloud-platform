package etf.iot.cloud.platform.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public static final long JWT_VALIDITY = 60;    //validity period for jwt
    @Value("${jwt.secretKey}")
    private String secretKey; // reading secret key needed for digital signature of jwt, from application.properties file


    public String getUsernameFromToken(String token) {      //retrieve username from jwt
        return getAllClaimsFromToken(token).getSubject();
    }
    public String getRoleFromToken(String token){return getAllClaimsFromToken(token).get("role",String.class);}

    public Date getExpirationDateFromToken(String token) {       //retrieve expiration date from jwt
        return getAllClaimsFromToken(token).getExpiration();
    }

    //retrieve all claims from jwt body ( claim = property of JSON object representing jwt body),
    // for retrieving any information from token secret key is required
    // exception will be thrown if token is changed, signed with different secret key...
    private Claims getAllClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            System.out.println("Error while getting claims from jwt - jwt probably changed or signed with different key!");
            e.printStackTrace();
            throw e;
        }
    }

    private Boolean isTokenExpired(String token) {       //check if the token has expired
        boolean expired=getExpirationDateFromToken(token).before(new Date());
        if(expired)
            System.out.println("Token expired!");
        return  expired;
    }

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

    //token is valid if it has valid username and it is not expired
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && userDetails.isAccountNonLocked());
    }
}