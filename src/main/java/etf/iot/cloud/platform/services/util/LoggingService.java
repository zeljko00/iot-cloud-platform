package etf.iot.cloud.platform.services.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Defines interface for logging HTTP requests
 */
public interface LoggingService {
    /**
     * Logs received HTTP request
     *
     * @param request HTTP request
     */
    void logRequest(HttpServletRequest request);
}
