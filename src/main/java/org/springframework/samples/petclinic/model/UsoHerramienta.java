package org.springframework.samples.petclinic.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usosHerramientas")
public class UsoHerramienta extends BaseEntity{

	LocalTime tiempo;
}
