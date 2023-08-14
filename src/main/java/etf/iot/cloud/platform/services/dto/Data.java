package etf.iot.cloud.platform.services.dto;

import java.util.Date;

@lombok.Data
public class Data {
    private double value;
    private String measured;
    private String type;
    private String unit;
}
