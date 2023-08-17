package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.model.StatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDao extends JpaRepository<StatsEntity,Long> {
}
