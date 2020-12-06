package org.springframework.samples.petclinic.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "videos")
public class Video extends BaseEntity{

	@NotEmpty
	private String nombre;
	@NotEmpty
	private String link;
	@Size(max = 250)
	private String descripcion;
	
	@javax.validation.constraints.NotEmpty
	private String duracion;
	
	
}
