package etf.iot.cloud.platform.services.services;

import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.model.StatsEntity;

public interface StatsService {
    void receive(Stats statsEntity);
}
