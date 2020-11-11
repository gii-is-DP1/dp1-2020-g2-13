package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity{

	private String nombre;
	private String apellidos;
	private String localidad;
	private String colegio;
	private String email;
	private String contrasena;
}
