package etf.iot.cloud.platform.services.dto;



import java.util.List;

@lombok.Data
public class DeviceData {
    private List<Data> temperatureData;
    private List<Data> fuelData;
    private List<Data> loadData;
    private List<Stats> deviceStats;
}
