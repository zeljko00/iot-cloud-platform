package etf.iot.cloud.platform.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

/**
 * Class that enables logging utility
 */
public class LoggerBean {
    /**
     * HTTP request logger
     */
    private Logger requestLogger= LoggerFactory.getLogger("RequestLogger");
    /**
     * Exception logger
     */
    private Logger errorLogger=LoggerFactory.getLogger("ErrorLogger");

    /**
     * Logs caught exception
     * @param e caught exception
     */
    public void logError(Exception e){
        Writer buffer = new StringWriter();
        PrintWriter pw = new PrintWriter(buffer);
        e.printStackTrace(pw);
        errorLogger.warn(buffer.toString());
    }

    /**
     * Logs string representation of received HTTP request
     * @param request string representation of received HTTP request
     */
    public void logRequest(String request){
        requestLogger.info(request);
    }

    /**
     * returns error logger
     *
     * @return error logger
     */
    public Logger getErrorLogger(){
        return errorLogger;
    }
    /**
     * returns request logger
     *
     * @return request logger
     */
    public Logger getRequestLogger() {
        return requestLogger;
    }

    /**
     * Sets error logger
     * @param logger error logger
     */
    public void setErrorLogger(Logger logger) {
        this.errorLogger = logger;
    }
    /**
     * Sets HTTP request logger
     * @param logger request logger
     */
    public void setRequestLogger(Logger logger){
        this.requestLogger=logger;
    }
}