package etf.iot.cloud.platform.services.dto;

import lombok.Data;

/**
 * Class representing iot gateway statistics data
 */
@Data
public class Stats {
    /**
     * Stats' id
     */
    private long id;
    /**
     * Amount of collected temperature data (in bytes)
     */
    private long tempDataBytes;
    /**
     * Amount of forwarded temperature data (in bytes)
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
    private String startTime;
    /**
     * End of stats collecting interval
     */
    private String endTime;

    /**
     * Creates string representation of stats values
     *
     * @return String representation of Stats object
     */
    @Override
    public String toString() {
        return "Stats{" +
                "id=" + id +
                ", tempDataBytes=" + tempDataBytes +
                ", tempDataBytesForwarded=" + tempDataBytesForwarded +
                ", tempDataRequests=" + tempDataRequests +
                ", loadDataBytes=" + loadDataBytes +
                ", loadDataBytesForwarded=" + loadDataBytesForwarded +
                ", loadDataRequests=" + loadDataRequests +
                ", fuelDataBytes=" + fuelDataBytes +
                ", fuelDataBytesForwarded=" + fuelDataBytesForwarded +
                ", fuelDataRequests=" + fuelDataRequests +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
