package practica.tecnologias.web.app.models.entity;

/**
 * Clase de la entidad Chat (mensajes enviados en el chat).
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
public class ChatMessage {

	/** The ChatMessage id. */
	private Long chatMessage_id;
	
	/** The type. */
	private MessageType type;

	/** The content. */
	private String content;

	/** The sender. */
	private String sender;

	/** The evento id. */
	private Long evento_id;

	/**
	 * The Enum MessageType.
	 */
	public enum MessageType {

		/** The chat. */
		CHAT,
		/** The join. */
		JOIN,
		/** The leave. */
		LEAVE,
		/** The delete. */
		DELETE
	}
	
	/**
	 * Gets the chatMessage id
	 * 
	 * @return the chatMessage_id
	 */
	public Long getChatMessage_id() {
		return chatMessage_id;
	}

	/**
	 * Sets the chatMessage id
	 * 
	 * @param chatMessage_id the new chatMessage_id
	 */
	public void setChatMessage_id(Long chatMessage_id) {
		this.chatMessage_id = chatMessage_id;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 *
	 * @param sender the new sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Gets the evento id.
	 *
	 * @return the evento id
	 */
	public Long getEvento_id() {
		return evento_id;
	}

	/**
	 * Sets the evento id.
	 *
	 * @param evento_id the new evento id
	 */
	public void setEvento_id(Long evento_id) {
		this.evento_id = evento_id;
	}

}