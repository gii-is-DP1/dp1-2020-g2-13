
package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class HiloValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Hilo hilo = (Hilo) obj;
		String nombre = hilo.getNombre();
		String categoria = hilo.getCategoria();
		String contenido = hilo.getContenido();
		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length()<1) {
			errors.rejectValue("nombre", REQUIRED+" and above 1 characters", REQUIRED+" and above 1 characters");
		}
		if (nombre.length()>50) {
			errors.rejectValue("nombre", REQUIRED+" and above 1024 characters", REQUIRED+" and above 1 characters");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("nombre", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// categoria validation
		if (!StringUtils.hasLength(categoria) || categoria.length()<1) {
			errors.rejectValue("categoria", REQUIRED+" and above 1 characters", REQUIRED+" and above 1 characters");
		}
		if (categoria.length()>50) {
			errors.rejectValue("categoria", REQUIRED+" and above 1024 characters", REQUIRED+" and above 1 characters");
		}
		if (categoria.trim().length() == 0) {
			errors.rejectValue("categoria", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// contenido validation
		if (!StringUtils.hasLength(contenido) || contenido.length()<1) {
			errors.rejectValue("contenido", REQUIRED+" and above 1 characters", REQUIRED+" and above 1 characters");
		}
		if (contenido.length()>1024) {
			errors.rejectValue("contenido", REQUIRED+" and above 1024 characters", REQUIRED+" and above 1 characters");
		}
		if (contenido.trim().length() == 0) {
			errors.rejectValue("contenido", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Hilo.class.isAssignableFrom(clazz);
	}

}
