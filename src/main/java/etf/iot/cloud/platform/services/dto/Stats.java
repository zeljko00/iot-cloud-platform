package etf.iot.cloud.platform.services.dto;

import lombok.Data;

@Data
public class Stats {
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

    private String startTime;
    private String endTime;

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
