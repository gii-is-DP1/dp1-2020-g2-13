package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OpcionValidator implements Validator {
	

	@Override
	public boolean supports(Class<?> clazz) {
		return Opcion.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Opcion opcion = (Opcion) obj;
		String texto= opcion.getTexto();
		Boolean esCorrecta= opcion.getEsCorrecta();
		
		// Texto validation
		if (texto.isEmpty() || texto.equals(" ")|| texto.equals("")) {
			errors.rejectValue("texto", "La opción no puede estar vacía");
		}
		
		//esCorrecta validation
		if (esCorrecta == null) {
			errors.rejectValue("esCorrecta", "La opción tiene que ser, obligatoriamente, verdadera o falsa");
		}	
		
	}

}
