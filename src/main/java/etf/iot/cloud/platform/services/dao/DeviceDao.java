package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.model.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<DeviceEntity,Long> {
    DeviceEntity findDeviceByUsername(String username);
}
