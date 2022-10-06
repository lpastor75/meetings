package practica.tecnologias.web.app.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import practica.tecnologias.web.app.models.entity.ChatMessage;

/**
 * Listener para recibir los eventos de los webSockets. La clase que está interesada en procesar un webSocketEvent
 * implementa esta interfaz, y el objeto creado con esa clase está registrado con un componente usando el
 * método addWebSocketEventListener del componente. Cuando el evento webSocketEvent ocurre, ese objeto invoca dicho método.
 *
 * @author Alumno 1, Alumno 2, Alumno 3
 * @version Junio 2020
 */
@Component
public class WebSocketEventListener {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	/** The messaging template. */
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	/**
	 * Handle web socket connect listener.
	 *
	 * @param event the event
	 */
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
	}

	/**
	 * Handle web socket disconnect listener.
	 *
	 * @param event the event
	 */
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");

		if (username != null) {
			logger.info("User Disconnected : " + username);

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType(ChatMessage.MessageType.LEAVE);
			chatMessage.setSender(username);

			messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
		}
	}

}
