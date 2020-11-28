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
		if (!StringUtils.hasLength(nombre) || nombre.length() < 1 || nombre.length() > 30) {
			errors.rejectValue("nombre", REQUIRED + " y entre 1 y 30 caracteres",
					REQUIRED + " y entre 1 y 30 caracteres");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("nombre", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
		}
		// descripcion validation
		if (!StringUtils.hasLength(descripcion) || descripcion.length() < 1 || descripcion.length() > 250) {
			errors.rejectValue("descripcion", REQUIRED + " y entre 1 y 250 caracteres",
					REQUIRED + " y entre 1 y 250 caracteres");
		}
		if (descripcion.trim().length() == 0) {
			errors.rejectValue("descripcion", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
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
