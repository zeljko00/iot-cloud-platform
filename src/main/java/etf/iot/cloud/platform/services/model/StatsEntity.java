package etf.iot.cloud.platform.services.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Class representing iot gateway statistics data entity stored in database
 */
@Entity
@Data
@ToString(exclude = "device")
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     *  Stats data id
     */
    private long id;
    /**
     * IoT gateway taht produced this stats
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="device",referencedColumnName = "id",nullable = false)
    private DeviceEntity device;

    /**
     * Amount of collected temperature data (in bytes)
     */
    private long tempDataBytes;
    /**
     * Number of requests sent to cloud temperature services
     */
    private long tempDataBytesForwarded;
    /**
     * Number of requests sent to cloud temperature services
     */
    private long tempDataRequests;

    /**
     * Amount of collected load data (in bytes)
     */
    private long loadDataBytes;
    /**
     * Amount of forwarded load data (in bytes)
     */
    private long loadDataBytesForwarded;
    /**
     * Number of requests sent to cloud services regarding arm load data
     */
    private long loadDataRequests;

    /**
     * Amount of collected fuel data (in bytes)
     */
    private long fuelDataBytes;
    /**
     * Amount of forwarded fuel data (in bytes)
     */
    private long fuelDataBytesForwarded;
    /**
     * Number of requests sent to cloud fuel services
     */
    private long fuelDataRequests;

    /**
     * Start of stats collecting interval
     */
    private Date startTime;
    /**
     * End of stats collecting interval
     */
    private Date endTime;

}
