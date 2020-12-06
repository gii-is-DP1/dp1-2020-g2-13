package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
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
	private Comentario comentario;

	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@ManyToOne(optional = false)
	@NotNull
	private Hilo hilo;
}