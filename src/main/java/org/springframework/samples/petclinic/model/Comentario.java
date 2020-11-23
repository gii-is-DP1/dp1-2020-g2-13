package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "comentarios")
public class Comentario extends BaseEntity {
	

//	private Date fecha;
//	private String tipoComentario;
	private String contenido;
//	private int likes;


	@ManyToOne(optional = true)
	private Usuario usuario;
	
	@ManyToOne(optional = true)
	private Hilo hilo;
}