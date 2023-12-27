package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.model.StatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *  Defines interface for accessing iot gateway apps' stats data in database, via JPA API
 */
@Repository
public interface StatsDao extends JpaRepository<StatsEntity,Long> {
    /**
     * Returns stats data of specified iot gateway app.
     *
     * @param id IoT gateway app's id
     * @return List of app's stats data.
     */
    List<StatsEntity> getAllByDeviceId(long id);
}
