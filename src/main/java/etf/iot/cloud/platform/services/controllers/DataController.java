package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.dto.DeviceData;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.services.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController {
    private  final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/temp")
    public ResponseEntity<?> temperature(@RequestBody Data data){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println("**** Device: "+device.getId()+" - Received temperature data: "+data.getValue()+data.getUnit()+" ["+data.getTime()+"] ****");
        data.setType(DataType.TEMPERATURE.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<?> load(@RequestBody Data data){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println("++++ Device: "+device.getId()+" - Received load data: "+data.getValue()+data.getUnit()+" ["+data.getTime()+"] ++++");
        data.setType(DataType.LOAD.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/fuel")
    public ResponseEntity<?> fuel(@RequestBody Data data){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println("---- Device: "+device.getId()+" - Received fuel data: "+data.getValue()+data.getUnit()+" ["+data.getTime()+"] ----");
        data.setType(DataType.FUEL_LEVEL.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<DeviceData> deviceData(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println("Device: "+device.getId()+" - Device data request!");
        return new ResponseEntity<>(dataService.deviceData(device.getId()),HttpStatus.OK);
    }
}
