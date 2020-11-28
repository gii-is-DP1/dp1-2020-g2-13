package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Logro;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LogroValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Logro logro = (Logro) obj;
		String nombre = logro.getNombre();
		String descripcion = logro.getDescripcion();
		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length()<1 || nombre.length()>30) {
			errors.rejectValue("nombre", REQUIRED+" and between 1 and 30 characters", REQUIRED+" and between 1 and 30 characters");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("nombre", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// descripcion validation
		if (!StringUtils.hasLength(descripcion) || descripcion.length()<1 || descripcion.length()>250) {
			errors.rejectValue("descripcion", REQUIRED+" and between 1 and 250 characters", REQUIRED+" and between 1 and 250 characters");
		}
		if (descripcion.trim().length() == 0) {
			errors.rejectValue("descripcion", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Logro.class.isAssignableFrom(clazz);
	}

}
