package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "niveles3")
public class Nivel3 extends BaseEntity{

	private Boolean esCoordinador;
	private Boolean esAutor;
}
