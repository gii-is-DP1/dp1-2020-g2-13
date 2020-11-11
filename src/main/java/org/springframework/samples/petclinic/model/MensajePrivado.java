package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "mensajesPrivados")
public class MensajePrivado extends BaseEntity{
	
	private LocalDate fecha;
	@Size(max = 250)
	private String contenido;
	
}
