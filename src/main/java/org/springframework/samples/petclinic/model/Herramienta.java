package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "herramientas")
public class Herramienta extends BaseEntity{

	private String nombre;
	@Size(max = 250)
	private String descripcion;
	// tipo de las herramientas?
}
