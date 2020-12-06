package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity{

	@NotNull
	@Size(max = 30)
	private String nombre;
	@NotNull
	@Size(max = 100)
	private String apellidos;
	@NotNull
	private String localidad;
	private String colegio;
	@NotNull
	private String email;
//	@NotNull
//	private String contrasena;
	
	@ManyToMany
	Set<Logro> logros;
	
	@ManyToMany
	Set<Examen> examenes;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
}
