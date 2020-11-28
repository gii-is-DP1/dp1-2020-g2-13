package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "hilos")
public class Hilo extends BaseEntity {

	private enum Categoria {
		General, Buenas_practicas, Preguntas, Eventos, Noticias
	}
	

	@NotNull
	private String nombre;
	@NotNull
	private String categoria;
	@NotNull
	private String contenido;

	
	@ManyToOne(optional = false)
	private Usuario usuario;

	
	@ManyToMany
	Set<Usuario> suscriptores;
}