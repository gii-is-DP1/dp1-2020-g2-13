package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tipoTests")
public class TipoTest extends BaseEntity {
	
	@OneToMany
	private List<Opcion> opciones;
	
}
