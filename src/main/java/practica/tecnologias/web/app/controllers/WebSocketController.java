package practica.tecnologias.web.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import practica.tecnologias.web.app.models.entity.ChatMessage;
import practica.tecnologias.web.app.models.entity.Mensaje;
import practica.tecnologias.web.app.models.service.IEventoFinalService;

/**
 * Clase controladora de los webSockets, que es la tecnología que se utiliza para gestionar 
 * el chat en el que interactuan los usuarios en la celebración de los eventos.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Controller
public class WebSocketController {

	/** The evento final service. */
	@Autowired
	private IEventoFinalService eventoFinalService;

	/**
	 * Send message.
	 *
	 * @param chatMessage the chat message
	 * @return the chat message
	 */
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/publicChatRoom")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {

		if (chatMessage.getType() == ChatMessage.MessageType.CHAT) {
			Mensaje mensaje = new Mensaje();
			mensaje.setContent(chatMessage.getContent());
			mensaje.setUsername(chatMessage.getSender());
			mensaje.setEventoFinal_id(chatMessage.getEvento_id());
			eventoFinalService.saveMensaje(mensaje);
			chatMessage.setChatMessage_id(mensaje.getId());
		}
		return chatMessage;
	}
	
	/**
	 * Delete message.
	 *
	 * @param chatMessage the chat message
	 * @return the chat message
	 */
	@MessageMapping("/chat.deleteMessage")
	@SendTo("/topic/publicChatRoom")
	public ChatMessage deleteMessage(@Payload ChatMessage chatMessage) {

		Mensaje mensaje = eventoFinalService.findMensajeById(chatMessage.getChatMessage_id());
		eventoFinalService.deleteMensaje(mensaje);
		
		return chatMessage;
	}

	/**
	 * Adds the user.
	 *
	 * @param chatMessage the chat message
	 * @param headerAccessor the header accessor
	 * @return the chat message
	 */
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/publicChatRoom")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Add username and eventoFinal_id in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		headerAccessor.getSessionAttributes().put("eventoFinal_id", chatMessage.getEvento_id());
		return chatMessage;
	}

}
