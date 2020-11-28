package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "examenes")
public class Examen extends BaseEntity{

	@NotNull
	private String titulos;
	@NotNull
	private Double puntuacionMaxima;
	@NotNull
	private Double puntuacionMinima;
	
	@ManyToOne(optional = false)
	private Usuario usuario;
}
