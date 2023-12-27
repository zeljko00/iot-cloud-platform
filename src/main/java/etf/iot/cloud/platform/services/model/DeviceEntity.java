package etf.iot.cloud.platform.services.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * DeviceEntity class represent iot gateway app/device account data stored in database
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"data", "stats"})
public class DeviceEntity {
    /**
     * iot gateway's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * iot gateway's username
     */
    private String username;
    /**
     * iot gateway's password
     */
    private String password;
    /**
     * iot gateway's time format
     */
    private String timeFormat;
    /**
     * sensor data received from specific iot gateway
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
    private List<DataEntity> data;
    /**
     * stats data for particular iot gateway
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
    private List<StatsEntity> stats;
}
