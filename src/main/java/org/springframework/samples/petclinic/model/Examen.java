package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "examenes")
public class Examen extends BaseEntity{

	@NotEmpty
	private String titulos;
	//@NotEmpty
	private Double puntuacionMaxima;
	//@NotEmpty
	private Double puntuacionMinima;
	
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@OneToMany
	private List<Pregunta> preguntas;
	
}
