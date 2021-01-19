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

	

	@NotEmpty
	private String nombre;
	@NotEmpty
	private String categoria;
	@NotEmpty
	private String contenido;

	
	@ManyToOne(optional = false)
	private Usuario usuario;

	
	@ManyToMany
	Set<Usuario> suscriptores;
}