package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "visitas")
public class Visita extends BaseEntity{

	private LocalTime tiempo;
	private LocalDate fecha;
}
