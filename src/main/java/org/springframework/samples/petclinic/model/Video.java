package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "videos")
public class Video extends BaseEntity{
	
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String link;
	@Size(max = 250)
	private String descripcion;
	
	@javax.validation.constraints.NotEmpty
	private String duracion;
	
	
}
