package etf.iot.cloud.platform.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

public class LoggerBean {
    private Logger requestLogger= LoggerFactory.getLogger("RequestLogger");
    private Logger errorLogger=LoggerFactory.getLogger("ErrorLogger");
    public void logError(Exception e){
        Writer buffer = new StringWriter();
        PrintWriter pw = new PrintWriter(buffer);
        e.printStackTrace(pw);
        errorLogger.warn(buffer.toString());
    }
    public void logRequest(String request){
        requestLogger.info(request);
    }
    public Logger getErrorLogger(){
        return errorLogger;
    }
    public Logger getRequestLogger() {
        return requestLogger;
    }
    public void setErrorLogger(Logger logger) {
        this.errorLogger = logger;
    }
    public void setRequestLogger(Logger logger){
        this.requestLogger=logger;
    }
}