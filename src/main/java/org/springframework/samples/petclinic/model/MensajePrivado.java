package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter

@Setter
@Entity
@Table(name = "mensajesPrivados")
public class MensajePrivado extends BaseEntity{
	
	//private LocalDate fecha;
	@NotEmpty
	@Size(max = 250)
	private String contenido;

	@ManyToOne(optional = false)
	private Usuario emisor;
	
	@ManyToOne(optional = false)
	private Usuario receptor;
	
	
}
