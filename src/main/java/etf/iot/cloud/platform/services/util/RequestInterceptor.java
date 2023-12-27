package etf.iot.cloud.platform.services.util;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Class that represents custom HTTP request interceptor
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * Class implementing LoggingService implements logging utility
     */
    private final LoggingService loggingService;

    /**
     * Class constructor
     *
     * @param loggingService service implementing logging utility
     */
    public RequestInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * Logging intercepted HTTP request
     *
     * @param request intercepted HTTP request
     * @param response HTTP response
     * @param handler habdler
     * @return true
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        loggingService.logRequest(request);
        return true;
    }
}