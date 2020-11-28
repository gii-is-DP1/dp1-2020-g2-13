package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
	private String contenido;

	
	@ManyToOne(optional = false)
	private Usuario usuario;

	@NotEmpty
	@ManyToMany
	Set<Usuario> suscriptores;
}