package etf.iot.cloud.platform.services.services;

import etf.iot.cloud.platform.services.dto.Device;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface for iot gateway app/device account service
 */
public interface DeviceService extends UserDetailsService {
    /**
     * Creates account for new iot gateway app
     *
     * @param device iot gateway app/device data
     * @return device entity
     */
    Device createDevice(Device device);
}
