package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.services.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping
    public ResponseEntity<?> receive(@RequestBody Stats stats){
        statsService.receive(stats);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
