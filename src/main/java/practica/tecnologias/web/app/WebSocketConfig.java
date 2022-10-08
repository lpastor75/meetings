package practica.tecnologias.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import practica.tecnologias.web.app.interceptor.HttpHandshakeInterceptor;

/**
 * Clase encargada de la configuración de los webSockets necesarios para el chat.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/** The handshake interceptor. */
	@Autowired
	private HttpHandshakeInterceptor handshakeInterceptor;

	/**
	 * Register stomp endpoints.
	 *
	 * @param registry the registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS().setInterceptors(handshakeInterceptor).setWebSocketEnabled(true);
	}

	/**
	 * Configure message broker.
	 *
	 * @param registry the registry
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic");
	}

}
