package etf.iot.cloud.platform.services.services;

/**
 * Interface for authentication service
 */
public interface AuthService {
    /**
     * Login of iot gateway app/device to cloud services account
     *
     * @param username iot gateway's username
     * @param password iot gateway's password
     * @return jwt
     */
    String login(String username,String password);

    /**
     * Creates account for new iot gateway
     *
     * @param username iot gateway's username
     * @param password iot gateway's password
     * @param time_format iot gateway's tiem format
     * @return jwt
     */
    String register(String username,String password,String time_format);
}
