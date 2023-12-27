package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.dao.DeviceDao;
import etf.iot.cloud.platform.services.dao.StatsDao;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.model.StatsEntity;
import etf.iot.cloud.platform.services.services.StatsService;
import etf.iot.cloud.platform.services.util.LoggerBean;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Implementation of StatsService
 *
 * Provides functionalities for receiving and storing iot gateway stats data
 */
@Service
public class StatsServiceImpl implements StatsService {
    /**
     *  Dao object for stats entity manipulation
     */
    private final StatsDao statsDao;
    /**
     *  Dao object for iot gateway account entity manipulation
     */
    private final DeviceDao deviceDao;
    /**
     *  Provides object mapping func
     */
    private final ModelMapper modelMapper;
    /**
     * Provides logging func
     */
    private final LoggerBean loggerBean;

    public StatsServiceImpl(StatsDao statsDao, DeviceDao deviceDao, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.statsDao = statsDao;
        this.deviceDao = deviceDao;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

    /**
     * Processes and stores received iot gateway stats data
     *
     * @param stats iot gateway stats data
     */
    @Override
    public void receive(Stats stats) {
        System.out.println(stats);
        String from=stats.getStartTime();
        String to=stats.getEndTime();
        stats.setStartTime(null);
        stats.setEndTime(null);
        StatsEntity statsEntity=modelMapper.map(stats,StatsEntity.class);
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();

        try{
            DateFormat dateFormat = new SimpleDateFormat(device.getTimeFormat());
            statsEntity.setStartTime(dateFormat.parse(from));
            statsEntity.setEndTime(dateFormat.parse(to));
        }catch (Exception e){
            e.printStackTrace();
            loggerBean.logError(e);
        }
        statsEntity.setDevice(deviceDao.findDeviceByUsername(device.getUsername()));
        statsDao.saveAndFlush(statsEntity);
    }
}
