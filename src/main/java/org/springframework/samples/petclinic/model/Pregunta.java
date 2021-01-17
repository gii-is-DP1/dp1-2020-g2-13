package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "preguntas")
public class Pregunta extends BaseEntity{
	@NotEmpty
    @Size(max = 250)
	private String contenido;
	
	@OneToOne
	private TipoTest tipoTest;
	
}
