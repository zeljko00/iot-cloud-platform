package etf.iot.cloud.platform.services.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long tempDataBytes;
    private long tempDataBytesForwarded;
    private long tempDataRequests;

    private long loadDataBytes;
    private long loadDataBytesForwarded;
    private long loadDataRequests;

    private long fuelDataBytes;
    private long fuelDataBytesForwarded;
    private long fuelDataRequests;

    private Date startTime;
    private Date endTime;

}
