package etf.iot.cloud.platform.services.util;

import jakarta.servlet.http.HttpServletRequest;

public interface LoggingService {
    void logRequest(HttpServletRequest request);
}
