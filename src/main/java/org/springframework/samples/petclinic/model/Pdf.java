package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.service.businessrules.ValidatePossiblePdf;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "pdfs")
@ValidatePossiblePdf
public class Pdf extends BaseEntity{

	@NotNull
	private String archivo;
}
