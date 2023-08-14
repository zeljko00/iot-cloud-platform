package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.dao.DeviceDao;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.model.DeviceEntity;
import etf.iot.cloud.platform.services.services.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceDao deviceDao;
    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder encoder;

    public DeviceServiceImpl(DeviceDao deviceDao, ModelMapper modelMapper) {
        this.deviceDao = deviceDao;
        this.modelMapper = modelMapper;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public Device loadUserByUsername(String username) throws UsernameNotFoundException {
        DeviceEntity device = deviceDao.findDeviceByUsername(username);
        if (device != null)
            return modelMapper.map(device,Device.class);
        else
            throw new UsernameNotFoundException(username);
    }

    public Device createDevice(Device device) {
        //check if device with specified name already exists
        DeviceEntity temp = deviceDao.findDeviceByUsername(device.getUsername());
        if (temp == null) {
            //create device's password hash
            device.setPassword(encoder.encode(device.getPassword()));
            DeviceEntity entity=modelMapper.map(device,DeviceEntity.class);
            entity = deviceDao.saveAndFlush(entity);
            device.setId(entity.getId());
            return device;
        } else return null;
    }


}
