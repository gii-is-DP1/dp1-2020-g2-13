package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.service.businessrules.ValidatePossiblePdf;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "pdfs")
@ValidatePossiblePdf
public class Pdf extends BaseEntity{
	
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@NotNull
	private String link;
	@NotNull
	private String nombre;
}
