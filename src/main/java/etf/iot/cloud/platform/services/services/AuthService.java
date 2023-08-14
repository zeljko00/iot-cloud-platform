package etf.iot.cloud.platform.services.services;

public interface AuthService {
    String login(String username,String password);
    String register(String username,String password,String time_format);
}
