package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.services.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println("Device: "+device.getId()+" - Received stats data!");
        statsService.receive(stats);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
