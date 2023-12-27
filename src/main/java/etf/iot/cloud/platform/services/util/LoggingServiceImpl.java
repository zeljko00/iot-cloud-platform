package etf.iot.cloud.platform.services.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class that implements LoggingService interface and provides logging utility implementation
 */
@Service
public class LoggingServiceImpl implements LoggingService {
    /**
     * Logger used for logging
     */
    private final LoggerBean loggerBean;

    /**
     * Class constructor
     *
     * @param loggerBean logger
     */
    public LoggingServiceImpl(LoggerBean loggerBean) {
        this.loggerBean = loggerBean;
    }

    /**
     * Extracts parameters from received HTML request
     *
     * @param request received HTML request
     * @return extracted data
     */
    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }

    /**
     * Logs received HTTP request
     *
     * @param request HTTP request
     */
    @Override
    public void logRequest(HttpServletRequest request) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String, String> parameters = getParameters(request);
        reqMessage.append("REQUEST ");
        reqMessage.append("method = [").append(request.getMethod()).append("] , ");
        reqMessage.append("path = [").append(request.getRequestURI()).append("] ");
        if (!parameters.isEmpty())
            reqMessage.append(" parameters = [").append(parameters).append("] ");

        loggerBean.logRequest(reqMessage.toString());
    }
}
