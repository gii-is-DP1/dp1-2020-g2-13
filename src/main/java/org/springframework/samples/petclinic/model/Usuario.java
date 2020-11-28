package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity{

	@Size(max = 30)
	private String nombre;
	
	@Size(max = 100)
	private String apellidos;
	
	private String localidad;
	
	private String colegio;
	
	private String email;
	
	private String contrasena;

	@NotEmpty
	@ManyToMany
	Set<Logro> logros;

	@NotEmpty
	@ManyToMany
	Set<Examen> examenes;
}
