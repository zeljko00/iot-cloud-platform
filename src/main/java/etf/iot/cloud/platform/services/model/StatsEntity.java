package etf.iot.cloud.platform.services.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@ToString(exclude = "device")
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="device",referencedColumnName = "id",nullable = false)
    private DeviceEntity device;

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
