package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "preguntas")
public class Pregunta extends BaseEntity{
	@NotNull
    @Size(max = 250)
	private String contenido;
	private String tipoContenido;
}
