package etf.iot.cloud.platform.services.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketsConfig class enables configuration of WebSocket endpoint.
 *
 * Enables setting up WebSocket endpoint and underlying message broker's topics.
 * @author Zeljko Tripic
 * @version 1.0
 * @since   2023-12-26
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketsConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Adding user defined topics to message broker.
     *
     * First enabling message broker and then configuring topic's prefixes.
     *
     * @param config Registry of underlying message broker's topics.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // we must specify prefixes for all topics that we are going to use in app
        config.enableSimpleBroker("/devices");
        // specifies prefix for all WebSocket endpoints
        config.setApplicationDestinationPrefixes("/dashboard");
        // enabling user to subscribe to topic that is specific to him
        // each message routed to this topic is forwarded to subscribed user
        config.setUserDestinationPrefix("/devices");
    }

    /**
     * Setting up websocket endpoint with STOMP protocol.
     *
     * @param registry Registry of available WebSocket endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // specifies endpoint that supports WebSocket and STOMP (allowing SockJS as fallback option)
        // this endpoint is used for establishing WebSocket connection (receives HTTP requests with switch/upgrade protocol header)
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}