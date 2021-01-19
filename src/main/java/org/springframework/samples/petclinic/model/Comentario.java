package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comentarios")
//@ValidatePossibleComentario
public class Comentario extends BaseEntity {
	

//	private Date fecha;
//	private String tipoComentario;
	@NotEmpty
	private String contenido;
//	private int likes;

	@ManyToOne(optional = true)
	private Comentario cita;

	@OneToMany
	private Set<Comentario> citas;

	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	private Hilo hilo;
	
	private int nivel;
}