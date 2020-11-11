package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "eventos")
public class Evento extends BaseEntity{
	@NotNull
	private String nombre;
	private LocalDate fecha;
	@Size(max = 250)
	private String descripcion;
	private Integer unlockLevel;
	private String tipoEvento;
	private Boolean esPublico;
	

}
