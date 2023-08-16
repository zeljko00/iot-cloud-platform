package etf.iot.cloud.platform.services.services.impl;

import etf.iot.cloud.platform.services.dao.DataDao;
import etf.iot.cloud.platform.services.dao.DeviceDao;
import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.enums.DataUnit;
import etf.iot.cloud.platform.services.model.DataEntity;
import etf.iot.cloud.platform.services.model.DeviceEntity;
import etf.iot.cloud.platform.services.services.DataService;
import etf.iot.cloud.platform.services.util.LoggerBean;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataServiceImpl implements DataService {

    private final DataDao dataDao;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;
    private final DeviceDao deviceDao;

    public DataServiceImpl(DataDao dataDao, ModelMapper modelMapper, LoggerBean loggerBean, DeviceDao deviceDao) {
        this.dataDao = dataDao;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
        this.deviceDao = deviceDao;
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
        } catch (Exception e) {
            e.printStackTrace();
            loggerBean.logError(e);
            entity.setTime(new Date());
        }
        DeviceEntity deviceEntity = deviceDao.findById(device.getId()).get();
        entity.setDevice(deviceEntity);
        System.out.println(entity);
        dataDao.saveAndFlush(entity);
    }
}
