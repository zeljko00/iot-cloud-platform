package etf.iot.cloud.platform.services.services.impl;

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

@Service
public class StatsServiceImpl implements StatsService {

    private final StatsDao statsDao;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;

    public StatsServiceImpl(StatsDao statsDao, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.statsDao = statsDao;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

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
        statsDao.saveAndFlush(statsEntity);
    }
}
