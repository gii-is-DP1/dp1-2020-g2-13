package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.service.businessrules.ValidatePossiblePdf;

import lombok.Data;

@Data
@Entity
@Table(name = "pdfs")
@ValidatePossiblePdf
public class Pdf extends BaseEntity{
	
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@NotEmpty
	private String link;
	@NotEmpty
	private String nombre;
}
