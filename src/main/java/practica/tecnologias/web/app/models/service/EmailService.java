package practica.tecnologias.web.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import practica.tecnologias.web.app.models.entity.Evento;
import practica.tecnologias.web.app.models.entity.Horario;
import practica.tecnologias.web.app.models.entity.Usuario;

/**
 * Clase Servicio de E-Mail, encargada de enviar un e-mail informativo al usuario cuando éste vota por un horario.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Service("emailService")
public class EmailService {

	/** The mail sender. */
	@Autowired
	private JavaMailSender mailSender;

	/** The to. */
	private String to;

	/** The subject. */
	private String subject;

	/** The body. */
	private String body;

	/**
	 * Enviar mail votado.
	 *
	 * @param evento the evento
	 * @param usuario the usuario
	 * @param horario the horario
	 */
	public void EnviarMailVotado(Evento evento, Usuario usuario, Horario horario) {

		this.to = usuario.getEmail();
		this.subject = "Has votado en el evento: " + evento.getTitulo();
		this.body = "Buenas " + usuario.getNombre() + "\n\n Has votado la Hora " + horario.getHorarioFlow() +
			" para el evento de la cabecera";
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.getSentDate();
		mailSender.send(message);
	}

}
