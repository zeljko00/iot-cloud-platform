package etf.iot.cloud.platform.services.model;

import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.enums.DataUnit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@ToString(exclude = "device")
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double value;
    private Date measured;
    @Enumerated(EnumType.STRING)
    private DataType type;
    @Enumerated(EnumType.STRING)
    private DataUnit unit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="device",referencedColumnName = "id",nullable = false)
    private DeviceEntity device;

}
