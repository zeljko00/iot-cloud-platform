package etf.iot.cloud.platform.services.dto;

@lombok.Data
public class Data {
    private double value;
    private String time;
    private String type;
    private String unit;

    public Data(){}
}
