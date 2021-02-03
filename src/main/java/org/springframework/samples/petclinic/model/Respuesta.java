package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "respuestas")
public class Respuesta extends BaseEntity{
	
	@NotEmpty
	private String textoRespuesta;
	private Integer numeroPregunta;
	

	@ManyToOne
	Intento intento;
}