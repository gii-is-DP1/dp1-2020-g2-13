package org.springframework.samples.petclinic.model.businessrulesexceptions;

public class ImpossibleUsuarioException extends Exception {
	
	private String usuario;
	
	public ImpossibleUsuarioException(String usuario) {
		super("El nombre de usuario no puede estar vacío");
		this.usuario = usuario;
	}

}
