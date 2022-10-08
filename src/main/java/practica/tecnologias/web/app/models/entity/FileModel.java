package practica.tecnologias.web.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Clase entidad FileModel encargada de guardar los archivo subidos durante una reunión en la BBDD.
 * 
 * @author Luis Pastor y José Gilarte
 * @version Junio 2020
 */
@Entity
@Table(name = "file_model")
public class FileModel implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/** The name. */
	@Column(name = "name")
	private String name;

	/** The mimetype. */
	@Column(name = "mimetype")
	private String mimetype;

	/** The id evento final. */
	@Column(name = "id_evento_final")
	private Long idEventoFinal;

	/** The pic. */
	@Lob
	@Column(name = "pic")
	private byte[] pic;

	/**
	 * Instantiates a new file model.
	 */
	public FileModel() {}

	/**
	 * Instantiates a new file model.
	 *
	 * @param name the name
	 * @param mimetype the mimetype
	 * @param pic the pic
	 * @param idEventoFinal the id evento final
	 */
	public FileModel(String name, String mimetype, byte[] pic, Long idEventoFinal) {
		this.name = name;
		this.mimetype = mimetype;
		this.pic = pic;
		this.idEventoFinal = idEventoFinal;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the mimetype.
	 *
	 * @return the mimetype
	 */
	public String getMimetype() {
		return mimetype;
	}

	/**
	 * Sets the mimetype.
	 *
	 * @param mimetype the new mimetype
	 */
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	/**
	 * Gets the pic.
	 *
	 * @return the pic
	 */
	public byte[] getPic() {
		return pic;
	}

	/**
	 * Sets the pic.
	 *
	 * @param pic the new pic
	 */
	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	/**
	 * Gets the id evento final.
	 *
	 * @return the id evento final
	 */
	public Long getIdEventoFinal() {
		return idEventoFinal;
	}

	/**
	 * Sets the id evento final.
	 *
	 * @param idEventoFinal the new id evento final
	 */
	public void setIdEventoFinal(Long idEventoFinal) {
		this.idEventoFinal = idEventoFinal;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}