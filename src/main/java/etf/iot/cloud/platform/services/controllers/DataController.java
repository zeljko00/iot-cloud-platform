package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.dto.Data;
import etf.iot.cloud.platform.services.enums.DataType;
import etf.iot.cloud.platform.services.services.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        data.setType(DataType.TEMPERATURE.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<?> load(@RequestBody Data data){
        data.setType(DataType.LOAD.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/fuel")
    public ResponseEntity<?> fuel(@RequestBody Data data){
        data.setType(DataType.FUEL_LEVEL.name());
        dataService.receive(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
