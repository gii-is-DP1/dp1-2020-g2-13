package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "examenes")
public class Examen extends BaseEntity{

	private String titulos;
	private Double puntuacionMaxima;
	private Double puntuacionMinima;
	
	@ManyToOne(optional = false)
	private Usuario usuario;
}
