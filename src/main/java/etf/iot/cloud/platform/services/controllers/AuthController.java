package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.services.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import etf.iot.cloud.platform.services.util.Constants;

/**
 * AuthController class represents REST controller that handles HTTP login and sing up requests.
 *
 * @author Zeljko Tripic
 * @version 1.0
 * @since   2023-12-26
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    /**
     * Base64 decoder used for creating content of HTTP Authorization header.
     */
    private final Base64.Decoder decoder = Base64.getDecoder();
    /**
     * AuthService instance contains device login and sign up logic.
     */
    private final AuthService authService;

    /**
     * API key required for accessing sign up endpoint
     */
    @Value("${api.key}")
    private String apiKey;

    /**
     * Class constructor.
     *
     * @param authService Object implementing AuthService interface.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles IoT gateway app login requests.
     *
     * @param auth Authorization header from received HTTP request
     * @return JWT if login is successful
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        //parsing credentials from Authorization header
        String[] tokens = auth.split(" ");
        byte[] data = tokens[1].getBytes();
        byte[] decodedData = decoder.decode(data);
        String credentials = new String(decodedData);
        tokens = credentials.split(":");
        String username = tokens[0];
        String password = tokens[1];
        System.out.println(Constants.ANSI_BLUE+"Device: "+username+" - Sign in request!"+Constants.ANSI_RESET);
        //authenticate device
        String jwt = authService.login(username, password);
        if (jwt != null){
            System.out.println(Constants.ANSI_GREEN+"Device: "+username+" - Successful sign in!"+Constants.ANSI_RESET);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        else{
            System.out.println(Constants.ANSI_RED+"Device: "+username+" - Sign in failed"+Constants.ANSI_RESET);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    // used for checking whether jwt has expired

    /**
     *  Checks if JWT token from Authorization header is valid.
     *
     * @return HTTP status, 200 if jwt is still valid, otherwise 401
     */
    @GetMapping("/jwt-check")
    public ResponseEntity<String> checkJwt() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Handles requests for new iot-gateway app account.
     *
     * @param key API key required for access.
     * @param username IoT gateway app username
     * @param password IoT gateway app password
     * @param time_format IoT gateway app time format
     * @return JWT if sign up is successful
     */
    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestHeader(HttpHeaders.AUTHORIZATION) String key, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("time_format") String time_format) {
        // allow signup only for devices that have valid api key
        System.out.println(Constants.ANSI_BLUE+"Device: "+username+" - Sign up request!"+Constants.ANSI_RESET);
        if (apiKey.equals(key)) {
            String jwt = authService.register(username, password, time_format);
            if (jwt != null)
                return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        else
            System.out.println(Constants.ANSI_RED+"Device: "+username+" - Invalid API key!"+Constants.ANSI_RESET);
        System.out.println(Constants.ANSI_RED+"Device: "+username+" - Sign up failed!"+Constants.ANSI_RESET);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
