package practica.tecnologias.web.app.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Clase interceptora HttpHandshakeInterceptor, relacionada con los webSockets utilizados en el Chat.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Component
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HttpHandshakeInterceptor.class);

	/**
	 * Before handshake.
	 *
	 * @param request the request
	 * @param response the response
	 * @param wsHandler the ws handler
	 * @param attributes the attributes
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {

		logger.info("Call beforeHandshake");

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession();
			attributes.put("sessionId", session.getId());
		}
		return true;
	}

	/**
	 * After handshake.
	 *
	 * @param request the request
	 * @param response the response
	 * @param wsHandler the ws handler
	 * @param ex the ex
	 */
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Exception ex) {
		
		logger.info("Call afterHandshake");
	}

}
