package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "intentos")
public class Intento extends BaseEntity{

	private Double puntuacion;
	
	private LocalDate fecha;
	
	@OneToOne
	private Examen examen;
}
