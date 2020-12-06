package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "opciones")
public class Opcion extends BaseEntity {
	
	private String texto;
	
	private Boolean esCorrecta;
	
}
