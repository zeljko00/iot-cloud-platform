package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  Defines interface for accessing sensor data stored in database, via JPA API
 */
@Repository
public interface DataDao extends JpaRepository<DataEntity,Long> {
    /**
     * Returns sensor data that satisfies specified conditions.
     *
     * @param id Id of iot gateway app that produced data.
     * @param type  Type of sensor data.
     * @param time Data generation time.
     * @return List of sensor data entities.
     */
    List<DataEntity> getAllByDeviceIdAndTypeAndTimeAfter(long id, DataType type, Date time);
}
