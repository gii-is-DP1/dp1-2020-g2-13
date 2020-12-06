package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "cuestionarios")
public class Cuestionario extends BaseEntity{
	
	@NotEmpty
	private String titulo;
	
	private String descripcion;
	
}
