package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.dao.DeviceDao;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.model.DeviceEntity;
import etf.iot.cloud.platform.services.services.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implements DeviceService interface
 *
 * Provides iot gateway's accounts manipulation functionalities
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    /**
     * Dao object for iot gateway's account entity manipulation
     */
    private final DeviceDao deviceDao;
    /**
     * Provides object mapping func
     */
    private final ModelMapper modelMapper;
    /**
     * Provides account's password hashing func
     */

    private final BCryptPasswordEncoder encoder;

    /**
     * Class constructor
     *
     * @param deviceDao dao object
     * @param modelMapper model mapper
     */
    public DeviceServiceImpl(DeviceDao deviceDao, ModelMapper modelMapper) {
        this.deviceDao = deviceDao;
        this.modelMapper = modelMapper;
        this.encoder = new BCryptPasswordEncoder();
    }

    /**
     *
     * Returns iot gateways account for specified username
     *
     * @param username account username
     * @return Device entity (iot gateway account)
     * @throws UsernameNotFoundException
     */
    @Override
    public Device loadUserByUsername(String username) throws UsernameNotFoundException {
        DeviceEntity device = deviceDao.findDeviceByUsername(username);
        if (device != null)
            return modelMapper.map(device,Device.class);
        else
            throw new UsernameNotFoundException(username);
    }

    /**
     * Creates new account
     *
     * @param device iot gateway's account data
     * @return stored Device entity (iot gateway account)
     */
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
