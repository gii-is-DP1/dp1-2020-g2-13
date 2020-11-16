package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pdfs")
public class Pdf extends BaseEntity{

	private String archivo;
}
