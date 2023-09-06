package etf.iot.cloud.platform.services.dao;

import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DataDao extends JpaRepository<DataEntity,Long> {
    List<DataEntity> getAllByDeviceIdAndTypeAndTimeAfter(long id, DataType type, Date time);
}
