package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "notificaciones")
public class Notificacion extends BaseEntity {

	// private LocalDate fecha;

	@ManyToOne(optional = false)
	private Usuario usuario;

	@OneToOne(optional = true)
	private Comentario comentario;

	@OneToOne(optional = true)
	private MensajePrivado mensajePrivado;

//	public Notificacion(Usuario usuario, Comentario comentario, MensajePrivado mensajePrivado) {
//		this.usuario = usuario;
//		this.comentario = comentario;
//		this.mensajePrivado = mensajePrivado;
//	}

}
