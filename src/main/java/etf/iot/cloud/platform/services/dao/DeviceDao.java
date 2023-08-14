package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<Device,Long> {
//public interface DeviceDao{
    Device findDeviceByUsername(String username);
}
