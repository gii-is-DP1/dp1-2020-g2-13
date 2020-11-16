package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tests")
public class Test extends BaseEntity{

	private String titulos;
	private Double puntuacionMaxima;
	private Double puntuacionMinima;
}
