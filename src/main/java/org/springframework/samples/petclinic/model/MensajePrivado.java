package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "mensajesPrivados")
public class MensajePrivado extends BaseEntity{
	
	//private LocalDate fecha;
	@NotNull
	@Size(max = 250)
	private String contenido;

	@ManyToOne(optional = false)
	private Usuario emisor;
	
	@ManyToOne(optional = false)
	private Usuario receptor;
	
	
}
