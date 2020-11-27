package org.springframework.samples.petclinic.model.businessrulesexceptions;

public class ImpossibleComentarioException extends Exception {
	
	private String comentario;
	
	public ImpossibleComentarioException(String comentario) {
		super("El comentario no puede estar vacío");
		this.comentario = comentario;
	}

}
