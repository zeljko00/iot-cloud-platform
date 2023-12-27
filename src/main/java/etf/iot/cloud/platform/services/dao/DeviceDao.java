package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.model.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Defines interface for accessing iot gateway apps' accounts stored in database, via JPA API
 */
@Repository
public interface DeviceDao extends JpaRepository<DeviceEntity,Long> {
    /**
     * Returns iot gateway app's account data
     *
     * @param username IoT gateway app's username
     * @return Device account data in form of DeviceEntity object.
     */
    DeviceEntity findDeviceByUsername(String username);
}
