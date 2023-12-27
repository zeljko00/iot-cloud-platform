package etf.iot.cloud.platform.services.controllers;

import etf.iot.cloud.platform.services.dto.Device;
import etf.iot.cloud.platform.services.dto.Stats;
import etf.iot.cloud.platform.services.services.StatsService;
import etf.iot.cloud.platform.services.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * StatsController class represents REST controller that handles HTTP requests regarding iot gateway's stats.
 *
 * @author Zeljko Tripic
 * @version 1.0
 * @since   2023-12-26
 */
@RestController
@RequestMapping("/stats")
public class StatsController {
    /**
     * StatsService instance contains device's stats handling logic.
     */
    private final StatsService statsService;
    /**
     * Class constructor.
     *
     * @param statsService Object implementing StatsService interface.
     */
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * Handles app's stats requests.
     *
     * @param stats Stats object sent in request's body.
     * @return HTTP status
     */
    @PostMapping
    public ResponseEntity<?> receive(@RequestBody Stats stats){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Device device = (Device) authentication.getPrincipal();
        System.out.println(Constants.ANSI_BLUE+"Device: "+device.getId()+" - Received stats data!"+ Constants.ANSI_RESET);
        statsService.receive(stats);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
