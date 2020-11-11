package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hilos")
public class Hilo extends BaseEntity{

	private String nombre;
	private Integer UnlockLevel;
}
