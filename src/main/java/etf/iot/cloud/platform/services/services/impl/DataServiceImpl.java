package etf.iot.cloud.platform.services.services.impl;

import com.google.gson.Gson;
import etf.iot.cloud.platform.services.dao.DataDao;
import etf.iot.cloud.platform.services.dao.DeviceDao;
import etf.iot.cloud.platform.services.dao.StatsDao;
import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.dto.DeviceData;
import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.enums.DataUnit;
import etf.iot.cloud.platform.services.model.DataEntity;
import etf.iot.cloud.platform.services.model.DeviceEntity;
import etf.iot.cloud.platform.services.services.DataService;
import etf.iot.cloud.platform.services.util.LoggerBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    private final DataDao dataDao;
    private final StatsDao statsDao;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;
    private final DeviceDao deviceDao;
    private final SimpMessagingTemplate simpMessageTemplate;
    private final Gson gson = new Gson();
    @Value("${history}")
    private String historyInHrs;
    private final DateFormat dateFormater=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public DataServiceImpl(DataDao dataDao, StatsDao statsDao, ModelMapper modelMapper, LoggerBean loggerBean, DeviceDao deviceDao, SimpMessagingTemplate simpMessageTemplate) {
        this.dataDao = dataDao;
        this.statsDao = statsDao;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
        this.deviceDao = deviceDao;
        this.simpMessageTemplate = simpMessageTemplate;
    }

    public void receive(Data data) {
        String time = data.getTime();
        data.setTime(null);
        DataEntity entity = modelMapper.map(data, DataEntity.class);
        DataUnit unit = DataUnit.Unknown;
        try {
            unit = DataUnit.valueOf(data.getUnit());
        } catch (Exception e) {
            e.printStackTrace();
            loggerBean.logError(e);
        }
        entity.setUnit(unit);
        entity.setType(DataType.valueOf(data.getType()));
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        try {
            DateFormat dateFormat = new SimpleDateFormat(device.getTimeFormat());
            entity.setTime(dateFormat.parse(time));
            data.setTime(dateFormater.format(dateFormat.parse(time)));
        } catch (Exception e) {
            e.printStackTrace();
            loggerBean.logError(e);
            entity.setTime(new Date());
            data.setTime(dateFormater.format(new Date()));
        }
        DeviceEntity deviceEntity = deviceDao.findById(device.getId()).get();
        entity.setDevice(deviceEntity);
        dataDao.saveAndFlush(entity);
        simpMessageTemplate.convertAndSendToUser(device.getUsername(), entity.getType().name().toLowerCase(), gson.toJson(data,Data.class));
    }

    @Override
    public DeviceData deviceData(long id) {
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int hrs=-1;
        try{
            hrs=Integer.parseInt(historyInHrs)*(-1);
        }catch (Exception e){
            e.printStackTrace();
            loggerBean.logError(e);
        }
        calendar.add(Calendar.HOUR_OF_DAY,hrs);
        DeviceData deviceData=new DeviceData();
        deviceData.setTemperatureData(dataDao.getAllByDeviceIdAndTypeAndTimeAfter(id,DataType.TEMPERATURE,calendar.getTime()).stream().map( entity -> {
            Data data=new Data();
            data.setValue(entity.getValue());
            data.setUnit(entity.getUnit().name());
            data.setType(entity.getType().name());
            data.setTime(dateFormater.format(entity.getTime()));
            return data;
        }).collect(Collectors.toList()));
        deviceData.setLoadData(dataDao.getAllByDeviceIdAndTypeAndTimeAfter(id,DataType.LOAD,calendar.getTime()).stream().map( entity -> {
            Data data=new Data();
            data.setValue(entity.getValue());
            data.setUnit(entity.getUnit().name());
            data.setType(entity.getType().name());
            data.setTime(dateFormater.format(entity.getTime()));
            return data;
        }).collect(Collectors.toList()));
        deviceData.setFuelData(dataDao.getAllByDeviceIdAndTypeAndTimeAfter(id,DataType.FUEL_LEVEL,calendar.getTime()).stream().map( entity -> {
            Data data=new Data();
            data.setValue(entity.getValue());
            data.setUnit(entity.getUnit().name());
            data.setType(entity.getType().name());
            data.setTime(dateFormater.format(entity.getTime()));
            return data;
        }).collect(Collectors.toList()));
        deviceData.setDeviceStats(statsDao.getAllByDeviceId(id).stream().map( entity -> {
            Stats data=new Stats();
            data.setId(entity.getId());
            data.setStartTime(dateFormater.format(entity.getStartTime()));
            data.setEndTime(dateFormater.format(entity.getEndTime()));
            data.setFuelDataBytes(entity.getFuelDataBytes());
            data.setFuelDataBytesForwarded(entity.getFuelDataBytesForwarded());
            data.setFuelDataRequests(entity.getFuelDataRequests());
            data.setLoadDataBytes(entity.getLoadDataBytes());
            data.setLoadDataBytesForwarded(entity.getLoadDataBytesForwarded());
            data.setLoadDataRequests(entity.getLoadDataRequests());
            data.setTempDataBytes(entity.getTempDataBytes());
            data.setTempDataBytesForwarded(entity.getTempDataBytesForwarded());
            data.setTempDataRequests(entity.getTempDataRequests());
            return data;
        }).collect(Collectors.toList()));
        return deviceData;
    }
}
