package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.security.JwtUtil;
import etf.iot.cloud.platform.services.services.AuthService;
import etf.iot.cloud.platform.services.services.DeviceService;
import etf.iot.cloud.platform.services.util.LoggerBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Implementation of AuthService interface
 * <p>
 * Implements logic regarding authentication of iot gateway apps on cloud platform
 */
@Service
public class AuthServiceImpl implements AuthService {
    /**
     * Responsible for authentication process
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Provides JWT manipulation functionalities
     */
    private final JwtUtil jwtUtil;
    /**
     * Provides logging functionalities
     */
    private final LoggerBean loggerBean;
    /**
     * Object implementing DeviceService interface
     */
    private final DeviceService deviceService;

    /**
     * Class constructor
     *
     * @param authenticationManager auth manager
     * @param deviceService implementation of DeviseService interface
     * @param jwtUtil jwt util bean
     * @param loggerBean logger bean
     */
    public AuthServiceImpl(AuthenticationManager authenticationManager, DeviceService deviceService, JwtUtil jwtUtil, LoggerBean loggerBean) {
        this.authenticationManager = authenticationManager;
        this.deviceService = deviceService;
        this.jwtUtil = jwtUtil;
        this.loggerBean = loggerBean;
    }

    /**
     * Handles iot gateway app/device login
     *
     * @param username iot gateway's username
     * @param password iot gateway's password
     * @return JWT if login is successful
     */
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
            loggerBean.logError(e);
            return null;
        }
    }

    /**
     * Forwards request for creating account for new iot gateway app/device to DeviceService
     *
     * @param username iot gateway's username
     * @param password iot gateway's password
     * @param time_format iot gateway's time format
     * @return JWT if login is successful
     */
    @Override
    public String register(String username, String password, String time_format) {
        //create new device
        Device device = new Device();
        device.setUsername(username);
        device.setPassword(password);
        device.setTimeFormat(time_format);
        device = deviceService.createDevice(device);
        //if already registered device try to register again, device wont be created again,
        // just login will be executed (if provided password is correct)
        return login(username, password);
    }
}
