package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "respuestas")
public class Respuesta extends BaseEntity{
	
	@NotEmpty
	private String textoRespuesta;
	private Integer numeroPregunta;
}