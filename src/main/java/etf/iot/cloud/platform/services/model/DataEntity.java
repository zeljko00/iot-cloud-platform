package etf.iot.cloud.platform.services.model;

import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.enums.DataUnit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Class representing sensor data entity stored in database
 */
@Entity
@Data
@ToString(exclude = "device")
public class DataEntity {
    /**
     * Data id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Data value
     */
    private double value;
    /**
     * Data generation time
     */
    private Date time;
    @Enumerated(EnumType.STRING)
    /**
     * Data type
     */
    private DataType type;
    @Enumerated(EnumType.STRING)
    /**
     * Data measurement unit
     */
    private DataUnit unit;
    /**
     * IoT gateway app/device that produced data
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="device",referencedColumnName = "id",nullable = false)
    private DeviceEntity device;

}
