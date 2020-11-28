package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "archivos")
public class Archivo extends BaseEntity{

	@NotNull
	private LocalDate fechaUpload;
	@NotNull
	private  Integer UnlockLevel;
	@NotNull
	private String autor;
}
