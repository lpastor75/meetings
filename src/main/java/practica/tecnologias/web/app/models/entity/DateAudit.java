package practica.tecnologias.web.app.models.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * Clase abstracta DateAudit para auditar los archivos que son subidos durante una reunión.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},allowGetters = true)
public abstract class DateAudit {

	/** The created at. */
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	/** The updated at. */
	@LastModifiedDate
	@Column(nullable = false)
	private Instant updatedAt;

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public Instant getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Sets the updated at.
	 *
	 * @param updatedAt the new updated at
	 */
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

}
