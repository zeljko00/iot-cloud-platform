package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.dto.DeviceData;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.services.DataService;
import etf.iot.cloud.platform.services.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * DataController class represents REST controller that handles HTTP requests regarding sensor data.
 *
 * @author Zeljko Tripic
 * @version 1.0
 * @since   2023-12-26
 */
@RestController
@RequestMapping("/data")
public class DataController {

    /**
     * Class implementing DataService interface contains logic for processing received sensor data
     */
    private  final DataService dataService;

    /**
     * Class constructor.
     *
     * @param dataService object implementing DataService interface
     */
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Handles HTTP requests that contain temperature sensor data
     *
     * @param data Sensor data provided in HTML request body
     * @return HTTP status
     */
    @PostMapping("/temp")
    public ResponseEntity<?> temperature(@RequestBody Data data){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println(Constants.ANSI_PURPLE+"**** Device: "+device.getId()+" - Received temperature data: "+data.getValue()+data.getUnit()+" ["+data.getTime()+"] ****"+Constants.ANSI_RESET);
        data.setType(DataType.TEMPERATURE.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Handles HTTP requests that contain arm load sensor data
     *
     * @param data Sensor data provided in HTML request body
     * @return HTTP status
     */
    @PostMapping("/load")
    public ResponseEntity<?> load(@RequestBody Data data){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println(Constants.ANSI_YELLOW+"++++ Device: "+device.getId()+" - Received load data: "+data.getValue()+data.getUnit()+" ["+data.getTime()+"] ++++"+ Constants.ANSI_RESET);
        data.setType(DataType.LOAD.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Handles HTTP requests that contain fuel sensor data
     *
     * @param data Sensor data provided in HTML request body
     * @return HTTP status
     */
    @PostMapping("/fuel")
    public ResponseEntity<?> fuel(@RequestBody Data data){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println(Constants.ANSI_CYAN+"---- Device: "+device.getId()+" - Received fuel data: "+data.getValue()+data.getUnit()+" ["+data.getTime()+"] ----"+Constants.ANSI_RESET);
        data.setType(DataType.FUEL_LEVEL.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Handles HTTP requests for specific iot gateway app's data
     *
     * @return Sensor and stats data of specified iot gateway app.
     */
    @GetMapping
    public ResponseEntity<DeviceData> deviceData(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println(Constants.ANSI_BLUE+"Device: "+device.getId()+" - Device data request!"+Constants.ANSI_RESET);
        return new ResponseEntity<>(dataService.deviceData(device.getId()),HttpStatus.OK);
    }
}
