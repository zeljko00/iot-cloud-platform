package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.model.Device;
import etf.iot.cloud.platform.services.security.JwtUtil;
import etf.iot.cloud.platform.services.services.AuthService;
import etf.iot.cloud.platform.services.services.DeviceService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final DeviceService deviceService;


    public AuthServiceImpl(AuthenticationManager authenticationManager, DeviceService deviceService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.deviceService = deviceService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(String username, String password) {
        //authentication manager authenticates device based on received credentials -
        //if authentication fails exception is thrown
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username, password
                            )
                    );
            //device is successfully authenticated and added to context
            Device device = (Device) authenticate.getPrincipal();
            // generate and return new jwt
            return jwtUtil.generateToken(device);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String register(String username, String password) {
        //create new device
        Device device=new Device();
        device.setUsername(username);
        device.setPassword(password);
        device=deviceService.createDevice(device);
        //if already registered device try to register again, device wont be created again,
        // just login will be executed (if provided password is correct)
        return login(username,password);
    }
}
