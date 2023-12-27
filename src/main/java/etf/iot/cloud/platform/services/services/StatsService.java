package etf.iot.cloud.platform.services.services;

import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.model.StatsEntity;

/**
 * Interface for stats service
 */
public interface StatsService {
    /**
     * Processes and persists received iot gateway's stats data
     *
     * @param statsEntity iot gateway stats data
     */
    void receive(Stats statsEntity);
}
