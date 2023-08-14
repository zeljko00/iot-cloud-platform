package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDao extends JpaRepository<DataEntity,Long> {
}
