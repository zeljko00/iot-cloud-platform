package etf.iot.cloud.platform.services.services;

import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.dto.DeviceData;

public interface DataService {
    void receive(Data data);
    DeviceData deviceData(long id);
}
