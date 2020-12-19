package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "respuestas")
public class Respuesta extends BaseEntity{
	
	@NotNull
	private String respuesta;
}
