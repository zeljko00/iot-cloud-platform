package etf.iot.cloud.platform.services.dto;

/**
 * Class that represents sensor data.
 */
@lombok.Data
public class Data {
    /**
     * Sensor data value
     */
    private double value;
    /**
     * Sensor data generation time.
     */
    private String time;
    /**
     * Sensor data type.
     */
    private String type;
    /**
     * Sensor data measurement unit
     */
    private String unit;

    /**
     * Class constructor.
     */
    public Data(){}
}
