package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.dao.DeviceDao;
import etf.iot.cloud.platform.services.model.Device;
import etf.iot.cloud.platform.services.services.DeviceService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceDao deviceDao;
    private final BCryptPasswordEncoder encoder;

    public DeviceServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public Device loadUserByUsername(String username) throws UsernameNotFoundException {
        Device device = deviceDao.findDeviceByUsername(username);
        if (device != null)
            return device;
        else
            throw new UsernameNotFoundException(username);
    }

    public Device createDevice(Device device) {
        //check if device with specified name already exists
        Device temp = deviceDao.findDeviceByUsername(device.getUsername());
        if (temp == null) {
            //create device's password hash
            device.setPassword(encoder.encode(device.getPassword()));
            device = deviceDao.saveAndFlush(device);
            return device;
        } else return null;
    }


}
