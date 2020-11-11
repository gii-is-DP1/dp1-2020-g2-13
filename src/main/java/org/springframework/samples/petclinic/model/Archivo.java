package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "archivos")
public class Archivo extends BaseEntity{

	private LocalDate fechaUpload;
	private  Integer UnlockLevel;
	private String autor;
}
