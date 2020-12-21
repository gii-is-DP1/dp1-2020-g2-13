package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
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
	@Size(min=1)
	private String contenido;
//	private int likes;

	@ManyToOne(optional = true)
	private Comentario cita;

	@OneToMany
	private Set<Comentario> citas;

	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	@NotNull
	private Hilo hilo;
}