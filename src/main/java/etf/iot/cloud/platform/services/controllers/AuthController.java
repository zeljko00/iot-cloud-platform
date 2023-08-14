package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.services.AuthService;
import etf.iot.cloud.platform.services.services.DeviceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Base64.Decoder decoder=Base64.getDecoder();
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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

        //authenticate device
        String jwt=authService.login(username,password);
        if (jwt!=null)
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("time_format") String time_format) {
        String jwt=authService.register(username,password,time_format);
        if (jwt!=null)
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
