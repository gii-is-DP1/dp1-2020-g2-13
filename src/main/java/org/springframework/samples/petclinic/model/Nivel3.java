package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "niveles3")
public class Nivel3 extends BaseEntity{

	@NotNull
	private Boolean esCoordinador;
	@NotNull
	private Boolean esAutor;
}
