package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hilos")
public class Hilo extends BaseEntity {

	private enum Categoria {
		General, Buenas_practicas, Preguntas, Eventos, Noticias
	}

	private String nombre;
	private String categoria;

	
	@ManyToOne(optional = true)
	private Usuario usuario;
}