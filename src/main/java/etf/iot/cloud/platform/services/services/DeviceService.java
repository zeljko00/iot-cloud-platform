package etf.iot.cloud.platform.services.services;

import etf.iot.cloud.platform.services.model.Device;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DeviceService extends UserDetailsService {
    Device createDevice(Device device);
}
