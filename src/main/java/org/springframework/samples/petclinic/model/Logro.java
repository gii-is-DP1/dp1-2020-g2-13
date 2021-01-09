package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "logros")
public class Logro extends BaseEntity {
	@NotEmpty
	private String nombre;
	
	@Size(max = 250)
	private String descripcion;
}
