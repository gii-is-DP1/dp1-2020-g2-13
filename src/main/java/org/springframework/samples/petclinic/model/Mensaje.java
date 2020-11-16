package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "mensajes")
public class Mensaje extends BaseEntity{
	
	private LocalDate fecha;
	private String tipoMensaje; //Hacer un tipo Enumerado o evaluarlo con String?
	@Size(max = 350)
	private String contenido;
	private Integer likes;
	//Comentario seria una relacion de mensaje con mensaje?
	

}
