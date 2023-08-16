package etf.iot.cloud.platform.services.util;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {


    private final LoggingService loggingService;

    public RequestInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        loggingService.logRequest(request);
        return true;
    }
}