package org.springframework.samples.petclinic.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "videos")
public class Video extends BaseEntity{

	private String link;
	@Size(max = 250)
	private String descripcion;
	private String duracion;
	
	
}
